package com.songshilong.starter.database.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsProject: chemical-data-java
 * @BelongsPackage: com.songshilong.starter.database.base
 * @Author: Ice, Song
 * @CreateTime: 2025-04-18  15:44
 * @Description: 分页查询结果封装类
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    /**
     * 当前页
     */
    private Long pageNumber;
    /**
     * 页数据大小
     */
    private Long pageSize;
    /**
     * 一共有多少页
     */
    private Long pages;
    /**
     * 一共有多少记录
     */
    private Long total;
    /**
     * 查询结果
     */
    private List<T> result;

}
