package cn.fintecher.system.service;

import java.util.List;
import java.util.Map; /**
 * @Auther: liangdeng
 * @Date: 2018/9/19
 * @Description:
 */
public interface NoticeService {
    /**
     * @description: 获取公告列表
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/19
     */
    List<String> getNoticeList(Map map) throws Exception;

    /**
     * @description: 查询公告详情
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/19
     */
    String getNoticeDetailById(String id) throws Exception;
}
