package cn.fintecher.service;

import cn.fintecher.common.file.UploadFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description mongoDB文件服务器接口，处理小于16M的文件
 * @Author coder_bao
 * @Date 2018/9/28 13:24
 */
public interface UploadFileToMongoService {
    /**
     * @Description 保存文件
     * @Param 
     * @return 
     * @Author coder_bao
     * @Date 2018/9/28 13:44
     */
    UploadFileEntity saveFile(MultipartFile file) throws Exception;
    /**
     * @Description 根据id删除文件
     * @Param 
     * @return 
     * @Author coder_bao
     * @Date 2018/9/28 13:44
     */
    void removeFile(String id) throws Exception;
    /**
     * @Description 根据id获取文件
     * @Param 
     * @return 
     * @Author coder_bao
     * @Date 2018/9/28 13:43
     */
    UploadFileEntity getFileById(String id) throws Exception;
    /**
     * @Description 分页查询，按上传时间降序
     * @Param companyCode
     * @Param pageIndex
     * @Param pageSize
     * @return 
     * @Author coder_bao
     * @Date 2018/9/28 13:43
     */
    List<UploadFileEntity> listFilesByPage(String companyCode,int pageIndex, int pageSize) throws Exception;



}
