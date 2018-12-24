package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/4.
 */
public class ApplyRoleInfoData {
    private String status;//	申请状态	是	String		“not_apply”未申请 “not_pay”未支付 “review”审核中 “success”通过 “failed”不通过 “overdue”过期
    private String type;//	申请类型	否	String		状态是 未申请 时没有此参数 “purchaser”采购商 “supplier”供应商
    private RoleApplyData  roleApply;//	申请详情	否	RoleApply		状态是 未支付 时有此参数
    private String date;//	有效期	否			状态是 通过、过期 时有此参数

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RoleApplyData getRoleApply() {
        return roleApply;
    }

    public void setRoleApply(RoleApplyData roleApply) {
        this.roleApply = roleApply;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
