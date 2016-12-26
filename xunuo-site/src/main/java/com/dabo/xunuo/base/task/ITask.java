package com.dabo.xunuo.base.task;

import com.dabo.xunuo.base.common.exception.SysException;

/**
 * 任务接口
 * Created by zhangbin on 16/9/3.
 */
public interface ITask {
    /**
     * 执行业务
     * @throws SysException
     */
    void run() throws SysException;
}
