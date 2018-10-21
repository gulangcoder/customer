package cn.fintecher.system.service.impl;

import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.system.mapper.EntMessageTempletMapper;
import cn.fintecher.system.model.EntMessageTemplet;
import cn.fintecher.system.service.EntMessageTempletService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月04日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@Service
public class EntMessageTempletServiceImpl implements EntMessageTempletService {

    @Autowired
    private EntMessageTempletMapper entMessageTempletMapper;

    @Override
    public List<Map> getMsgTemplList(String companyCode, String msgType, String title, Short status)throws Exception {
        Map param = new HashMap();
        param.put("companyCode",companyCode);
        param.put("msgType",msgType);
        param.put("title",title);
        param.put("status",status);
        List<Map> list = entMessageTempletMapper.findList(param);

        return list;
    }

    @Override
    public EntMessageTemplet getMsgTemplById(String id) throws Exception{
        return entMessageTempletMapper.selectByPrimaryKey(id);
    }


    /**
     * 保存
     * @param entMessageTemplet
     * @return
     */
    @Override
    public Map saveMessageTemplet(EntMessageTemplet entMessageTemplet) throws Exception{
        Map respMap = new HashMap();
        //判断一个企业一种类型的模板只有一个
        Map param = new HashMap();
        param.put("companyCode",entMessageTemplet.getCompanyCode());
        param.put("msgType",entMessageTemplet.getMsgType());
        List<Map>list = entMessageTempletMapper.findList(param);
        if(list.size()>0){
            respMap.put("flag",false);
            respMap.put("msg","message.msgTempl.existed");
            return respMap;
        }
        entMessageTemplet.setCreateTime(new Date());
        entMessageTemplet.setId(CreateIDUtil.getId());
        int row = entMessageTempletMapper.insertSelective(entMessageTemplet);
        if(row>0){
            respMap.put("flag",true);
            respMap.put("msg","message.system.save.success");
            return respMap;
        }
        respMap.put("flag",false);
        respMap.put("msg","message.system.save.fail");
        return respMap;
    }


    /**
     * 修改
     * @param entMessageTemplet
     * @return
     */
    @Override
    public Map updateMessageTemplet(EntMessageTemplet entMessageTemplet) throws Exception{

        Map respMap = new HashMap();
        if(StringUtil.isEmpty(entMessageTemplet.getId())){
            respMap.put("flag",false);
            respMap.put("msg","message.system.request.param.exception");
            return respMap;
        }
        //判断一个企业一种类型的模板只有一个
        Map param = new HashMap();
        param.put("companyCode",entMessageTemplet.getCompanyCode());
        param.put("msgType",entMessageTemplet.getMsgType());
        List<Map>list = entMessageTempletMapper.findList(param);
        boolean flag = false;
        for (Map ms:list) {
            if(!ms.get("id").toString().equals(entMessageTemplet.getId())){
                flag=true;
                break;
            }
        }
        if(flag){
            respMap.put("flag",false);
            respMap.put("msg","message.msgTempl.existed");
            return respMap;
        }
        entMessageTemplet.setUpdateTime(new Date());
        int row = entMessageTempletMapper.updateByPrimaryKeySelective(entMessageTemplet);
        if(row>0){
            respMap.put("flag",true);
            respMap.put("msg","message.system.update.success");
            return respMap;
        }
        respMap.put("flag",false);
        respMap.put("msg","message.system.update.fail");
        return respMap;
    }


    /**
     * 修改状态
     * @param data
     * @return
     */
    @Override
    public Map updateStatus(Map data) throws Exception{
        Map respMap = new HashMap();
        if(null==data.get("id")||StringUtil.isEmpty(data.get("id").toString())){
            respMap.put("flag",false);
            respMap.put("msg","message.system.request.param.exception");
            return respMap;
        }

        if(null==data.get("status")||StringUtil.isEmpty(data.get("status").toString())){
            respMap.put("flag",false);
            respMap.put("msg","message.system.request.param.exception");
            return respMap;
        }
        EntMessageTemplet entMessageTemplet = new EntMessageTemplet();
        entMessageTemplet.setId(data.get("id").toString());
        entMessageTemplet.setStatus(Short.parseShort(data.get("status").toString()));
        entMessageTemplet.setUpdateUser(data.get("updateUser").toString());
        entMessageTemplet.setUpdateTime(new Date());
        int row = entMessageTempletMapper.updateByPrimaryKeySelective(entMessageTemplet);
        if(row>0){
            respMap.put("flag",true);
            respMap.put("msg","message.system.operation.success");
            return respMap;
        }
        respMap.put("flag",false);
        respMap.put("msg","message.system.operation.fail");
        return respMap;
    }

    @Override
    public EntMessageTemplet getMsgTemplDetailById(String id) throws Exception {
        Map map = new HashMap();
        map.put("id",id);
        map.put("companyCode", UserContextUtil.getUserInfo().getCompanyCode());
        return entMessageTempletMapper.getMsgTemplDetailById(map);
    }

    @Override
    public List<Map> getTempDetailList(Map param) {
        return entMessageTempletMapper.getTempDetailList(param);
    }
}