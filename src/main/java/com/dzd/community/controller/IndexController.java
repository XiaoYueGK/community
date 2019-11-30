package com.dzd.community.controller;

import com.dzd.community.mapper.UserMapper;
import com.dzd.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/")
    public String getHello(HttpServletRequest req){

        Cookie[] cookies = req.getCookies();
        for(Cookie c : cookies){
            if(c.getName().equals("token")){
                String token = c.getValue();
                User byToken = userMapper.findByToken(token);
                if (byToken != null) {
                    req.getSession().setAttribute("user",byToken);
                    //System.out.println(byToken);
                }

                break;
            }
        }

        return "index";
    }
}
