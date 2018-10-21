package cn.fintecher.app.mapper.customer;

import cn.fintecher.app.model.customer.EntCustFile;
import cn.fintecher.app.model.customer.EntProduct;
import cn.fintecher.app.model.customer.EntProductVideo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface EntCustFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntCustFile record);

    int insertSelective(List<EntCustFile> record);

    EntCustFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntCustFile record);

    int updateByPrimaryKey(EntCustFile record);


    /**
     * 查询用户文件
     * @param param
     * @return
     */
    List<EntCustFile> findCustFile(Map param);

    /**
     * 根据 cust_id and company_code and file_type删除用户文件
     * @param param
     * @return
     */
    int deleteCustomerFile(Map param);

    EntProduct selectProductById(String id);

    List<EntProductVideo> selectVideoByMap(Map map);
}