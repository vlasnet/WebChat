package com.github.mykhalechko.webchat.controller;

import com.github.mykhalechko.webchat.entity.ChatUser;
import com.github.mykhalechko.webchat.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RegistrationController {

    @Autowired
    RegistrationService service;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView getRegister() {
        return service.getRegister();
    }

    @RequestMapping(value = "/registration-handler", method = RequestMethod.POST)
    public ModelAndView registrationHandler(@ModelAttribute("user")
                                            @Validated ChatUser user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            if (service.create(user)) {
                modelAndView.setViewName("index");
            } else {
                modelAndView.setViewName("registration");
                modelAndView.addObject("alreadyexist", "User alreadyexist");
            }
        }
        return modelAndView;
    }
}
