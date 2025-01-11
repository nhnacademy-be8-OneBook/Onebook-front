package com.onebook.frontapi.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static com.onebook.frontapi.service.cart.CartService.CART_ID;

/**
 * 장바구니 쿠키가 있으면, 해당 장바구니 ID가 레디스에 있는지 확인.
 * 없으면 task에 요청해서 DB에서 가져옵니다. 만약 DB에 없다면(쿠키 만료) 장바구니 쿠키 삭제.
 */
@RequiredArgsConstructor
public class CartFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        if(Objects.isNull(cookies)) {
            filterChain.doFilter(request, response);
        }

        for(Cookie c : cookies) {
            if(c.getName().equals(CART_ID)) {
                String cartId = c.getValue();
                redisTemplate.
            }
        }
    }
}
