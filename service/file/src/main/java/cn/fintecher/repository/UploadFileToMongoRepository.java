package cn.fintecher.repository;

import cn.fintecher.common.file.UploadFileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @Description 文件存储
 * @Author coder_bao
 * @Date 2018/9/28 13:21
 */
public interface UploadFileToMongoRepository extends MongoRepository<UploadFileEntity,String> {
    /**
     * @Description 查询指定公司的文件
     * @Param compayCode
     * @Param pageable
     * @return
     * @Author coder_bao
     * @Date 2018/9/29 10:05
     */
    @Query(value="{'companyCode':?0}")
    public Page<UploadFileEntity> queryUploadFileByCompayCode(String compayCode, Pageable pageable)throws Exception;
}
