package cn.fintecher.system.controller;

import cn.fintecher.system.service.ITestFeignService;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestFeignDemoController
 * @Description TODO
 * @Author coder_bao
 * @Date 2018/8/23 10:17
 */
@RestController
@RequestMapping("/test/feign")
@Api(value = "测试feign", description = "测试feign")
public class TestFeignDemoController {
    @Autowired
    @Qualifier("testFeignServiceDemo")
    ITestFeignService testFeignService;

    @PostMapping("/testper" )
    @ApiOperation(value = "测试feign", notes = "测试feign")
    public ResponseEntity queryUserPermission(@RequestParam String userId){
        try {
            Object object = testFeignService.queryUserPermission(userId);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("菜单加载成功", "test_feign")).body(object);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("test_feign", "The data does not exist", "用户没有配置权限菜单")).body(null);
        }
    }
}
