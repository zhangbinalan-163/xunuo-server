package com.dabo.xunuo.web.filter;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.util.RequestUtils;
import com.dabo.xunuo.web.vo.ClientType;
import com.dabo.xunuo.web.vo.RequestContext;
import com.dabo.xunuo.web.vo.Version;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 请求解析的Filter
 * 解析一些HEADER、请求参数,设置到当前请求环境中
 * 解析出
 */
public class RequestParseFilter extends BaseFilter {

    private static final String PARAM_NAME_CLIENT="client_type";

    private static final String PARAM_NAME_DEVICEID="device_id";

    @Override
    protected boolean beforeExecute(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //解析出全部参数,设置到请求上下文
        Map<String, String> notEmptyParamMap = RequestUtils.getNotEmptyParam(request);
        RequestContext.webParamThreadLocal(notEmptyParamMap);

        //client_type
        String client=RequestUtils.getString(notEmptyParamMap, PARAM_NAME_CLIENT);
        int index=client.indexOf("_");
        if(index==-1){
            throw new SysException("参数空或格式错误:"+PARAM_NAME_CLIENT, Constants.ERROR_CODE_INVALID_PARAM);
        }
        String clientVersion=client.substring(index+1);
        String clientTypeStr=client.substring(0,index);
        ClientType clientType = ClientType.getInstance(clientTypeStr);
        RequestContext.setClientType(clientType);
        RequestContext.setVersion(Version.getInstance(clientVersion));
        //device_id
        String deviceId=RequestUtils.getString(notEmptyParamMap, PARAM_NAME_DEVICEID);
        RequestContext.setDeviceId(deviceId);

        return true;
    }
}
