package cn.fintecher.service;

import cn.fintecher.common.file.UploadFileEntity;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @Description 处理大于16M文件接口
 * @Author coder_bao
 * @Date 2018/9/28 13:48
 */
public interface UploadFileCridFsService {
    /**
     * 保存文件
     *
     * @return
     */
    UploadFileEntity uploadFile(MultipartFile file) throws Exception;

    /**
     * 删除文件
     *
     * @return
     */
    void removeFile(String id)throws Exception;

    /**
     * 根据id获取文件列表信息
     *
     * @return
     */
    UploadFileEntity getFileById(String id)throws Exception;

    /**
     * 获取文件内容
     *
     * @param id
     * @return
     */
    GridFSDBFile getFileContent(String id)throws Exception;

    /**
     * @Description 分页查询big File
     * @Param
     * @return
     * @Author coder_bao
     * @Date 2018/9/29 10:15
     */
    List<UploadFileEntity> getUploadCridFsFileList(String companyCode, int pageIndex, int pageSize)throws Exception;

    /**
     *
     * @param inputStream
     * @param userId
     * @param userName
     * @param batchNum
     */
    void uploadCaseFileReduce(InputStream inputStream, String userId, String userName, String batchNum, String companyCode) throws Exception ;
}
