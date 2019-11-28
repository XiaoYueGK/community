package com.dzd.community.provider;


import com.alibaba.fastjson.JSON;
import com.dzd.community.model.AccessToken;
import com.dzd.community.model.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * OkHttp框架的使用 3.x
 */
@Component
public class GitHubProvider {

    public String getAccessToken(AccessToken accessToken){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessToken));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            String token = str.split("&")[0].split("=")[1];
//            System.out.println(token);
            return token;
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return null;
    }


    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str  = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(str,GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
