package com.hb.task.impl;

import com.hb.task.mapper.SynDataToShopMallMapper;
import com.hb.task.model.HouseDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huzhe on 2018/11/29.
 */
@Service
public class SynHouseDataService {

    public static final Logger logger = LoggerFactory.getLogger(SynHouseDataService.class);

    @Autowired
    private SynDataToShopMallMapper mapper;

    //新增仓库
    public void addHouse(HouseDetail houseDetail){
        mapper.addHouse(houseDetail);
    }

    //修改仓库
    public void updateHouse(HouseDetail houseDetail){
        int isExist = mapper.checkHouseIsExist(houseDetail);
        if(isExist == 0){
            mapper.addHouse(houseDetail);
        }else{
            mapper.updateHouse(houseDetail);
        }
    }

}
