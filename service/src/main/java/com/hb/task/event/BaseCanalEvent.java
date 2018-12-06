package com.hb.task.event;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springframework.context.ApplicationEvent;

/**
 * Created by huzhe on 2018/11/29.
 */
public class BaseCanalEvent extends ApplicationEvent {

    public BaseCanalEvent(CanalEntry.Entry source) {
        super(source);
    }

    public CanalEntry.Entry getEntry() {
        return (CanalEntry.Entry) source;
    }
}
