package com.noknow.shardingjdbcdemo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/19
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Test
@Tag("simple-no-spring-test")
@EnabledOnLocal
public @interface NoSpringTest {

}
