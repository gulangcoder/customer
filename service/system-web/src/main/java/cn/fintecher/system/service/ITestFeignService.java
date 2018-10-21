package cn.fintecher.system.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ITestFeignService {

    public Object queryUserPermission(@RequestParam String userId)throws Exception;
}
