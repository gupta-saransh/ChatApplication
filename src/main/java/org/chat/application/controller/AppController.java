package org.chat.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
/**
 * @author Saransh Gupta
 */
@Controller
@RequestMapping("/")
public class AppController {

/*    @GetMapping("/")
    public RedirectView redirectToNewURL() {
        return new RedirectView("/app/index.html"); // Redirects to /new-url
    }*/
}
