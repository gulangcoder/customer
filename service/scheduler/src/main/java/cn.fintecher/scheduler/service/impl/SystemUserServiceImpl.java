package cn.fintecher.scheduler.service.impl;

import cn.fintecher.model.User;
import cn.fintecher.scheduler.mapper.SystemUserMapper;
import cn.fintecher.scheduler.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author coder_bao
 * @Date 2018/9/30 15:39
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Override
    public List<User> getLockedUserList() throws Exception {
        return systemUserMapper.getLockedUserList();
    }

    @Override
    public void unLockUser(List<String> list) throws Exception {
        systemUserMapper.unLockUser(list);
    }
}
