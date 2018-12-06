package com.hb.task.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * Created by huzhe on 2018/11/29.
 */
@Component
public class CanalClient {

    private static final Logger logger = LoggerFactory.getLogger(CanalClient.class);

    private CanalConnector canalConnector;

    @Value("${canal.host}")
    private String canalHost;
    @Value("${canal.port}")
    private String canalPort;
    @Value("${canal.destination}")
    private String canalDestination;
    @Value("${canal.username}")
    private String canalUsername;
    @Value("${canal.password}")
    private String canalPassword;

    @Bean
    public CanalConnector getCanalConnector() {
        canalConnector = CanalConnectors.newSingleConnector(new InetSocketAddress(canalHost, Integer.valueOf(canalPort)), canalDestination, "", "");
        canalConnector.connect();
        // 指定filter，格式 {database}.{table}，这里不做过滤，过滤操作留给用户
        canalConnector.subscribe();
        // 回滚寻找上次中断的位置
        canalConnector.rollback();
        logger.info("CanalClient-->canal客户端启动成功");
        return canalConnector;
    }

}
