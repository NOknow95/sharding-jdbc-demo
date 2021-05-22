package com.noknow.shardingjdbcdemo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/20
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@EnabledOnOs(value = {OS.WINDOWS, OS.MAC}, disabledReason = "should be windows or mac system")
public @interface EnabledOnLocal {

}
