package com.hb.task.model;

import lombok.Data;
/**
 * Created by huzhe on 2018/11/29.
 */
@Data
public class HouseDetail {
    //仓库ID
    private Long houseID;
    //仓库名称
    private String houseName;
    //仓库编码
    private String houseCode;
    //别名
    private String alias;
    //配送中心ID
    private Long demandID;
    //是否期初 0：否 1：是 默认否
    private String isOpeningBalance;
    //仓库地址
    private String address;
    //负责人
    private String charge;
    //创建人
    private String createBy;
    //修改人
    private String actionBy;
    //联系电话
    private String linkTel;
    //备注
    private String note;
    //是否启用 1：启用 0：停用
    private Integer isActive;
    //记录状态 0：新建 1：修改 2：删除
    private Integer action;
    //创建时间
    private Long createTime;
    //修改时间
    private Long actionTime;
    //是否被修改 0:未修改  1:已修改
    private int isUpdated;
}
