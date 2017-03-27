package com.ch.study.other;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

/**
 * Created by chenhao on 2016/9/14.
 */
public class ConnectionRedis {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Jedis jedis = new Jedis("121.40.211.14",6379);
        jedis.set("aa","dfsdfds");
        System.out.println(jedis.get("aa"));
        jedis.del("aa");
        /*MobileMsg mobileMsg = new MobileMsg();
        mobileMsg.setMessage(new String[]{"imya测试发送雉哈哈@@@"});
        mobileMsg.setRec_num("15068815576");
        mobileMsg.setType(0);
        mobileMsg.setTimestamp("115555888456");
        mobileMsg.setSms_param("dfd");
        jedis.lpush("ronglian_sfyz_sms",objectMapper.writeValueAsString(mobileMsg));*/
        jedis.close();
    }
}
