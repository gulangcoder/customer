package cn.fintecher.service.impl;

import cn.fintecher.common.file.UploadFileEntity;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.repository.UploadFileToMongoRepository;
import cn.fintecher.service.UploadFileCridFsService;
import cn.fintecher.service.UploadFileToMongoService;
import cn.fintecher.util.DateUtil;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author coder_bao
 * @Date 2018/9/28 13:50
 */
@Service
public class UploadFileCridFsServiceImpl implements UploadFileCridFsService {
    @Autowired
    UploadFileToMongoRepository uploadFileRepository;
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Value("${gridfs.path}")
    private String path;
    @Autowired
    private UploadFileToMongoService uploadFileToMongoService;

    /**
     * 获取外网地址
     */
    private String getResAccessUrl(String fid) throws Exception{
        String fileUrl = "http://".concat(path).concat("/api/fileUpload/downFileCridFs/").concat(fid);
        return fileUrl;
    }

    @Override
    public UploadFileEntity uploadFile(MultipartFile file) throws Exception {
        UploadFileEntity uploadFileEntity = upload(file);
        GridFSFile gridFSFile = gridFsTemplate.store(new ByteArrayInputStream(file.getBytes()),uploadFileEntity.getId(),uploadFileEntity.getType());
        if (gridFSFile!=null){
            uploadFileEntity.setCridFsId(gridFSFile.getId().toString());
        }
        uploadFileEntity.setUrl(getResAccessUrl(uploadFileEntity.getId()));
        uploadFileEntity = uploadFileRepository.save(uploadFileEntity);
        return uploadFileEntity;
    }

    @Override
    public void removeFile(String id) throws Exception{
        UploadFileEntity uploadFileEntity = getFileById(id);
        uploadFileRepository.delete(id);
        Query query = Query.query(GridFsCriteria.whereFilename().is(uploadFileEntity.getCridFsId()));
        gridFsTemplate.delete(query);
    }

    @Override
    public UploadFileEntity getFileById(String id) throws Exception{

        return uploadFileRepository.findOne(id);
    }

    @Override
    public GridFSDBFile getFileContent(String id) throws Exception{
        Query query = Query.query(GridFsCriteria.whereFilename().is(id));
        return gridFsTemplate.findOne(query);
    }

    @Override
    public List<UploadFileEntity> getUploadCridFsFileList(String companyCode, int pageIndex, int pageSize) throws Exception {
        return uploadFileToMongoService.listFilesByPage(companyCode,pageIndex,pageSize);
    }

    @Override
    public void uploadCaseFileReduce(InputStream inputStream, String userId, String userName, String batchNum, String companyCode) throws Exception {

    }

    private UploadFileEntity upload(MultipartFile file) throws Exception{
        UserInfo userInfo = UserContextUtil.getUserInfo();
        UploadFileEntity uploadFileEntity = new UploadFileEntity();
        if (userInfo!=null){
            String companyCode = userInfo.getCompanyCode();
            uploadFileEntity.setCreator(userInfo.getAccount());
            uploadFileEntity.setCompanyCode(companyCode);
            uploadFileEntity.setName(companyCode==null?"":companyCode+"_"+file.getOriginalFilename());
        }else{
            uploadFileEntity.setCreator(null);
            uploadFileEntity.setCompanyCode(null);
            uploadFileEntity.setName(file.getOriginalFilename());
        }
        uploadFileEntity.setSize(file.getSize());
        uploadFileEntity.setType(FilenameUtils.getExtension(file.getOriginalFilename()));
        uploadFileEntity.setRealName(file.getOriginalFilename());
        uploadFileEntity.setCreateTime(new Date());
        uploadFileEntity = uploadFileRepository.save(uploadFileEntity);
        return uploadFileEntity;
    }
}
