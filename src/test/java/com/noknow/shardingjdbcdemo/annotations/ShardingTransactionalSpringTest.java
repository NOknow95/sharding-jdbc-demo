package com.noknow.shardingjdbcdemo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/19
 */
@SpringBootTest
@Transactional(transactionManager = "shardingTransactionManager")
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnabledOnLocal
public @interface ShardingTransactionalSpringTest {

}
