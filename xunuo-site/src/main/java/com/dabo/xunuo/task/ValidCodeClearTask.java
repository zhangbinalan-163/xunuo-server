package com.dabo.xunuo.task;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 无效验证码定时清理任务
 * Created by zhangbin on 16/9/3.
 */
@Component
public class ValidCodeClearTask implements ITask{

    @Autowired
    private ISmsService smsService;

    @Override
    public void run() throws SysException {
        //查到全部的已失效的验证码
        List<Long> invalidIdList = smsService.getInvalidSmsCodeId();
        if(!CollectionUtils.isEmpty(invalidIdList)){
            //批量删除
            //TODO 控制批量删除的数量
            smsService.deleteSmsCode(invalidIdList);
        }
    }
}
