package cn.fintecher;

import cn.fintecher.common.constant.AppConstant;
import cn.fintecher.database.DynamicDataSourceRegister;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.amqp.core.Queue;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * @ClassName BaseAppServiceApplication
 * @Description TODO
 * @Author coder_bao
 * @Date 2018/9/7 17:35
 */
@EnableAsync
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@Import({DynamicDataSourceRegister.class}) // 注册动态多数据源
public class BaseAppServiceApplication {
    private static final Logger log = LoggerFactory.getLogger(BaseAppServiceApplication.class);

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public Queue unReduceSuccessQueue() {
        return new Queue(AppConstant.DATAINFO_CONFIRM_QE);
    }
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("returnPageInfo", "check");
        p.setProperty("params", "count=countSql");
        pageHelper.setProperties(p);
        return pageHelper;
    }
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(BaseAppServiceApplication.class,args);
    }
}
