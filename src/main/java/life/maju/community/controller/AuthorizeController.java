package life.maju.community.controller;


import life.maju.community.dto.AccesstokenDTO;
import life.maju.community.dto.GithubUser;
import life.maju.community.mapper.UserMapper;
import life.maju.community.model.User;
import life.maju.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    //把写好的实例加载到当前写好的上下文
    private GithubProvider githubProvider;

    @Value("d33cbfa860c959295e81d0ca404fa47972adf4a452b47de3138f9a934343b154")
    private String clinetId;
    @Value("55d648c6d303152b0e82196e37771d96e8b2843c93c92af9dbe1302a8506f916")
    private  String clientSecret;
    @Value("http://localhost:8800/callback")
    private String redirectUri;

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/callback")
    public String Callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletRequest response){

        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id("d33cbfa860c959295e81d0ca404fa47972adf4a452b47de3138f9a934343b154");
        accesstokenDTO.setClient_secret("55d648c6d303152b0e82196e37771d96e8b2843c93c92af9dbe1302a8506f916");
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri("http://localhost:8800/callback");

//        accesstokenDTO.setClient_id(clinetId);
//        accesstokenDTO.setClient_secret(clientSecret);
//        accesstokenDTO.setCode(code);
//        accesstokenDTO.setRedirect_uri(redirectUri);

        accesstokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());
        if(githubUser != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //HttpServletResponse response = null;
 //           response.addCookie(new Cookie("token",token));

            request.getSession().setAttribute("user",githubUser);
            return "redirect:/index";
            //登录成功，写cookie和session
        }else{
            //登录失败，重新登录
            return "redirect:/index";
        }
    }
}
