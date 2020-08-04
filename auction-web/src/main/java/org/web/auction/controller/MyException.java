package org.web.auction.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.web.auction.utils.CustomException;

@ControllerAdvice  //主要的作用：设计全局异常处理
public class MyException {

    @ExceptionHandler(CustomException.class)
    public ModelAndView priceException(Exception ex) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorMsg",ex.getMessage());
        mv.setViewName("error");
        return mv;
    }
}
