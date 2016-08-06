package com.dabo.xunuo.web.filter;

import com.dabo.xunuo.entity.SidInfo;
import com.dabo.xunuo.util.RequestUtils;
import com.dabo.xunuo.util.SidUtils;
import com.dabo.xunuo.util.StringUtils;
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

    private static final String HEADER_NAME_CLIENT="X-XN-CLIENT";

    private static final String HEADER_NAME_CLIENT_V="X-XN-CLIENT-V";

    private static final String HEADER_NAME_DEVICEID="X-XN-DEVICEID";

    private static final String HEADER_NAME_SID="X-XN-SID";

    @Override
    protected boolean beforeExecute(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //解析出header
        String client=RequestUtils.getHeader(request, HEADER_NAME_CLIENT);
        ClientType clientType = ClientType.getInstance(client);
        RequestContext.setClientType(clientType);

        String clientVersion=RequestUtils.getHeader(request, HEADER_NAME_CLIENT_V);
        RequestContext.setVersion(Version.getInstance(clientVersion));

        String deviceId=RequestUtils.getHeader(request,HEADER_NAME_DEVICEID,null);
        RequestContext.setDeviceId(deviceId);

        String sid=RequestUtils.getHeader(request,HEADER_NAME_SID,null);
        RequestContext.setSid(sid);

        //解析出全部参数,设置到请求上下文
        Map<String, String> notEmptyParamMap = RequestUtils.getNotEmptyParam(request);
        RequestContext.webParamThreadLocal(notEmptyParamMap);

        return true;
    }
}
