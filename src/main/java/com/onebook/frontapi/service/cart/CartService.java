package com.onebook.frontapi.service.cart;

import com.onebook.frontapi.dto.cart.BookOrderRequest;
import com.onebook.frontapi.dto.cart.CartItemResponse;
import com.onebook.frontapi.dto.cart.CartResponse;
import com.onebook.frontapi.dto.cart.CartFeignResponse;
import com.onebook.frontapi.feign.cart.CartClient;
import feign.FeignException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartClient cartClient;
    private final RedisTemplate<String, Long> redisTemplate;
    public final static String CART_ID = "CART_ID";
    public final static String CART_PREFIX = "CART:";

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
        if(Boolean.TRUE.equals(redisTemplate.hasKey(CART_PREFIX + cartId))) {
            redisTemplate.opsForHash().put(CART_PREFIX+cartId, bookOrderRequest.bookId(), bookOrderRequest.quantity());
            redisTemplate.expire(CART_PREFIX + cartId, 30, TimeUnit.DAYS); // 유효기간 30일
        } else {
            redisTemplate.expire(CART_PREFIX + cartId, 30, TimeUnit.DAYS); // 유효기간 30일
        }

        return true;
    }

    // 장바구니에서 도서 삭제
    public void removeCartItem(String cartId, List<Long> bookId) {
        redisTemplate.opsForHash().delete(CART_PREFIX + cartId, bookId);
    }

    // 장바구니에서 도서 수량 변경
    public void modifyCartItem(String cartId, BookOrderRequest bookOrderRequest) {
        redisTemplate.opsForHash().put(CART_PREFIX + cartId, bookOrderRequest.bookId(), bookOrderRequest.quantity());
    }

    // task(DB)에서 장바구니 가져오와서 redis에 저장. by cartId
    public void saveCartFromDbToRedisByCartId(String cartId) {
        CartFeignResponse cartFeignResponse = cartClient.getRequestById(cartId);
        CartResponse cartResponse = CartResponse.from(cartFeignResponse);

        for(CartItemResponse ci : cartResponse.cartItemResponses()) {
            redisTemplate.opsForHash().put(CART_PREFIX + cartId, ci.bookId(), ci.quantity());
        }

    }

    // task(DB)에서 장바구니 가져와서 redis에 저장. by loginId
    public void saveCartFromDbToRedisByLoginId(String loginId,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        // CART_ID 쿠키가 있다면
        for(Cookie c : cookies) {
            if(c.getName().equals(CART_ID)) {
                return;
            }
        }

        try {
            // CART_ID 쿠키가 없으면
            CartFeignResponse cartFeignResponse = cartClient.getRequestByLoginId(loginId);
            CartResponse cartResponse = CartResponse.from(cartFeignResponse);

            // redis에 db에서 가져온 cart 저장.
            if (Objects.nonNull(cartResponse)) {
                for (CartItemResponse ci : cartResponse.cartItemResponses()) {
                    redisTemplate.opsForHash().put(CART_PREFIX + cartResponse.cartId(), ci.bookId(), ci.quantity());
                    redisTemplate.expire(CART_ID + cartFeignResponse.cartId(), 30, TimeUnit.DAYS);
                }
            }

            // CART_ID 쿠키 재발급
            HttpSession session = request.getSession(true);
            session.setAttribute("cartId", cartResponse.cartId());

            Cookie cookie = new Cookie(CART_ID, session.getId());
            cookie.setHttpOnly(true);
            cookie.setMaxAge(30 * 24 * 60 * 60); // 장바구니 쿠키 유효 기간: 30일
            cookie.setPath("/");
            response.addCookie(cookie);
        }catch(FeignException e) {
            return;
        }
    }

}
