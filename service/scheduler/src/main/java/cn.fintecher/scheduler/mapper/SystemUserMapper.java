package cn.fintecher.scheduler.mapper;

import cn.fintecher.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description TODO
 * @Author coder_bao
 * @Date 2018/9/30 15:40
 */
@Mapper
public interface SystemUserMapper {
    List<User> getLockedUserList()throws Exception;
    void unLockUser(List<String> list)throws Exception;
}
