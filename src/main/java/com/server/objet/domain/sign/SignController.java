package com.server.objet.domain.sign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sign")
public class SignController {

    @GetMapping("/test")
    public String tester() {
        return "Success";
    }

}
