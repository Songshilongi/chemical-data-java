package com.songshilong.service.task;

import com.songshilong.service.task.dao.entity.SmilesProcessResultEntity;
import com.songshilong.service.task.dao.entity.TextProcessResultEntity;
import com.songshilong.service.task.dto.StandardReaction;
import com.songshilong.starter.database.util.MongoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.task
 * @Author: Ice, Song
 * @CreateTime: 2025-04-02  21:00
 * @Description: MongoTest
 * @Version: 1.0
 */
@SpringBootTest
public class MongoTest {


    @Autowired
    private MongoUtil mongoUtil;


    @Test
    public void smiles() {
        SmilesProcessResultEntity data = SmilesProcessResultEntity
                .builder()
                .id(String.valueOf(Math.random() * 100000))
                .ossUrl("url")
                .smiles("COH@")
                .build();
        mongoUtil.getInstance().insert(data);
    }

    @Test
    public void connect() {
        TextProcessResultEntity entity = new TextProcessResultEntity();
        entity.setId(String.valueOf(Math.random() * 100000));
//        entity.setId("2132321321323");
        entity.setChemicalText("hello world");
        List<StandardReaction> paramList = new ArrayList<StandardReaction>();
        StandardReaction reactionParam = new StandardReaction();
        reactionParam.setReactant(Collections.singletonList("test"));
        reactionParam.setProduct(Collections.singletonList("test"));
        reactionParam.setCatalyst(Collections.singletonList("test"));
        reactionParam.setSolvent(Collections.singletonList("test"));
        reactionParam.setReagent(Collections.singletonList("test"));
        reactionParam.setAfter(Collections.singletonList("test"));
        reactionParam.setTemperature(Collections.singletonList("test"));
        reactionParam.setTime(Collections.singletonList("test"));
        reactionParam.setYieldRate(Collections.singletonList("test"));
        paramList.add(reactionParam);
        entity.setResult(paramList);
        mongoUtil.getInstance().insert(entity);
    }


    @Test
    public void query() {
        Criteria criteria = new Criteria("id").is("2132321321323");
        Query query = new Query(criteria);
        System.out.println(mongoUtil.getInstance().findOne(query, TextProcessResultEntity.class));
    }

    @Test
    public void querySmiles() {
        System.out.println(mongoUtil.findByDataId("8562.741104149973", SmilesProcessResultEntity.class));
    }


}
