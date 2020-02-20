package com.gps.api.modules.sys.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "主页")
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping(value = "/")
    public String index() {
        return "redirect:/swagger-ui.html";
    }
    @GetMapping(value = "/index")
    public String index1() {
        return "redirect:/doc.html";
    }
}
