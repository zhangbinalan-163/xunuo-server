package com.dabo.xunuo.web.filter;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.util.RequestUtils;
import com.dabo.xunuo.util.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 签名校验过滤器
 * 将请求中除了app_key以为的所有非空参数按照key字典排序,进行SHA1签名
 */
public class SignVerifyFilter extends BaseFilter {
    private static Logger LOGGER = LoggerFactory.getLogger(SignVerifyFilter.class);

    private static final String PARAM_APP_KEY_NAME="app_key";

    private static final String PARAM_SIGN_NAME="sign";

    @Override
    protected boolean beforeExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //请求中获取全部非空参数
        Map<String,String> notEmptyParamMap=RequestUtils.getNotEmptyParam(request);
        if(CollectionUtils.isEmpty(notEmptyParamMap)){
            return false;
        }
        //从中找到app_key再去找key对应的secret,这里我们是一一对应
        String app_key=notEmptyParamMap.getOrDefault(PARAM_APP_KEY_NAME, Constants.APP_KEY);
        String app_secret=Constants.APP_SECRET;

        String signParam=notEmptyParamMap.get(PARAM_SIGN_NAME);
        //去掉sign参数
        notEmptyParamMap.remove(PARAM_SIGN_NAME);
        //签名校验
        String righSign=SignUtils.generateSign(notEmptyParamMap, app_key, app_secret);
        if(righSign==null||signParam==null||!signParam.equals(righSign)){
            throw new SysException("请求签名错误",Constants.ERROR_CODE_INVALID_SIGN);
        }
        return true;
    }
}
