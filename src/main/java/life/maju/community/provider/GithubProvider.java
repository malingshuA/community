package life.maju.community.provider;


import life.maju.community.dto.AccesstokenDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
//承载Spring boot 上下文
public class GithubProvider {
    public String getAccessToken(AccesstokenDTO accesstokenDTO){
        public static final MediaType JSON
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            return string;
        } catch (IOException e) {

        }
        return null;
    }
}
