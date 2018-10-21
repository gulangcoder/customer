package cn.fintecher.util;


import cn.fintecher.util.redis.RedisKeyConstants;
import cn.fintecher.util.redis.RedisUtil;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author menglei
 * date:2018-10-01
 */
public class SeqGeneratorUtils {
    public static final String PREFIX = "CN";

    public synchronized static String customerNo(String companyCode){
        String newCustomerNo = "";
        String companyKey = RedisKeyConstants.COMPANY_CODE+companyCode;
        if(companyCode ==null || "".equals(companyCode)){
            return newCustomerNo;
        }
        if(RedisUtil.hasKey(companyKey)){
            String no = RedisUtil.get(companyKey);
            int no_new = NumberUtils.toInt(no)+1;
            RedisUtil.set(companyKey,no_new+"");
            newCustomerNo = PREFIX+String.format("%06d",no_new);
        } else {
            RedisUtil.set(companyKey,"1");
            newCustomerNo = PREFIX+"000001";
        }
        return newCustomerNo;
    }

    public static void main(String[] args) {
        System.out.println(customerNo("AA"));
    }
}
