package com.hb.task.test;

import com.hb.task.impl.SynHouseDataService;
import com.hb.task.model.HouseDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration( exclude = RedisAutoConfiguration.class)
@SpringBootTest
public class TestCase {
    @Autowired
    SynHouseDataService synHouseDataService;

    @Test
    public void addHouse(){
        HouseDetail houseDetail = new HouseDetail();
        houseDetail.setHouseID(1L);
        houseDetail.setHouseName("仓库1206-1");
        houseDetail.setHouseCode("1206-1");
        houseDetail.setAlias("仓库1206-1");
        houseDetail.setDemandID(1010L);
        houseDetail.setIsOpeningBalance("1");
        houseDetail.setAddress("北京市西城区凯德大厦");
        houseDetail.setCharge("张三");
        houseDetail.setCreateBy("张三");
        houseDetail.setActionBy("张三");
        houseDetail.setLinkTel("15712566589");
        houseDetail.setNote("123仓库备注");
        houseDetail.setIsActive(1);
        houseDetail.setAction(1);
        houseDetail.setCreateTime(20181205201512L);
        houseDetail.setActionTime(20181205201512L);

        synHouseDataService.addHouse(houseDetail);
    }

    @Test
    public void updateHouse(){
        HouseDetail houseDetail = new HouseDetail();
        houseDetail.setHouseID(2L);
        houseDetail.setHouseName("仓库1206-2");
        houseDetail.setHouseCode("1206-2");
        houseDetail.setAlias("仓库1206-2");
        houseDetail.setDemandID(1010L);
        houseDetail.setIsOpeningBalance("1");
        houseDetail.setAddress("北京市西城区凯德大厦12345");
        houseDetail.setCharge("张三");
        houseDetail.setCreateBy("张三");
        houseDetail.setActionBy("王五");
        houseDetail.setLinkTel("15712566589");
        houseDetail.setNote("123仓库备注");
        houseDetail.setIsActive(1);
        houseDetail.setAction(1);
        houseDetail.setCreateTime(20181205201512L);
        houseDetail.setActionTime(20181205201512L);

        synHouseDataService.updateHouse(houseDetail);
    }
}
