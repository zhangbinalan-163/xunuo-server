package com.dabo.xunuo.util;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.SidInfo;

/**
 * SID工具类
 * Created by zhangbin on 16/8/6.
 */
public class SidUtils {

    /**
     * 生成SID
     * @param sidInfo
     * @return
     */
    public static String generateSid(SidInfo sidInfo) throws SysException {
        try {
            String sid=sidInfo.getUserId()+"#"+sidInfo.getLoginTime()+"#"+sidInfo.getDeviceId();
            sid=EncodeUtils.aesEncrypt(sid, Constants.SECRET_KEY);
            return sid;
        } catch (Exception e) {
            throw new SysException(Constants.DEFAULT_MSG_ERROR,Constants.DEFAULT_CODE_ERROR);
        }
    }

    /**
     * 解析SID
     * @param sid
     * @return
     */
    public static SidInfo parseSid(String sid) throws SysException {

        String info= null;
        try {
            info = EncodeUtils.aesDecrypt(sid, Constants.SECRET_KEY);
        } catch (Exception e) {
            throw new SysException(Constants.DEFAULT_MSG_ERROR,Constants.DEFAULT_CODE_ERROR,e);
        }

        String[] tmp=info.split("#");
        if(tmp==null||tmp.length!=3){
            return null;
        }
        long userId=Long.parseLong(tmp[0]);
        long loginTime= Long.parseLong(tmp[1]);
        String deviceId=tmp[2];

        SidInfo sidInfo=new SidInfo();
        sidInfo.setUserId(userId);
        sidInfo.setDeviceId(deviceId);
        sidInfo.setSid(sid);
        sidInfo.setLoginTime(loginTime);

        return sidInfo;
    }
}
