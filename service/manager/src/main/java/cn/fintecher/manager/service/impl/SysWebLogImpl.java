package cn.fintecher.manager.service.impl;

import cn.fintecher.manager.mapper.SysWebLogMapper;
import cn.fintecher.common.logger.SysWebLog;
import cn.fintecher.manager.model.SysWebLogRequest;
import cn.fintecher.manager.service.SysWebLogService;
import cn.fintecher.util.CreateIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SysWebLogImpl
 * @Description
 * @Author coder_bao
 * @Date 2018/9/5 11:29
 */
@Service
public class SysWebLogImpl implements SysWebLogService {
    @Autowired
    private SysWebLogMapper sysWebLogMapper;
    @Override
    public void addSysWebLogInfo(SysWebLog sysWebLog) throws Exception {
        if (sysWebLog!=null){
            Date now = new Date();
            sysWebLog.setCreateTime(now);
            sysWebLog.setOperateTime(new Date());
            sysWebLog.setIsDelete(0);
            sysWebLogMapper.addSysWebLogInfo(sysWebLog);
        }else{
            throw new Exception();
        }
    }

    @Override
    public List<SysWebLog> getSysWebLogList(SysWebLogRequest sysWebLog) throws Exception {
        return sysWebLogMapper.getSysWebLogList(sysWebLog);
    }

    @Override
    public String getExceptionStackMsgById(String id) throws Exception {
        return sysWebLogMapper.getExceptionStackMsgById(id);
    }
}
