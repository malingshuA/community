package life.maju.community.controller;


import life.maju.community.dto.AccesstokenDTO;
import life.maju.community.dto.GithubUser;
import life.maju.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    //把写好的实例加载到当前写好的上下文
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String Callback(@RequestParam(name="code") String code,@RequestParam(name="state") String state  ){

        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id("b2d945f47a58fefdb3ae");
        accesstokenDTO.setClient_secret("324e2d7a189dcc868d5fc2ae91d9aa9c992ff2df");
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri("http://Community.zhixing.life");
        accesstokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accesstokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "hello";
    }
}
