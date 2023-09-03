package org.chat.application.controller;

import org.chat.application.AppServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
/**
 * @author Saransh Gupta
 */
@RestController
public class AppErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(AppServer.class);
    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public ModelAndView handleError()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        return modelAndView;
    }

}
