package cn.fintecher.system.service.impl;

import cn.fintecher.system.client.ManagerFeignUtil;
import cn.fintecher.system.service.ITestFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName TestFeignServiceImpl
 * @Description TODO
 * @Author coder_bao
 * @Date 2018/8/23 11:01
 */
@Service("testFeignServiceDemo")
public class TestFeignServiceImpl implements ITestFeignService {
    @Autowired
    RestTemplate restTemplate;
    /**
    *@Description get方式跨服务调用api
    *@Param 
    *@return 
    *@Author coder_bao
    *@Date  
    **/
    @Override
    public Object queryUserPermission(@RequestParam String userId) throws Exception {
        ResponseEntity<Object> listResponseEntity = restTemplate.getForEntity
                (ManagerFeignUtil.MANAGER_API_SYSLOGIN_QUERYUSERPERMISSION_API+"?userId="+userId,Object.class);
        return listResponseEntity.getBody();
    }
}
