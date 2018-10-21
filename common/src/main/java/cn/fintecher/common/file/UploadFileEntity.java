package cn.fintecher.common.file;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


/**
 * 上传文件信息
 * Created by ChenChang on 2017/3/7.
 */
@Data
@Document
public class UploadFileEntity implements Serializable{
    @Id
    private String id;
    private String cridFsId;//big file存储主键
    private String realName;//文件名称
    private String type;//文件类型
    private String url;//存放路径
    private String localUrl;//内网路径
    private String name;//文件别名
    private Long size;//文件大小
    private byte[] content; // 文件内容
    private Date createTime;//创建时间
    private String creator;
    private String companyCode;//文件所属公司


}
