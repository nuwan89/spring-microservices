package com.zirconlabz.accountmanager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountResource {

    @GetMapping("/test")
    public String test() {
        System.out.println("Testing..");
        return "OK!";
    }

    @GetMapping("/verify")
    public int verify(@RequestParam("id") int id){
        System.out.println("verifying ID: "+id);
        if(id > 10000) {
            id = id/1000000;
        }
        if (id % 2 != 0) {
            id = 0;
        }
        return id;
    }

    @RequestMapping("/error")
    public String fail() {
        System.out.println("REQUEST RECEIVED FOR /error ENDPOINT");
        throw new RuntimeException();
//        return "I WORK LOL";
    }
}
