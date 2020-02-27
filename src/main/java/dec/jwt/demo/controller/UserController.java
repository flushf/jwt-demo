package dec.jwt.demo.controller;

import com.alibaba.fastjson.JSONObject;
import dec.jwt.demo.annotation.JwtLoginToken;
import dec.jwt.demo.service.TokenService;
import dec.jwt.demo.service.UserService;
import dec.jwt.demo.util.TokenUtils;
import dec.jwt.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Decimon
 * @Date: 2020-02-25 09:34
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    //登录
    @PostMapping("/login")
    public Object login(@RequestBody User user, HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        User userForBase=userService.findByUsername(user);
        if(userForBase==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!userForBase.getPassword().equals(user.getPassword())){
                jsonObject.put("message","登录失败,密码错误");
                return jsonObject;
            }else {
                String remoteHost = request.getRemoteHost();
                String token = TokenUtils.getToken(remoteHost,userForBase.getId());
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return jsonObject;
            }
        }
    }
    @JwtLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }
}
