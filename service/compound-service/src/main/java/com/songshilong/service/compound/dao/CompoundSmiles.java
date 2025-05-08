package com.songshilong.service.compound.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.songshilong.starter.database.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.service.compound.dao
 * @Author: Ice, Song
 * @CreateTime: 2025-05-08  21:38
 * @Description: CompoundSmiles
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("compound_smiles")
public class CompoundSmiles extends BaseEntity {

    /**
     * primary key
     */
    @TableId(type = IdType.INPUT)
    private UUID id;

    /**
     * Compound Smiles
     */
    private String Smiles;

    /**
     * PubChem Compound IUPAC Name
     */
    private String iupac;
    /**
     * PubChem Compound Synonym Name ['...', '...', ......]
     */
    private String synonym;



}
