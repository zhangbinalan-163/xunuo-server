package com.dabo.xunuo.web.filter;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.SidInfo;
import com.dabo.xunuo.util.SidUtils;
import com.dabo.xunuo.util.StringUtils;
import com.dabo.xunuo.web.vo.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对是否登录等进行检查
 */
public class LoginFilter extends BaseFilter {

    @Override
    protected boolean beforeExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //是否登录
        String sid=RequestContext.getSid();
        if(!StringUtils.isEmpty(sid)){
            SidInfo sidInfo= SidUtils.parseSid(sid);
            if(sidInfo!=null){
                //检查登录有效时间,目前暂时固定为一直有效
                //当前登录的UserId
                RequestContext.setUserId(sidInfo.getUserId());
                return true;
            }
        }
        throw new SysException("未登录", Constants.ERROR_CODE_NOT_LOGIN);
    }
}
