package com.zirconlabz.ordermanager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("account-manager")
public interface AccountFeignClient {

    @GetMapping("/account/verify")
    public int verify(@RequestParam("id") int id);
}
