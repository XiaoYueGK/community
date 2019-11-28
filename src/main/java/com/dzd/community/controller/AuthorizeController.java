package com.dzd.community.controller;

import com.dzd.community.model.AccessToken;
import com.dzd.community.model.GitHubUser;
import com.dzd.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){

        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setCode(code);
        accessToken.setState(state);
        accessToken.setRedirect_uri(redirectUri);

        String acc = gitHubProvider.getAccessToken(accessToken);
        GitHubUser gitHubUser = gitHubProvider.getUser(acc);

        System.out.println(gitHubUser.getName());

        return "index";
    }
}
