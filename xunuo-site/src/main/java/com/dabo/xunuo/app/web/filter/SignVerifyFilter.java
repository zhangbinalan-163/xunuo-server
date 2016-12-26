package com.dabo.xunuo.app.web.filter;

import com.dabo.xunuo.base.common.Constants;
import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.util.RequestUtils;
import com.dabo.xunuo.base.util.SignUtils;
import com.dabo.xunuo.app.web.vo.RequestContext;

import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * 签名校验过滤器
 * 将请求中除了app_key以为的所有非空参数按照key字典排序,进行SHA1签名
 * 要先经过RequestParseFilter
 */
public class SignVerifyFilter extends BaseFilter {
    private static final String PARAM_APP_KEY_NAME="app_key";

    private static final String PARAM_SIGN_NAME="sign";

    private static final String PARAM_NONCE_NAME="nonce";

    private static final String PARAM_TIME_NAME="timestamp";

    private static final long TIME_VALID_GAP=1000*60*5L;//5分钟内有效

    @Override
    protected boolean beforeExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取全部非空参数
        Map<String,String> notEmptyParamMap= RequestContext.getNotEmptyParamMap();
        //检查参数格式
        checkSignParam(notEmptyParamMap);
        //检查签名
        checkSign(notEmptyParamMap);
        //检查时间范围
        checkTime(notEmptyParamMap);

        return true;
    }

    private void checkTime(Map<String, String> notEmptyParamMap) throws SysException {
        long timestamp= RequestUtils.getLong(notEmptyParamMap, PARAM_TIME_NAME);
        //检查时间
        long currentTime=System.currentTimeMillis();
        if(currentTime-timestamp > TIME_VALID_GAP){
            throw new SysException("请求已失效", Constants.ERROR_CODE_INVALID_SIGN);
        }
    }

    private void checkSign(Map<String, String> notEmptyParamMap) throws SysException {
        String app_key=RequestUtils.getString(notEmptyParamMap, PARAM_APP_KEY_NAME);
        String sign=RequestUtils.getString(notEmptyParamMap, PARAM_SIGN_NAME);
        //从中找到app_key再去找key对应的secret,这里我们是一一对应
        String app_secret=Constants.APP_SECRET;
        //去掉sign参数、app_secret参数
        notEmptyParamMap.remove(PARAM_SIGN_NAME);
        notEmptyParamMap.remove(PARAM_APP_KEY_NAME);
        //签名校验
        String righSign= SignUtils.generateSign(notEmptyParamMap, app_key, app_secret);
        if(!sign.equals(righSign)){
            throw new SysException("请求签名错误",Constants.ERROR_CODE_INVALID_SIGN);
        }
    }

    /**
     * 检查参数格式
     * @param notEmptyParamMap
     * @throws SysException
     */
    private void checkSignParam(Map<String, String> notEmptyParamMap) throws SysException {
        if(CollectionUtils.isEmpty(notEmptyParamMap)){
            throw new SysException("请求签名错误",Constants.ERROR_CODE_INVALID_SIGN);
        }
        //检查参数
        RequestUtils.getString(notEmptyParamMap, PARAM_APP_KEY_NAME);
        RequestUtils.getString(notEmptyParamMap, PARAM_SIGN_NAME);
        RequestUtils.getInt(notEmptyParamMap, PARAM_NONCE_NAME, 1, 999999);
        RequestUtils.getLong(notEmptyParamMap, PARAM_TIME_NAME);
    }
}
