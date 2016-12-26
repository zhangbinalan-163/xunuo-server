package com.dabo.xunuo.app.web.filter;

import com.dabo.xunuo.base.common.Constants;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.entity.DeviceInfo;
import com.dabo.xunuo.base.service.IDeviceService;
import com.dabo.xunuo.app.web.vo.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对是否登录等进行检查
 */
@Component
public class LoginFilter extends BaseFilter {

    @Autowired
    private IDeviceService deviceService;

    @Override
    protected boolean beforeExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String deviceId=RequestContext.getDeviceId();
        DeviceInfo deviceInfo = deviceService.getByDeviceId(deviceId);
        if(deviceInfo!=null&&deviceInfo.getLoginUserId()!=0&&inTimeValid(deviceInfo.getLoginTime())){
            RequestContext.setUserId(deviceInfo.getLoginUserId());
            return true;
        }
        //是否登录
        throw new SysException("未登录", Constants.ERROR_CODE_NOT_LOGIN);
    }

    /**
     * 登录是否过期
     * @param loginTime
     * @return
     */
    private boolean inTimeValid(long loginTime) {
        return true;
    }
}
