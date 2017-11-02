package com.dabo.xunuo.base.task;

import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.event.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务的总配置
 * Created by zhangbin on 16/9/3.
 */
@Component
public class TaskConfig {
    private static Logger LOG = LoggerFactory.getLogger(TaskConfig.class);

    @Autowired
    private ValidCodeClearTask validCodeClearTask;

    @Autowired
    private EventNoticeTask eventNoticeTask;


    @Autowired
    private EventNextTimeTask eventNextTimeTask;

    /**
     * 无效短信验证码清理后台任务
     * 每天01:00执行
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void clearInvalidSmsCode() {
        try {
            validCodeClearTask.run();
        } catch (SysException e) {
            LOG.error("task fail,invalid code clear task", e);
        }
    }

    /**
     * 更新全部事件的下一次事件时间、下一次通知时间
     * 每天02:00执行
     */
    @Scheduled(cron = "0 0 2 * * ? ")
    public void eventNextTime() {
        try {
            eventNextTimeTask.run();
        } catch (SysException e) {
            LOG.error("task fail,event next time task", e);
        }
    }

    /**
     * 事件通知
     * 每天08:00执行
     */
    @Scheduled(cron = "0 0 8 * * ? ")
    public void eventNotice() {
        try {
            eventNoticeTask.run();
        } catch (SysException e) {
            LOG.error("task fail,event notice task", e);
        }
    }
}
