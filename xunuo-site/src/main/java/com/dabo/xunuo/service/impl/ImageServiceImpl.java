package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.service.IImageService;
import com.dabo.xunuo.util.JsonUtils;
import com.dabo.xunuo.util.StringUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * 图片业务实现类
 * 存到七牛云中
 * Created by zhangbin on 16/9/7.
 */
@Service
public class ImageServiceImpl implements IImageService{
    private static Logger LOG= LoggerFactory.getLogger(ImageServiceImpl.class);

    private static final String QINIU_ACCESS_KEY="EG-eMLPQ_HcCW8VfPHUDc276KG1-XsvE6nu1eZiK";

    private static final String QINIU_SECRET_KEY="yarvGWmyfVV4hgH4QQ9AX5DqPEpOAG2A_ONrTfJl";

    private static final String QINIU_BUCKET="bucket-head";

    private static final String QINIU_DOMAIN="http://od4azqx23.bkt.clouddn.com";


    private Auth auth;

    private UploadManager uploadManager;

    @Override
    public String uploadImage(MultipartFile file) throws SysException {
        //先放到临时位置
        String fileName = file.getOriginalFilename();
        String extType=fileName.substring(fileName.lastIndexOf("."));
        String newFileName= StringUtils.md5(fileName+System.currentTimeMillis())+extType;
        String path="tmp/upload";
        File tmpFile = new File(path,newFileName);
        //保存
        try {
            file.transferTo(tmpFile);
        } catch (IOException e) {
            throw new SysException("文件上传失败",e);
        }
        //上传到七牛
        uploadToQiniu(tmpFile.getAbsolutePath(),newFileName);
        //删除临时文件
        tmpFile.delete();

        return QINIU_DOMAIN+"/"+newFileName;
    }

    @PostConstruct
    private void init(){
        //密钥配置
        auth = Auth.create(QINIU_ACCESS_KEY, QINIU_SECRET_KEY);
        //创建上传对象
        uploadManager = new UploadManager();
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    private String getUpToken(){
        return auth.uploadToken(QINIU_BUCKET);
    }

    /**
     * 上传图片到七牛云中
     * @param filePath
     * @throws IOException
     */
    private void uploadToQiniu(String filePath,String targetName) throws SysException {
        try {
            //调用put方法上传
            uploadManager.put(filePath, targetName, getUpToken());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            LOG.error("upload to qiniu fail,filepath={},key={},res={}",filePath,targetName, JsonUtils.fromObject(r));
            throw new SysException("文件上传失败");
        }
    }
}
