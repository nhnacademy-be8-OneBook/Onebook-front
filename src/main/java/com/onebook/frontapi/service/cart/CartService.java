package com.onebook.frontapi.service.cart;

import com.onebook.frontapi.dto.cart.BookOrderRequest;
import com.onebook.frontapi.dto.cart.CartItemResponse;
import com.onebook.frontapi.dto.cart.CartResponse;
import com.onebook.frontapi.dto.cart.CartFeignResponse;
import com.onebook.frontapi.feign.cart.CartClient;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartClient cartClient;
    private final RedisTemplate<String, Objects> redisTemplate;
    public final static String CART_ID = "CART_ID";
    private final static String CART_PREFIX = "CART:";

    // 장바구니 ID return.
    public String getCartIdFromCookie (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = null;

        Cookie[] cookies = request.getCookies();
        // 쿠키가 하나도 없으면 '홈'으로 리다이렉트 하게 함.
        if(Objects.isNull(cookies)) {
            response.sendRedirect("/");
        }

        // 장바구니 쿠키가 있으면 쿠키 안의 cartId 값 return.
        for(Cookie c : cookies) {
            if(c.getName().equals(CART_ID)) {
                result = c.getValue();
                return result;
            }
        }

        // cartId가 없으면 새로 생성.
        String cartId = UUID.randomUUID().toString(); // cartId 생성.

        // CART_ID cookie 생성.
        HttpSession session = request.getSession(true);
        session.setAttribute("cartId", cartId);

        Cookie cookie = new Cookie(CART_ID, session.getId());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30*24*60*60); // 장바구니 쿠키 유효 기간: 30일
        cookie.setPath("/");
        response.addCookie(cookie);

        return result;
    }

    // cartId로 redis에 저장된 cartItems 가져오기.
    public List<CartItemResponse> getCartItemsFromRedisById(String cartId) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(cartId);
        List<CartItemResponse> cartItemResponses = CartItemResponse.fromMapToList(map);

        return cartItemResponses;
    }

    // 장바구니에 물건 담기: redis에 cartItemResponse 저장.
    public boolean addCartItem(String cartId, BookOrderRequest bookOrderRequest) {

        Map<Object, Object> map = redisTemplate.opsForHash().entries(CART_PREFIX + cartId);

        if(map.containsKey(bookOrderRequest.bookId())) {
            return false;
        }

        map.put(bookOrderRequest.bookId(), bookOrderRequest.quantity());
        redisTemplate.opsForHash().putAll(CART_PREFIX + cartId, map);

        return true;
    }

    // 장바구니에서 도서 삭제
    public void removeCartItem(String cartId, List<Long> bookId) {
//        Map<Object, Object> map = redisTemplate.opsForHash().entries(CART_PREFIX + cartId);
//        Set<Object> bookIds = map.keySet();
//
//        if(map.containsKey(bookId)) {
        redisTemplate.opsForHash().delete(CART_PREFIX + cartId);
//        }
    }

    // 장바구니에서 도서 수량 변경
    public void modifyCartItem(String cartId, BookOrderRequest bookOrderRequest) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(CART_PREFIX + cartId);

        if(map.containsKey(bookOrderRequest.bookId())) {
            map.put(bookOrderRequest.bookId(), bookOrderRequest.quantity());
            redisTemplate.opsForHash().putAll(CART_PREFIX + cartId, map);
        }

    }

    // task(DB)에서 장바구니 가져오기와서 redis에 저장.
    public void saveCartFromDbToRedis(String cartId) {
        CartFeignResponse cartFeignResponse = cartClient.getRequestById(cartId);
        CartResponse cartResponse = CartResponse.from(cartFeignResponse);

        for(CartItemResponse ci : cartResponse.cartItemResponses()) {
            redisTemplate.opsForHash().put(CART_PREFIX + cartId, ci.bookId(), ci.quantity());
        }

    }




}
