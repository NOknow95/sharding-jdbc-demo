package com.noknow.shardingjdbcdemo.exception;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/23
 */
public class TestRunTimeException extends RuntimeException{

  public TestRunTimeException(String message) {
    super(message);
  }
}
