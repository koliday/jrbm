package com.jrsportsgame.jrbm.redis;

import com.jrsportsgame.jrbm.service.intf.FreeMarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.nio.charset.StandardCharsets;

public class FreeMarketExpiredListener extends KeyExpirationEventMessageListener {
    private static final Logger logger= LoggerFactory.getLogger(FreeMarketExpiredListener.class);

    @Autowired
    private FreeMarketService freeMarketService;
    public FreeMarketExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        //得到过期的fpid
        String fpid = new String(message.getBody(),StandardCharsets.UTF_8);
        logger.info("redis key 过期：pattern={},channel={},key={}",new String(pattern),channel,fpid);
        //删除过期的自由市场球员fpid
        freeMarketService.removeExpiredFreeMarketPlayer(fpid);
    }
}
