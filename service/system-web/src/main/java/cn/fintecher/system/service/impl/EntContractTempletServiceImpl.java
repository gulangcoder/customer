package cn.fintecher.system.service.impl;

import cn.fintecher.system.mapper.EntContractTempletMapper;
import cn.fintecher.system.model.EntContractTemplet;
import cn.fintecher.system.service.EntContractTempletService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Title :
 * Description : @合同管理@
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
public class EntContractTempletServiceImpl implements EntContractTempletService {


    @Autowired
    private EntContractTempletMapper entContractTempletMapper;

    @Override
    public List<Map> getContractTemplList(String companyCode, String title, Short status, String contractType)throws Exception {
        Map param = new HashMap();
        param.put("companyCode",companyCode);
        param.put("title",title);
        param.put("status",status);
        param.put("contractType",contractType);
        List<Map> list = entContractTempletMapper.findList(param);
        return list;
    }

    @Override
    public Map getConractTemplById(String id) throws Exception{
        Map map = new HashMap();
        EntContractTemplet contractTemplet = entContractTempletMapper.selectByPrimaryKey(id);
        if(contractTemplet!=null){
            map.put("id",contractTemplet.getId());
            map.put("companyCode",contractTemplet.getCompanyCode());
            map.put("contractType",contractTemplet.getContractType());
            map.put("title",contractTemplet.getTitle());
            map.put("status",contractTemplet.getStatus());
            map.put("content",contractTemplet.getContent());
            map.put("version",contractTemplet.getVersion());
        }
        return map;
    }


    /**
     * 保存
     * @param entContractTemplet
     * @return
     */
    @Override
    public Map saveContractTemplet(EntContractTemplet entContractTemplet)throws Exception {
        Map respMap = new HashMap();
        //同一企业下同一类型模板不能重复
        Map param = new HashMap();
        param.put("companyCode",entContractTemplet.getCompanyCode());
        param.put("contractType",entContractTemplet.getContractType());
        List<Map>list = entContractTempletMapper.findList(param);
        if(list!=null&&list.size()>0){
            respMap.put("msg","message.contractTempl.existed");
            respMap.put("flag",false);
            return respMap;
        }
        Date now = new Date();
        entContractTemplet.setId(CreateIDUtil.getId());
        entContractTemplet.setCreateTime(now);
        int row = entContractTempletMapper.insertSelective(entContractTemplet);
        if(row>0){
            respMap.put("flag",true);
            respMap.put("msg","message.system.save.success");
            return respMap;
        }
        respMap.put("flag",false);
        respMap.put("msg","message.system.save.fail");
        return respMap;
    }

    @Override
    public Map updateContractTemplet(EntContractTemplet entContractTemplet) throws Exception{
        Map respMap = new HashMap();
        if(StringUtil.isEmpty(entContractTemplet.getId())){
            respMap.put("flag",false);
            respMap.put("msg","message.system.request.param.exception");
            return respMap;
        }
        //同一企业下同一类型模板不能重复
        Map param = new HashMap();
        param.put("companyCode",entContractTemplet.getCompanyCode());
        param.put("contractType",entContractTemplet.getContractType());
        List<Map>list = entContractTempletMapper.findList(param);
        boolean flag = false;
        for (Map ent:list) {
            if(!ent.get("id").toString().equals(entContractTemplet.getId())){
                flag = true;
                break;
            }
        }
        if(flag){
            respMap.put("msg","message.contractTempl.existed");
            respMap.put("flag",false);
            return respMap;
        }
        Date now = new Date();
        entContractTemplet.setUpdateTime(now);
        int row= entContractTempletMapper.updateByPrimaryKeySelective(entContractTemplet);
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
        EntContractTemplet templet = new EntContractTemplet();
        templet.setId(data.get("id").toString());
        templet.setStatus(Short.parseShort(data.get("status").toString()));
        templet.setUpdateUser(data.get("updateUser").toString());
        templet.setUpdateTime(new Date());
        int row = entContractTempletMapper.updateByPrimaryKeySelective(templet);
        if(row>0){
            respMap.put("flag",true);
            respMap.put("msg","message.system.operation.success");
            return respMap;
        }
        respMap.put("flag",false);
        respMap.put("msg","message.system.operation.fail");
        return respMap;
    }
}