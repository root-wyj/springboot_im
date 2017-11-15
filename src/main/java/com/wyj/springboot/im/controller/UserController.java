package com.wyj.springboot.im.controller;

import java.util.Base64;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.wyj.springboot.im.authorize.cookie.HeaderFactory;
import com.wyj.springboot.im.authorize.cookie.UserHeaderContainer;
import com.wyj.springboot.im.entity.common.ZJHProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.wyj.springboot.im.authorize.UserContext;
import com.wyj.springboot.im.authorize.cache.LongRedisCacheManager;
import com.wyj.springboot.im.authorize.cache.RedisCacheManager;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.entity.common.ResponseBean;
import com.wyj.springboot.im.service.IRedisService;
import com.wyj.springboot.im.service.UserService;
import com.wyj.springboot.im.tools.StringUtil;
import com.wyj.springboot.im.tools.XXTEA;
import com.wyj.springboot.im.config.BeanIocConfig;

/**
 * @author wuyingjie
 * @date 2017年9月28日
 */

@RestController
@RequestMapping(produces = "application/json;charset=utf-8")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Resource(name = BeanIocConfig.USER_CACHE)
    RedisCacheManager<String, User> userCache;
    @Resource(name = BeanIocConfig.LOGIN_TIMES_CACHE)
    LongRedisCacheManager<User> loginTimeCache;

    @Resource
    IRedisService redisService;

    @Autowired
    UserContext userContext;

    @Autowired
    Environment env;

    @PostMapping(value = "login")
    public String login(HttpServletRequest request, HttpServletResponse response,
                        @RequestBody Map<String, String> model) {
        if (model == null) {
            return ResponseBean.crtParameterErrorResult();
        }

        String username = model.get("username");
        String password = model.get("password");

        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            return ResponseBean.crtParameterErrorResult();
        }

        if (!userService.isExist(username)) {
            return ResponseBean.crtFailureResult("该用户不存在");
        }

        User user = userService.getUser(username);
        if (user.getPassword().equals(password)) {
            HeaderFactory.Header tokenHeader = loginSuccess(user.getId());
            ResponseBean bean = ResponseBean.crtSuccessBean();
            JSONObject content = new JSONObject();
            content.put("wsServer", ZJHProperties.WEBSOCKET_SERVER_URL);
            content.put(tokenHeader.key, tokenHeader.value);
            return bean.toString();
        } else {
            return ResponseBean.crtFailureResult("密码错误");
        }


    }

    @PostMapping
    public String registe(HttpServletRequest request, HttpServletResponse response,
                          @RequestBody Map<String, String> model) {
        if (model == null) {
            return ResponseBean.crtParameterErrorResult();
        }

        String username = model.get("username");
        String password = model.get("password");

        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            return ResponseBean.crtParameterErrorResult();
        }

        long userId = userService.addUser(username, password);
        if (userId > 0) {
            logger.info("username:{} 注册成功！！ userId:{}", username, userId);
            return ResponseBean.crtSuccessResult();
        } else {
            logger.error("注册过程出错！！username:{}, password:{}, userId:{}", username, password, userId);
            return ResponseBean.crtFailureResult("注册失败，请稍后再试！");
        }

    }

    @GetMapping("/userInfo")
    public String userInfo(HttpServletRequest request, HttpServletResponse response) {
        return JSON.toJSONString(userContext.get());
    }

    private HeaderFactory.Header loginSuccess(long userId) {
        String token = "";
        return HeaderFactory.getUserHeader(new UserHeaderContainer(userId, System.currentTimeMillis()));
    }

    public static void main(String[] args) {
        String originStr = System.currentTimeMillis() + ":" + "2"
//					+":"+UUID.randomUUID().toString()
                ;

        String encrytStr = XXTEA.encrypt(originStr);

        String base64Str = Base64.getEncoder().encodeToString(originStr.getBytes());
        System.out.println("originStr:" + originStr
                + "\n encrytStr:" + encrytStr
                + "\n base64Str:" + base64Str);
    }

}
