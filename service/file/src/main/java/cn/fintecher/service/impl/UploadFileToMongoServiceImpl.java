package cn.fintecher.service.impl;

import cn.fintecher.common.file.UploadFileEntity;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.repository.UploadFileToMongoRepository;
import cn.fintecher.service.UploadFileToMongoService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author coder_bao
 * @Date 2018/9/28 13:46
 */
@Service
public class UploadFileToMongoServiceImpl implements UploadFileToMongoService {
    @Autowired
    private UploadFileToMongoRepository uploadFileToMongoRepository;
    @Value("${gridfs.path}")
    private String path;

    @Override
    public UploadFileEntity saveFile(MultipartFile file) throws Exception{
        UserInfo userInfo = UserContextUtil.getUserInfo();
        UploadFileEntity uploadFileEntity = new UploadFileEntity();
        uploadFileEntity.setSize(file.getSize());
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
        uploadFileEntity.setType(FilenameUtils.getExtension(file.getOriginalFilename()));
        uploadFileEntity.setRealName(file.getOriginalFilename());
        uploadFileEntity.setCreateTime(new Date());
        uploadFileEntity.setContent(file.getBytes());
        uploadFileEntity = uploadFileToMongoRepository.save(uploadFileEntity);
        uploadFileEntity.setUrl(getResAccessUrl(uploadFileEntity.getId()));
        return uploadFileToMongoRepository.save(uploadFileEntity);
    }

    @Override
    public void removeFile(String id) {
        uploadFileToMongoRepository.delete(id);
    }

    @Override
    public UploadFileEntity getFileById(String id) {
        return uploadFileToMongoRepository.findOne(id);
    }

    @Override
    public List<UploadFileEntity> listFilesByPage(String companyCode,int pageIndex, int pageSize) throws Exception{
        Page<UploadFileEntity> page = null;
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
        if (companyCode==null||"".equals(companyCode)){
            page = uploadFileToMongoRepository.findAll(pageable);
        }else{
            page = uploadFileToMongoRepository.queryUploadFileByCompayCode(companyCode,pageable);
        }
        return page.getContent();
    }

    /**
     * 获取外网地址
     */
    private String getResAccessUrl(String fid) throws Exception{
        String fileUrl = "http://".concat(path).concat("/api/fileUpload/downFile/").concat(fid);
        return fileUrl;
    }
}
