package com.dabo.xunuo.service;

import com.dabo.xunuo.common.exception.SysException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片的业务接口
 * Created by zhangbin on 16/9/7.
 */
public interface IImageService {

    /**
     * 上传图片
     * @param file
     * @return
     *      图片地址
     * @throws SysException
     */
    String uploadImage(MultipartFile file) throws SysException;
}
