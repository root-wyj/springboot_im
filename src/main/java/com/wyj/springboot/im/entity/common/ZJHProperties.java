package com.wyj.springboot.im.entity.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created
 * Author: wyj
 * Email: 18346668711@163.com
 * Date: 2017/11/15
 */
@Component
public class ZJHProperties {

    public static String WEBSOCKET_SERVER_URL;


    @PostConstruct
    private void init() {
        WEBSOCKET_SERVER_URL = websocketServerUrl;
    }

    @Value(value = "${nss.server.host}")
    private String websocketServerUrl;

}
