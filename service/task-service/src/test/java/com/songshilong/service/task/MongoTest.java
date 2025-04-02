package com.songshilong.service.task;

import com.songshilong.service.task.dao.entity.TextProcessResultEntity;
import com.songshilong.service.task.dto.ReactionParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
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
    private MongoOperations mongoOperations;


    @Test
    public void connect() {
        TextProcessResultEntity entity = new TextProcessResultEntity();
//        entity.setId(String.valueOf(Math.random() * 100000));
        entity.setId("2132321321323");
        entity.setChemicalText("hello world");
        List<ReactionParam> paramList = new ArrayList<ReactionParam>();
        ReactionParam reactionParam = new ReactionParam();
        reactionParam.setReactant(Collections.singletonList("reactant"));
        paramList.add(reactionParam);
        entity.setResult(paramList);
        mongoOperations.insert(entity);
    }


    @Test
    public void query() {
        Criteria criteria = new Criteria("id").is("2132321321323");
        Query query = new Query(criteria);
        System.out.println(mongoOperations.findOne(query, TextProcessResultEntity.class));
    }


}
