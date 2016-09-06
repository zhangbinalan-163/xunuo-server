package com.dabo.xunuo.task;

import com.dabo.xunuo.common.exception.SysException;
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
    private static Logger LOG= LoggerFactory.getLogger(TaskConfig.class);

    @Autowired
    private ValidCodeClearTask validCodeClearTask;

    /**
     * 无效短信验证码清理后台任务
     * 每天01:00执行
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void clearInvalidSmsCode(){
        try {
            validCodeClearTask.run();
        } catch (SysException e) {
            LOG.error("task fail,invalid code clear task",e);
        }
    }
}
