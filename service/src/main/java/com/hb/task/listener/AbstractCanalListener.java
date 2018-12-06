package com.hb.task.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.hb.task.event.BaseCanalEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by huzhe on 2018/11/29.
 */
public abstract class AbstractCanalListener implements ApplicationListener<BaseCanalEvent> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCanalListener.class);

    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("fix-pool-%d").build();

    private ExecutorService pool = new ThreadPoolExecutor(1, 200, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    @Override
    public void onApplicationEvent(BaseCanalEvent event) {
        Entry entry = event.getEntry();
        String database = entry.getHeader().getSchemaName();
        String table = entry.getHeader().getTableName();
        CanalEntry.EventType eventType = entry.getHeader().getEventType();
        RowChange change;
        try {
            change = RowChange.parseFrom(entry.getStoreValue());
        } catch (InvalidProtocolBufferException e) {
            logger.error("canalEntry_parser_error,根据CanalEntry获取RowChange失败！", e);
            return;
        }
        doSync(database, table, change.getRowDatasList(),eventType);
    }

    /**
     * 抽象方法
     * @param database
     * @param table
     * @param rowDataList
     */
    protected abstract void doSync(String database, String table, List<RowData> rowDataList, CanalEntry.EventType eventType);
}
