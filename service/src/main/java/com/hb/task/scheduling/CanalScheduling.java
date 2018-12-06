package com.hb.task.scheduling;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.Message;
import com.hb.task.event.BaseCanalEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
/**
 * Created by huzhe on 2018/11/29.
 * 定时监控canal中数据变更
 */
@Component
public class CanalScheduling implements Runnable, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(CanalScheduling.class);
    private ApplicationContext applicationContext;

    @Resource
    private CanalConnector canalConnector;

    @Scheduled(fixedDelay = 100)
    @Override
    public void run() {
        try {
            int batchSize = 2*1000;
            Message message = canalConnector.getWithoutAck(batchSize);
            long batchId = message.getId();
            logger.info("scheduled_batchId=" + batchId);
            try {
                List<Entry> entries = message.getEntries();
                logger.info("entries size {}",entries.size());
                if (batchId != -1 && entries.size() > 0) {
                    entries.forEach(entry -> {
                        if (entry.getEntryType() == EntryType.ROWDATA) {
                            logger.info("entry.getHeader() ="+entry.getHeader().toString());
                            long executeTime = entry.getHeader().getExecuteTime();
                            long delayTime = System.currentTimeMillis() - executeTime;
                            logger.info ("数据库binglog时间到解析时间偏移 ="+delayTime);
                            publishCanalEvent(entry);
                           
                        }
                    });
                }
                canalConnector.ack(batchId);
            } catch (Exception e) {
                logger.error("发送监听事件失败！batchId回滚,batchId=" + batchId, e);
                //canalConnector.ack(batchId);
                canalConnector.rollback(batchId);
            }
        } catch (Exception e) {
            logger.error("canal_scheduled异常！", e);
        }
    }

    /**
     * 事件发布
     * @param entry
     */
    private void publishCanalEvent(Entry entry) {
        applicationContext.publishEvent(new BaseCanalEvent(entry));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
