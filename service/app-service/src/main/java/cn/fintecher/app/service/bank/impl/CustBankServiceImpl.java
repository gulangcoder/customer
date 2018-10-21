package cn.fintecher.app.service.bank.impl;

import cn.fintecher.app.client.AppFeginUtil;
import cn.fintecher.app.mapper.bank.EntCustBankMapper;
import cn.fintecher.app.model.bank.EntCustBank;
import cn.fintecher.app.model.customer.EntCustomerInfo;
import cn.fintecher.app.service.bank.CustBankService;
import cn.fintecher.app.service.customer.CustomerInfoService;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.sms.EntMessageSendDetail;
import cn.fintecher.common.sms.EntMessageSendType;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.StringUtil;
import cn.fintecher.util.yibao.YeepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

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
public class CustBankServiceImpl implements CustBankService {

    @Autowired
    private EntCustBankMapper entCustBankMapper;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CustomerInfoService customerInfoService;

    /**
     * 鉴权绑卡请求接口
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public Map bindingCard(Map<String,String> param) throws Exception {
        //生成业务号
        String requestno = UUID.randomUUID().toString();
        String identityid = param.get("idCardNo"); //用户标识，身份证号代替
        String idcardno = param.get("idCardNo"); //证件号
        String username = param.get("name"); //用户姓名
        String cardno = param.get("cardno");//银行卡号
        String bankName = param.get("bankName");//银行卡号
        String bankCode = param.get("bankCode");//银行卡号
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String requesttime = format.format(date);//请求时间格式2017-11-01 18:19:20
        //查询是否有正在绑卡
        Map sp = new HashMap();
        sp.put("custId", UserContextUtil.getUserInfo().getUserId());
        sp.put("status",2);//绑卡中
        List<EntCustBank> custBanks = entCustBankMapper.findByCustId(sp);
        if(custBanks.size() > 0 && custBanks != null){
            String id = custBanks.get(0).getId();
            EntCustBank cBank = new EntCustBank();
            cBank.setId(id);
            cBank.setRequestno(requestno);
            cBank.setBankName(bankName);
            cBank.setBankCode(bankCode);
            cBank.setUpdateDate(new Date());
            cBank.setIdcard(idcardno);
            cBank.setBankBindName(username);
            cBank.setBankNo(cardno);
            cBank.setBankType((short) 1);
            cBank.setStatus((short) 2);//绑卡中
            int row = entCustBankMapper.updateByPrimaryKeySelective(cBank);
            //更新请求号
        }else {
            //保存业务请求号
            EntCustBank custBank = new EntCustBank();
            custBank.setId(CreateIDUtil.getId());
            custBank.setCustId(param.get("custId"));
            custBank.setRequestno(requestno);
            custBank.setBankName(bankName);
            custBank.setBankCode(bankCode);
            custBank.setCreateDate(new Date());
            custBank.setIdcard(idcardno);
            custBank.setBankBindName(username);
            custBank.setBankNo(cardno);
            custBank.setBankType((short) 1);
            custBank.setStatus((short) 2);//绑卡中
            //保存用户银行卡
            int row = entCustBankMapper.insertSelective(custBank);
        }
        //String callbackurl = host+"/ybCard/bindingCardTimeOut"; //回调地址
        Map<String,String> ybmap = new HashMap<String, String>();
        String merchantno = param.get("merchantno");
        ybmap.put("merchantno", merchantno);//商户编号
        ybmap.put("requestno", requestno);//业务请号
        ybmap.put("identityid", identityid);//商户生成的唯一标识
        ybmap.put("identitytype", "ID_CARD");//商户生成的唯一标识类型

        ybmap.put("cardno", cardno);//银行卡号
        ybmap.put("idcardno", idcardno);//身份证号码
        ybmap.put("idcardtype", "ID");//身份证类型
        ybmap.put("username", username);//用户姓名
        String tel = param.get("tel");
        ybmap.put("phone", tel);//手机号码
        ybmap.put("issms", "true");//是否存在短验
        ybmap.put("advicesmstype", "MESSAGE");//建议短信发送类型
        ybmap.put("smstemplateid", "");//定制短验模板ID
        ybmap.put("smstempldatemsg", "");//短信模板
        ybmap.put("avaliabletime", "");//绑卡订单有效期
        ybmap.put("requesttime", requesttime);//请求时间
        ybmap.put("authtype", "COMMON_FOUR");//鉴权类型
        ybmap.put("remark", "");//备注
        ybmap.put("extinfos", "");//扩展信息

        Map returnMap = new HashMap();
        //appkey
        String appKey = param.get("appKey");//"SQKK10015908980";
        //secretKey
        String secretKey = param.get("secretKey");//"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCqcnTejUSBAHaFpNiO9p5wsWErV7JcA+ocS8lcdUFiceCIQfumeUHFj1D99o92zuRPY0i6R7NNCg4tAbkh7p82MsGzDbQjRwRF1wvPm25lYbuvGvGSBRWo4Wiih5sRetkwUweJXRAmKdcyK1/S/hOmaUGqZ+P2cfuR7mBtShY8T6Cm2QujlkZoVt+H5XqUQilwvYWcQnvLCnAEjbTzG7xt4umiad4fB9K3h5K59UpYpuFC99ayJApHX9Ds5kqowm43+sJ2TZmD9wC/OFmmXe+Y+9eKQmdNq4KFQSSZW/Yf+R62YbmUMizZTduvODNH2eEMCqJ36mnFbwa4DKeq3krfAgMBAAECggEAM6duHYfoS8PtJ0E209SPXY2T6gOwrMwO5bZd9qQeRYxHRGPitKeotAtjuyM+hP3cGOb4wmM6Rk2W0DLmfQ8Itu8Y3n93qP07oUKzdJ4hDZ5Zt45NyTe7QavV/vNjnh+BtHBlJ07JtxcU/UJBzvpq0BKz8MV9Q1F1sNrx5A0AwhPMcAZ37j736o23+bZ8ipN7f0/7WJ/t/OgCO8/Cx1E3BtD7cdqD94Mv8Xpm+tC/EkSGQBeqngb1z2AZnFxVRd38mcrw5vW0Q2RTkd+JvhYO55ByouMJrsZsskNcfRlYFeJ+JMJNHCC+WsrWB2iODglD3bgc1a6DvPmiCDRh9QS6+QKBgQDlI9kvGlY5mAmZaWdhu2POmEtqBcv3Lm2hn4XmCWbWAdONcNvEQt9kNIDfrZfOqoT/kIf6mC5L4W1U1yFNvoc4mbcsRYRg3quLk0opVePL/atWgVjHG/Y6IR93tILFNqNkbYrZpW3Tc/g/RqhBsaJ+TEhelql2tDXJ4SU+Ab9k6wKBgQC+bVIFLkBEX7qcAn2Na3LXh3EA7+nBgRfjFbH4CuW7+e9+ZEn7XOWoY+6aVg72FFoZbHKVTYlex2tdBzEsyMeLkZzCwEpzCMTaL0fDbZJLVlZ4JkrLAL0+EhNiTxLYAlIAkXQagqNfFgxSty5jrYDypggRaC1nSU0m50tODxGE3QKBgEDdPQXQdsgm+dCrvdA0s7Qv+Gky6uI2CmLOPaE42BuMuM45PHz6UTKUikbHZUnji3Ks/1E48yIX1lNF8u+HF9A181xc8XRalEUWlM/OuIVucaozQ2ZZzAH4jmfceYhKR0aOm9ewtL4+/e8rmUW/ezg1b+cWzzIDIZbcXSaIaB2xAoGARv8XH1tZFqTiPBwplzpCPN0AYqsP6dcpgr6p9aKKeIT8p5DYjGDcNuXbJauENHbuCKCIL+YSm0WaX7q4uMu6qeyGF439s9nHGtmZ1eDaNEBiSLVuGTWTWLsAgxycF/D1hcS1FtUx99eOjKBDKWKcutrwEx1WIDYSD7kYOGghzfkCgYEAzId313Z31+fhVP83qAx+XiyBrTe84Nb7gnxpbEO46IaWVj+YJM2npRCZyVmFTe7cMciZMcD1LTXLsLYKjAVoaBuIoc5zl12FhLn0NlTqtiSPu2sNcOkjrOZgI8ko8yQjsAaKvc25TgeH5JIdlcLsIjBPlCISeq4CwhS/DzOqKUA=";
        //基地址
        String serverRoot = param.get("serverRoot");//"https://open.yeepay.com/yop-center";
        String authbindcardreqUri = param.get("authbindcardreqUri");
        Map<String,String> bindCardMap = new YeepayService().yeepayYOP(ybmap,authbindcardreqUri,appKey,secretKey,serverRoot);
        if (bindCardMap != null&&!bindCardMap.isEmpty()) {
            if("FAIL".equals(bindCardMap.get("status"))){
                String errorcode=bindCardMap.get("errorcode");
                if(StringUtil.isNotEmpty(errorcode)&&errorcode.trim().equals("DK0011111")){
                    //身份证不正确
                    returnMap.put("msg","message.IDCard.fail");
                }else if(StringUtil.isNotEmpty(errorcode)&&errorcode.trim().equals("DK0400002")){
                    //银行卡不正确
                    returnMap.put("msg", "message.bankno.fail");
                }else {
                    //其他异常
                    LoggerCommon.info(this.getClass(),bindCardMap.get("errormsg"));
                    returnMap.put("msg","message.system.errorMessage");
                }
                returnMap.put("flag",false);
            }else if ("BIND_ERROR".equals(bindCardMap.get("status"))) {//重新调重发接口(前端调)
                //绑卡异常 调重发接口
                returnMap.put("msg","message.bindCard.exception");
                returnMap.put("flag",true);
            } else if ("TO_VALIDATE".equals(bindCardMap.get("status"))) { //带短验 调确认接口
                //绑卡请求成功 等待短信
                returnMap.put("msg","message.bindCard.request.success.waitsms");
                returnMap.put("flag",true);
            } else if ("BIND_FAIL".equals(bindCardMap.get("status")) || ("TIME_OUT").equals(bindCardMap.get("status"))) {
                //其他异常
                String eMsg = "message.system.errorMessage";
                if("DKAU00004".equals(bindCardMap.get("errorcode").trim())){
                    eMsg = "message.bindCard.bank.orTel.error";
                }
                if("DKAU00015".equals(bindCardMap.get("errorcode").trim())){
                    eMsg = "message.bindCard.countOut";
                }
                if("DKAU00008".equals(bindCardMap.get("errorcode").trim())){
                    eMsg = "message.bankTel.fail";
                }
                if("DK05000012".equals(bindCardMap.get("errorcode").trim())){
                    eMsg = "message.bank.not.support";
                }
                if("DK0000019".equals(bindCardMap.get("errorcode").trim())){
                    eMsg = "message.bank.more.count";
                }
                LoggerCommon.info(this.getClass(),bindCardMap.get("errormsg"));
                returnMap.put("msg",eMsg);
                returnMap.put("flag",false);
            }else{
                if(StringUtil.isNotEmpty(bindCardMap.get("errormsg"))){
                    //其他异常
                    LoggerCommon.info(this.getClass(),bindCardMap.get("errormsg"));
                    returnMap.put("msg","message.system.errorMessage");
                    returnMap.put("flag",false);
                }
            }
            returnMap.put("status",bindCardMap.get("status"));//将状态返给前台
        } else {
            returnMap.put("msg","message.system.errorMessage");
            returnMap.put("flag",false);
        }

        return returnMap;
    }


    /**
     * 鉴权绑卡重发短信接口
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public Map bindingCardResend(Map<String,String> param) throws Exception {
        Map returnMap = new HashMap();
        //发送验证码
        Map ybmap = new HashMap();
        String custId = param.get("custId");
        //获取之前的请求号
        //查询首次绑卡未成功（绑卡中）生成的业务号
        Map sp = new HashMap();
        sp.put("custId",custId);
        sp.put("status",2);//绑卡中
        List<EntCustBank> custBanks = entCustBankMapper.findByCustId(sp);
        String requestno = "";
        if(custBanks != null && custBanks.size() > 0){
            requestno = custBanks.get(0).getRequestno();//首次绑卡请求的业务号
        }
        ybmap.put("requestno",requestno);//业务请求号
        String merchantno = param.get("merchantno");
        ybmap.put("merchantno", merchantno);//商户编号
        ybmap.put("advicesmstype", "MESSAGE");//短信模板
        String appKey = param.get("appKey");//"SQKK10015908980";
        String secretKey = param.get("secretKey");//"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCqcnTejUSBAHaFpNiO9p5wsWErV7JcA+ocS8lcdUFiceCIQfumeUHFj1D99o92zuRPY0i6R7NNCg4tAbkh7p82MsGzDbQjRwRF1wvPm25lYbuvGvGSBRWo4Wiih5sRetkwUweJXRAmKdcyK1/S/hOmaUGqZ+P2cfuR7mBtShY8T6Cm2QujlkZoVt+H5XqUQilwvYWcQnvLCnAEjbTzG7xt4umiad4fB9K3h5K59UpYpuFC99ayJApHX9Ds5kqowm43+sJ2TZmD9wC/OFmmXe+Y+9eKQmdNq4KFQSSZW/Yf+R62YbmUMizZTduvODNH2eEMCqJ36mnFbwa4DKeq3krfAgMBAAECggEAM6duHYfoS8PtJ0E209SPXY2T6gOwrMwO5bZd9qQeRYxHRGPitKeotAtjuyM+hP3cGOb4wmM6Rk2W0DLmfQ8Itu8Y3n93qP07oUKzdJ4hDZ5Zt45NyTe7QavV/vNjnh+BtHBlJ07JtxcU/UJBzvpq0BKz8MV9Q1F1sNrx5A0AwhPMcAZ37j736o23+bZ8ipN7f0/7WJ/t/OgCO8/Cx1E3BtD7cdqD94Mv8Xpm+tC/EkSGQBeqngb1z2AZnFxVRd38mcrw5vW0Q2RTkd+JvhYO55ByouMJrsZsskNcfRlYFeJ+JMJNHCC+WsrWB2iODglD3bgc1a6DvPmiCDRh9QS6+QKBgQDlI9kvGlY5mAmZaWdhu2POmEtqBcv3Lm2hn4XmCWbWAdONcNvEQt9kNIDfrZfOqoT/kIf6mC5L4W1U1yFNvoc4mbcsRYRg3quLk0opVePL/atWgVjHG/Y6IR93tILFNqNkbYrZpW3Tc/g/RqhBsaJ+TEhelql2tDXJ4SU+Ab9k6wKBgQC+bVIFLkBEX7qcAn2Na3LXh3EA7+nBgRfjFbH4CuW7+e9+ZEn7XOWoY+6aVg72FFoZbHKVTYlex2tdBzEsyMeLkZzCwEpzCMTaL0fDbZJLVlZ4JkrLAL0+EhNiTxLYAlIAkXQagqNfFgxSty5jrYDypggRaC1nSU0m50tODxGE3QKBgEDdPQXQdsgm+dCrvdA0s7Qv+Gky6uI2CmLOPaE42BuMuM45PHz6UTKUikbHZUnji3Ks/1E48yIX1lNF8u+HF9A181xc8XRalEUWlM/OuIVucaozQ2ZZzAH4jmfceYhKR0aOm9ewtL4+/e8rmUW/ezg1b+cWzzIDIZbcXSaIaB2xAoGARv8XH1tZFqTiPBwplzpCPN0AYqsP6dcpgr6p9aKKeIT8p5DYjGDcNuXbJauENHbuCKCIL+YSm0WaX7q4uMu6qeyGF439s9nHGtmZ1eDaNEBiSLVuGTWTWLsAgxycF/D1hcS1FtUx99eOjKBDKWKcutrwEx1WIDYSD7kYOGghzfkCgYEAzId313Z31+fhVP83qAx+XiyBrTe84Nb7gnxpbEO46IaWVj+YJM2npRCZyVmFTe7cMciZMcD1LTXLsLYKjAVoaBuIoc5zl12FhLn0NlTqtiSPu2sNcOkjrOZgI8ko8yQjsAaKvc25TgeH5JIdlcLsIjBPlCISeq4CwhS/DzOqKUA=";
        String serverRoot = param.get("serverRoot");//"https://open.yeepay.com/yop-center";
        String authbindcardresendUri = param.get("authbindcardresendUri");
        try {
            Map bindCardResendMap = new YeepayService().yeepayYOP(ybmap,authbindcardresendUri,appKey,secretKey,serverRoot);
            if (bindCardResendMap != null&&!bindCardResendMap.isEmpty()) {
                if ("FAIL".equals(bindCardResendMap.get("status"))) {
                    returnMap.put("msg",bindCardResendMap.get("errormsg"));
                    returnMap.put("flag",false);
                } else if ("TIME_OUT".equals(bindCardResendMap.get("status"))) {
                    returnMap.put("msg","server.connection.timeout");
                    returnMap.put("flag",false);
                } else if ("TO_VALIDATE".equals(bindCardResendMap.get("status"))) {
                    //这个状态下，是成功的
                    returnMap.put("msg","message.bindCard.request.success.waitsms");
                    returnMap.put("flag",true);
                }
                returnMap.put("status",bindCardResendMap.get("status"));
            } else {
                returnMap.put("msg","message.system.errorMessage");
                returnMap.put("flag",false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnMap;
    }

    /**
     * 鉴权绑卡确认接口
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public Map bindingCardConfirm(Map<String,String> param) throws Exception {
        Map ybmap = new HashMap<String, String>();
        String custId = param.get("custId");
        //获取之前的请求号
        //查询首次绑卡未成功生成的业务号
        Map sp = new HashMap();
        sp.put("custId",custId);
        sp.put("status",2);//绑卡中
        List<EntCustBank> custBanks = entCustBankMapper.findByCustId(sp);
        String requestno = "";
        String bankNO = "";
        if(custBanks != null && custBanks.size() > 0){
            requestno = custBanks.get(0).getRequestno();//首次绑卡请求的业务号
            bankNO = custBanks.get(0).getBankNo();
        }
        String merchantno = param.get("merchantno");
        ybmap.put("merchantno", merchantno);//商户编号
        ybmap.put("requestno", requestno);//业务请号
        String smsCode = param.get("smsCode");
        ybmap.put("validatecode", smsCode);//短信验证码

        Map returnMap = new HashMap();
        ybmap.put("requestno", requestno);//业务请号
        String appKey = param.get("appKey");//"SQKK10015908980";
        String secretKey = param.get("secretKey");//"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCqcnTejUSBAHaFpNiO9p5wsWErV7JcA+ocS8lcdUFiceCIQfumeUHFj1D99o92zuRPY0i6R7NNCg4tAbkh7p82MsGzDbQjRwRF1wvPm25lYbuvGvGSBRWo4Wiih5sRetkwUweJXRAmKdcyK1/S/hOmaUGqZ+P2cfuR7mBtShY8T6Cm2QujlkZoVt+H5XqUQilwvYWcQnvLCnAEjbTzG7xt4umiad4fB9K3h5K59UpYpuFC99ayJApHX9Ds5kqowm43+sJ2TZmD9wC/OFmmXe+Y+9eKQmdNq4KFQSSZW/Yf+R62YbmUMizZTduvODNH2eEMCqJ36mnFbwa4DKeq3krfAgMBAAECggEAM6duHYfoS8PtJ0E209SPXY2T6gOwrMwO5bZd9qQeRYxHRGPitKeotAtjuyM+hP3cGOb4wmM6Rk2W0DLmfQ8Itu8Y3n93qP07oUKzdJ4hDZ5Zt45NyTe7QavV/vNjnh+BtHBlJ07JtxcU/UJBzvpq0BKz8MV9Q1F1sNrx5A0AwhPMcAZ37j736o23+bZ8ipN7f0/7WJ/t/OgCO8/Cx1E3BtD7cdqD94Mv8Xpm+tC/EkSGQBeqngb1z2AZnFxVRd38mcrw5vW0Q2RTkd+JvhYO55ByouMJrsZsskNcfRlYFeJ+JMJNHCC+WsrWB2iODglD3bgc1a6DvPmiCDRh9QS6+QKBgQDlI9kvGlY5mAmZaWdhu2POmEtqBcv3Lm2hn4XmCWbWAdONcNvEQt9kNIDfrZfOqoT/kIf6mC5L4W1U1yFNvoc4mbcsRYRg3quLk0opVePL/atWgVjHG/Y6IR93tILFNqNkbYrZpW3Tc/g/RqhBsaJ+TEhelql2tDXJ4SU+Ab9k6wKBgQC+bVIFLkBEX7qcAn2Na3LXh3EA7+nBgRfjFbH4CuW7+e9+ZEn7XOWoY+6aVg72FFoZbHKVTYlex2tdBzEsyMeLkZzCwEpzCMTaL0fDbZJLVlZ4JkrLAL0+EhNiTxLYAlIAkXQagqNfFgxSty5jrYDypggRaC1nSU0m50tODxGE3QKBgEDdPQXQdsgm+dCrvdA0s7Qv+Gky6uI2CmLOPaE42BuMuM45PHz6UTKUikbHZUnji3Ks/1E48yIX1lNF8u+HF9A181xc8XRalEUWlM/OuIVucaozQ2ZZzAH4jmfceYhKR0aOm9ewtL4+/e8rmUW/ezg1b+cWzzIDIZbcXSaIaB2xAoGARv8XH1tZFqTiPBwplzpCPN0AYqsP6dcpgr6p9aKKeIT8p5DYjGDcNuXbJauENHbuCKCIL+YSm0WaX7q4uMu6qeyGF439s9nHGtmZ1eDaNEBiSLVuGTWTWLsAgxycF/D1hcS1FtUx99eOjKBDKWKcutrwEx1WIDYSD7kYOGghzfkCgYEAzId313Z31+fhVP83qAx+XiyBrTe84Nb7gnxpbEO46IaWVj+YJM2npRCZyVmFTe7cMciZMcD1LTXLsLYKjAVoaBuIoc5zl12FhLn0NlTqtiSPu2sNcOkjrOZgI8ko8yQjsAaKvc25TgeH5JIdlcLsIjBPlCISeq4CwhS/DzOqKUA=";
        String serverRoot = param.get("serverRoot");//"https://open.yeepay.com/yop-center";
        String authbindcardconfirmUri = param.get("authbindcardconfirmUri");
        String eMsg = "";
        try {
            Map<String,String> bindCardConfirmMap = new YeepayService().yeepayYOP(ybmap,authbindcardconfirmUri,appKey,secretKey,serverRoot);
            if (bindCardConfirmMap != null&&!bindCardConfirmMap.isEmpty()) {
                if ("FAIL".equals(bindCardConfirmMap.get("status"))) {//系统异常调 查绑卡关系接口
                    //系统异常
                    LoggerCommon.info(this.getClass(),bindCardConfirmMap.get("errormsg"));
                    returnMap.put("msg","message.system.errorMessage");
                    returnMap.put("flag",false);
                } else if ("BIND_ERROR".equals(bindCardConfirmMap.get("status"))) {//重新调重发接口(前端调)
                    //绑卡失败重发
                    //returnMap.put("msg","message.bindCard.exception");
                    //returnMap.put("flag",true);
                    if("DKAU001019".equals(bindCardConfirmMap.get("errorcode").trim())){
                        eMsg = "message.bank.code.more.error";
                    }
                    LoggerCommon.info(this.getClass(),bindCardConfirmMap.get("errormsg"));
                    returnMap.put("msg",eMsg);
                    returnMap.put("flag",false);
                }else if ("TO_VALIDATE".equals(bindCardConfirmMap.get("status"))) {//重新调重发接口(前端调)
                    //短信验证码错误
                    //returnMap.put("msg","message.bindCard.message.fail");
                    //returnMap.put("flag",true);
                    eMsg = "message.system.errorMessage";
                    if("DKAU00012".equals(bindCardConfirmMap.get("errorcode").trim())){
                        eMsg = "message.code.error";
                    }
                    if("DKAU001019".equals(bindCardConfirmMap.get("errorcode").trim())){
                        eMsg = "message.bank.code.more.error";
                    }
                    LoggerCommon.info(this.getClass(),bindCardConfirmMap.get("errormsg"));
                    returnMap.put("msg",eMsg);
                    returnMap.put("flag",false);
                }else if ("BIND_FAIL".equals(bindCardConfirmMap.get("status")) || ("TIME_OUT").equals(bindCardConfirmMap.get("status"))) {
                    //超时、绑卡失败
                    eMsg = "message.system.errorMessage";
                    if(("TIME_OUT").equals(bindCardConfirmMap.get("status"))){
                        eMsg = "message.bindCard.timeOut";
                    }
                    //其他异常
                    if("DKAU00004".equals(bindCardConfirmMap.get("errorcode").trim())){
                        eMsg = "message.bindCard.bank.orTel.error";
                    }
                    if("DKAU00015".equals(bindCardConfirmMap.get("errorcode").trim())){
                        eMsg = "message.bindCard.countOut";
                    }
                    if("DKAU00008".equals(bindCardConfirmMap.get("errorcode").trim())){
                        eMsg = "message.bankTel.fail";
                    }
                    if("DK05000012".equals(bindCardConfirmMap.get("errorcode").trim())){
                        eMsg = "message.bank.not.support";
                    }
                    if("DK0000019".equals(bindCardConfirmMap.get("errorcode").trim())){
                        eMsg = "message.bank.more.count";
                    }
                    LoggerCommon.info(this.getClass(),bindCardConfirmMap.get("errormsg"));
                    returnMap.put("msg",eMsg);
                    returnMap.put("flag",false);
                }else if("BIND_SUCCESS".equals(bindCardConfirmMap.get("status"))){//绑卡成功
                    //修改用户绑卡状态
                    entCustBankMapper.updateBankStatus(custId);
                    Map maps = new HashMap();
                    maps.put("custId",custId);
                    maps.put("requestno",requestno);
                    List<EntCustBank> custBankList = entCustBankMapper.findByCustId(maps);
                    if(custBankList != null && custBankList.size() > 0){
                        EntCustBank custBank=custBankList.get(0);
                        custBank.setStatus((short) 1);
                        custBank.setUpdateDate(new Date());
                        entCustBankMapper.updateByPrimaryKeySelective(custBank);
                    }
                    //绑卡成功 发送短信
                    EntCustomerInfo customerInfo = customerInfoService.getCustomerInfoById(UserContextUtil.getUserInfo().getUserId());
                    EntMessageSendDetail entMessageSendDetail = new EntMessageSendDetail();
                    entMessageSendDetail.setMsgType(EntMessageSendType.ENT_MSG_TIE_CARD);
                    entMessageSendDetail.setCustomerId(customerInfo.getCustId());
                    entMessageSendDetail.setCompanyCode(customerInfo.getCompanyCode());
                    entMessageSendDetail.setCustomerTel(UserContextUtil.getUserInfo().getTel());
                    entMessageSendDetail.setLastNumber("**"+bankNO.substring(bankNO.length()-4,bankNO.length()));
                    entMessageSendDetail.setCustomerName(customerInfo.getRealName());
                    restTemplate.postForEntity(AppFeginUtil.SYSTEMWEB_API_SAVEMESTEMPLDETAIL_API,entMessageSendDetail,Object.class);
                    returnMap.put("msg","message.bindCard.success");
                    returnMap.put("flag",true);
                }else{
                    if(StringUtil.isNotEmpty(bindCardConfirmMap.get("errormsg"))){
                        //其他异常
                        LoggerCommon.info(this.getClass(),bindCardConfirmMap.get("errormsg"));
                        returnMap.put("msg","message.system.errorMessage");
                        returnMap.put("flag",false);
                    }
                }
                returnMap.put("status",bindCardConfirmMap.get("status"));//将状态返给前台
            } else {
                returnMap.put("msg","message.system.errorMessage");
                returnMap.put("flag",false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnMap;
    }

    /**
     * 根据用户id查询用户银行卡列表
     * @param custId
     * @return
     * @throws Exception
     */
    @Override
    public List<EntCustBank> getCustBankList(String custId) throws Exception {
        Map param = new HashMap();
        param.put("custId",custId);
        param.put("status",1);
        return entCustBankMapper.findByCustId(param);
    }

}