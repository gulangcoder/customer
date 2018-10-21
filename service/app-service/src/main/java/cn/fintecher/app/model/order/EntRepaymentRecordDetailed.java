package cn.fintecher.app.model.order;

import java.util.Date;

/**
 * Title :
 * Description : @还款流水明细model@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
public class EntRepaymentRecordDetailed {
    private String id;

    //还款流水id
    private String repaymentRecordId;

    //还款计划id
    private String repaymentPlanId;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRepaymentRecordId() {
        return repaymentRecordId;
    }

    public void setRepaymentRecordId(String repaymentRecordId) {
        this.repaymentRecordId = repaymentRecordId == null ? null : repaymentRecordId.trim();
    }

    public String getRepaymentPlanId() {
        return repaymentPlanId;
    }

    public void setRepaymentPlanId(String repaymentPlanId) {
        this.repaymentPlanId = repaymentPlanId == null ? null : repaymentPlanId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}