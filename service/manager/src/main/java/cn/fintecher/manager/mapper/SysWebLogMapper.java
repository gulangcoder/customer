package cn.fintecher.manager.mapper;

import cn.fintecher.common.logger.SysWebLog;
import cn.fintecher.manager.model.SysWebLogRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysWebLogMapper {
    void addSysWebLogInfo(SysWebLog sysWebLog)throws Exception;

    List<SysWebLog> getSysWebLogList(SysWebLogRequest sysWebLog)throws Exception;

    String getExceptionStackMsgById(String id)throws Exception;
}
