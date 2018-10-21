package cn.fintecher.scheduler.service.impl;

import cn.fintecher.scheduler.mapper.TaskDispersedLockMapper;
import cn.fintecher.scheduler.service.TaskDispersedLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月20日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@Service
public class TaskDispersedLockServiceImpl implements TaskDispersedLockService {

    @Autowired
    private TaskDispersedLockMapper taskDispersedLockMapper;

    @Override
    public int updateTask(String className, Long utime) {
        Map param = new HashMap();
        param.put("className",className);
        param.put("utime",utime);
        return taskDispersedLockMapper.updateTask(param);
    }
}