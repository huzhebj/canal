package com.hb.task.mapper;

import com.hb.task.model.HouseDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SynDataToShopMallMapper {

    public void addHouse(HouseDetail houseDetail);

    public int checkHouseIsExist(HouseDetail houseDetail);

    public void updateHouse(HouseDetail houseDetail);

}
