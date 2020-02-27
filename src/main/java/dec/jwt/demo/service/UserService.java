package dec.jwt.demo.service;

import dec.jwt.demo.vo.User;
import org.springframework.stereotype.Service;

/**
 * @Author: Decimon
 * @Date: 2020-02-25 09:39
 */
@Service
public class UserService {
    public User findByUsername(User user) {
        if(user==null){
            user = new User();
            user.setUsername("zhang");
            user.setPassword("123456");
        }
        user.setId("1");
        return user;
    }
}
