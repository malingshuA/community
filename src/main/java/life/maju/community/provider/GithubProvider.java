package life.maju.community.provider;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import life.maju.community.dto.AccesstokenDTO;
import life.maju.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * provider:承载第三方接口
 * 这个文件是okhttp文档
 */
@Component
//承载Spring boot 上下文,加上这个注解之后就不需要 A b = new A()格式了
public class GithubProvider {
    public String getAccessToken(AccesstokenDTO accesstokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accesstokenDTO));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code&code="+accesstokenDTO.getCode() + "&client_id "+accesstokenDTO.getClient_id() + "&redirect_uri="+ accesstokenDTO.getRedirect_uri() + "&client_secret=" + accesstokenDTO.getClient_secret())
                //.url("https://gitee.com/oauth/authorize/access_token")
                //.url("https://gitee.com/oauth/token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            String token = string.split("&")[0].split("=")[1];
            //System.out.println(token);
            return string;
        } catch (IOException e) {
            //printStackTrace()函数:指出异常的类型,性质,栈层次及出现在程序中的位置
            e.printStackTrace();
            // e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        //String AA ="wangbadan";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user/access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            //assert response.body() != null;
            String string = response.body().string();
            //将json的对象,自动解析成类对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            //System.out.println(string);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
