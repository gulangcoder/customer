package cn.fintecher.system.service.impl;

import cn.fintecher.system.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/19
 * @Description:
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Override
    public List<String> getNoticeList(Map map) throws Exception{
        //TODO 查询公告列表
        List<String> list = new ArrayList<>();
        list.add("关于我司新产品上线具有重大优惠仅限十天");
        list.add("关于我司新产品上线具有重大优惠仅限月底");
        list.add("关于我司新产品上线具有重大优惠只有两天");
        list.add("关于我司新产品上线具有重大优惠最后一天");
        return list;
    }

    @Override
    public String getNoticeDetailById(String id) throws Exception {
        //TODO 目前写假数据 后续再补
        String n = "关于我司新产品上线具有重大优惠仅限月底关于我司新产品上线具有重大优惠仅限月底";
        return n;
    }
}
