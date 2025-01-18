package com.onebook.frontapi.config.advice;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;
import java.util.Objects;

import static com.onebook.frontapi.service.cart.CartService.CART_ID;
import static com.onebook.frontapi.service.cart.CartService.CART_PREFIX;


@RequiredArgsConstructor
@ControllerAdvice
public class GlobalControllerAdvice {

    private final RedisTemplate<Object, Object> redisTemplate;

//    private final CategoryService categoryService;
//
//    @Autowired
//    public GlobalControllerAdvice(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }
//
//    // 모든 요청에서 topCategories를 자동으로 추가
//    @ModelAttribute("topCategories")
//    public List<CategoryDTO> getTopCategories() {
//        List<CategoryDTO> categories = categoryService.getTopCategories();
//        return categories;
//    }
    @ModelAttribute(name="cartProductCounting")
    public void setViewHeaderFrag(
            @CookieValue(required=false, value=CART_ID) Cookie cartCookie,
            Model model) {
        // 장바구니 아이템 개수.
        Long count = 0L;

        if(Objects.nonNull(cartCookie)) {
            String cartId = cartCookie.getValue();
            count = redisTemplate.opsForHash().size(CART_PREFIX +cartId);
        }

        model.addAttribute("cartCount", count);
    }
}
