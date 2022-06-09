package com.zirconlabz.accountmanager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountWeb {

    @RequestMapping("/web-page")
    public String webPage() {
        System.out.println("CALLING WEB");
       return "abc";
    }

}
