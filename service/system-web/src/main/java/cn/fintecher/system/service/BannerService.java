package cn.fintecher.system.service;

import java.util.List;
import java.util.Map; /**
 * @Auther: liangdeng
 * @Date: 2018/9/19
 * @Description:
 */
public interface BannerService {
    /**
     * @description: 获取banner列表
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/19
     */
    List<String> getBannerList(Map map);
}
