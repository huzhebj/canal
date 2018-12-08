package com.hb.task.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.hb.task.impl.SynHouseDataService;
import com.hb.task.mapper.SynDataToShopMallMapper;
import com.hb.task.model.HouseDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huzhe on 2018/11/29.
 */
@Component
public class CanalListener extends AbstractCanalListener {
    private static final Logger logger = LoggerFactory.getLogger(CanalListener.class);

    @Autowired
    private SynDataToShopMallMapper mapper;

    @Autowired
    private SynHouseDataService synHouseDataService;

    @Override
    protected void doSync(String database, String table, List<RowData> rowDataList, CanalEntry.EventType eventType) {
        logger.info("处理数据，总共条数:" + rowDataList.size());
        if(!"db_shop".equals(database) || !"tbl_shop_org".equals(table))
            return;
        for (RowData rowData : rowDataList) {
            switch (eventType) {
                case INSERT:
                    List<Column> insertColumns = rowData.getAfterColumnsList();
                    HouseDetail insertHouseDetail = getHouseDetail(insertColumns);
                    /*if(insertHouseDetail != null && insertHouseDetail.getIsUpdated() == 1){
                        synHouseDataService.addHouse(insertHouseDetail);
                    }*/

                    if(insertHouseDetail != null){
                        synHouseDataService.addHouse(insertHouseDetail);
                    }

                    break;
                case UPDATE:
                    List<Column> updateColumns = rowData.getAfterColumnsList();
                    HouseDetail updateHouseDetail = getHouseDetail(updateColumns);
                    /*if(updateHouseDetail != null && updateHouseDetail.getIsUpdated() == 1){
                        synHouseDataService.updateHouse(updateHouseDetail);
                    }*/

                    if(updateHouseDetail != null){
                        synHouseDataService.updateHouse(updateHouseDetail);
                    }

                    break;
                case DELETE:
                    List<Column> deleteColumns = rowData.getBeforeColumnsList();
                    HouseDetail deleteHouseDetail = getHouseDetail(deleteColumns);
                    /*if(deleteHouseDetail != null && deleteHouseDetail.getIsUpdated() == 1){
                        synHouseDataService.updateHouse(deleteHouseDetail);
                    }*/

                    if(deleteHouseDetail != null){
                        synHouseDataService.updateHouse(deleteHouseDetail);
                    }

                    break;
                default:
                    break;
            }
        }
    }

    public HouseDetail getHouseDetail(List<Column> columns){
        HouseDetail houseDetail = null;

        if(columns != null && columns.size() > 0){
            houseDetail = new HouseDetail();
            boolean noteUpdated = false;
            boolean chargeUpdated = false;
            for (Column column:columns) {
                String name = column.getName();
                String value = column.getValue();
                boolean updated = column.getUpdated();
                switch (name){
                    //仓库ID
                    case "id":
                        houseDetail.setHouseID(Long.parseLong(value));
                    //仓库名称
                    case "houseName":
                        houseDetail.setHouseName(value);
                        break;
                    //仓库编码
                    case "houseCode":
                        houseDetail.setHouseCode(value);
                        break;
                    //别名
                    case "alias":
                        houseDetail.setAlias(value);
                        break;
                    //配送中心ID
                    case "demandID":
                        houseDetail.setDemandID(Long.parseLong(value));
                        break;
                    //是否期初 0：否 1：是 默认否
                    case "isOpeningBalance":
                        houseDetail.setIsOpeningBalance(value);
                        break;
                    //仓库地址
                    case "address":
                        houseDetail.setAddress(value);
                        break;
                    //负责人
                    case "charge":
                        houseDetail.setCharge(value);
                        break;
                    //创建人
                    case "createBy":
                        houseDetail.setCreateBy(value);
                        break;
                    //修改人
                    case "actionBy":
                        houseDetail.setActionBy(value);
                        break;
                    //联系电话
                    case "linkTel":
                        houseDetail.setLinkTel(value);
                        break;
                    //备注
                    case "note":
                        houseDetail.setNote(value);
                        break;
                    //是否启用 1：启用 0：停用
                    case "isActive":
                        houseDetail.setIsActive(Integer.valueOf(value));
                        break;
                    //记录状态 0：新建 1：修改 2：删除
                    case "action":
                        houseDetail.setAction(Integer.valueOf(value));
                        break;
                    //创建时间
                    case "createTime":
                        houseDetail.setCreateTime(Long.valueOf(value));
                        break;
                    //修改时间
                    case "actionTime":
                        houseDetail.setActionTime(Long.valueOf(value));
                        break;
                    default:
                        break;
                }
            }
            /*if(orgDescUpdated || chargeUpdated)
                houseDetail.setIsUpdated(1);*/
        }

        return houseDetail;
    }
}
