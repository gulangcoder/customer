package cn.fintecher.app.service.customer.impl;

import cn.fintecher.app.client.AppFeginUtil;
import cn.fintecher.app.mapper.customer.EntCustomerMapper;
import cn.fintecher.app.model.customer.EntCustomer;
import cn.fintecher.app.model.customer.EntCustomerInfo;
import cn.fintecher.app.service.customer.CustomerInfoService;
import cn.fintecher.app.service.customer.UpdatePasswordService;
import cn.fintecher.common.sms.EntMessageSendDetail;
import cn.fintecher.common.sms.EntMessageSendType;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/17
 * @Description:
 */
@Service
public class UpdatePasswordServiceImpl implements UpdatePasswordService{

    @Autowired
    private EntCustomerMapper customerMapper;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CustomerInfoService customerInfoService;

    @Override
    public Map isRealnameVerify(Map map)  throws Exception{
        //TODO : 先根据手机号查询用户是否存在，然后判断是否认证
        Map restultMap = new HashMap();
        EntCustomerInfo customerInfo = customerMapper.selectCustomerInfoByMap(map);
        if(null == customerInfo){
            restultMap.put("flag",false);
            restultMap.put("msg","message.customer.not.exist");
            return restultMap;
        }
        String idcard = "";
        if(null!=map.get("idcardNum")){
            idcard = map.get("idcardNum").toString();
        }
        //已认证
        if(customerInfo.getRealnameVerify() == 0){
            if(!idcard.equals(customerInfo.getIdcardNum())){
                restultMap.put("flag",false);
                restultMap.put("msg","message.customer.idcardNum.error");
                return restultMap;
            }
        }else {
            if(!"".equals(idcard.trim())){
                restultMap.put("flag",false);
                restultMap.put("msg","message.not.inut.idcardNum");
                return restultMap;
            }
        }
        restultMap.put("flag",true);
        restultMap.put("msg","");
        return restultMap;
    }

    @Transactional
    @Override
    public int updatePassword(Map map) throws Exception {
        String password = map.get("password").toString();
        String newPassword = MD5Util.GetMD5Code(password);
        map.put("newPassword",newPassword);
        int updRow = customerMapper.updatePasswordByMap(map);
        return updRow;
    }

    @Transactional
    @Override
    public Map resetPassword(Map map) throws Exception {
        Map returnMap = new HashMap();
        UserInfo userInfo = UserContextUtil.getUserInfo();
        map.put("custId", userInfo.getUserId());
        String oldPassword = map.get("oldPassword").toString();
        String md5OldPassword = MD5Util.GetMD5Code(oldPassword);
        List<EntCustomer> customerList = customerMapper.selectCustomerByMap(map);
        if (customerList == null || customerList.size() == 0) {
            returnMap.put("flag", false);
            returnMap.put("msg", "message.user.not.exist");
            return returnMap;
        }
        EntCustomer customer = customerList.get(0);
        if (!md5OldPassword.equals(customer.getPassword())) {
            returnMap.put("flag", false);
            returnMap.put("msg", "message.old.password.error");
            return returnMap;
        }
        String password = map.get("newPassword").toString();
        String newPasswords = MD5Util.GetMD5Code(password);
        EntCustomer ct = new EntCustomer();
        ct.setId(customer.getId());
        ct.setPassword(newPasswords);
        ct.setUpdateTime(new Date());
        int updRow = customerMapper.updateByPrimaryKeySelective(ct);
        if(updRow != 1){
            returnMap.put("flag", false);
            returnMap.put("msg", "message.system.update.fail");
            return returnMap;
        }
        //修改密码成功，发送站内信
        EntCustomerInfo customerInfo = customerInfoService.getCustomerInfoById(userInfo.getUserId());
        if(null == customerInfo){
            returnMap.put("flag", false);
            returnMap.put("msg", "message.user.not.exist");
            return returnMap;
        }
        EntMessageSendDetail entMessageSendDetail = new EntMessageSendDetail();
        entMessageSendDetail.setMsgType(EntMessageSendType.ENT_MSG_UPDATE_PSD);
        entMessageSendDetail.setCustomerId(userInfo.getUserId());
        entMessageSendDetail.setCompanyCode(userInfo.getCompanyCode());
        entMessageSendDetail.setCustomerTel(customer.getPhone());
        entMessageSendDetail.setCustomerName(null==customerInfo.getRealName()?customer.getPhone():customerInfo.getRealName());
        restTemplate.postForEntity(AppFeginUtil.SYSTEMWEB_API_SAVEMESTEMPLDETAIL_API,entMessageSendDetail,Object.class);
        returnMap.put("flag", true);
        returnMap.put("msg", "message.system.update.success");
        return returnMap;
    }
}
