package life.maju.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@Controller允许这个类接受前端的请求.
//@RequestMapping("")
public class HelloController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

//    @RequestMapping("/hello")
//    //@RequestParam:情趣的参数
//    //Model:用于接受浏览器传过来的信息;浏览器传来的值放到modeld里面
//    //
//    public String index(@RequestParam(name = "name") String name,Model modeld){
//        modeld.addAttribute("name",name);
//        return "hello";
//    }
}
