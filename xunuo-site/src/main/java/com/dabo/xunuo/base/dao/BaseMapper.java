package com.dabo.xunuo.base.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基础的mapper
 *
 * @param <K>
 * @param <T>
 */
public interface BaseMapper<K, T> {
    /**
     * 新增数据,可以获取自增ID
     *
     * @param entity
     */
    void insert(@Param(value = "entity") T entity);

    /**
     * 新增数据，此时无法获取自增的ID
     *
     * @param entityList
     */
    void insertBatch(List<T> entityList);

    /**
     * 修改数据，必须指定id
     *
     * @param entity
     */
    void update(T entity);

    /**
     * 删除实体数据,根据ID
     *
     * @param keyId
     */
    void delete(@Param("keyId") K keyId);

    /**
     * 删除实体数据,根据ID
     *
     * @param keyIdList
     */
    void deleteBatch(@Param("keyIdList") List<K> keyIdList);

    /**
     * 根据ID获取实体数据
     *
     * @param keyId
     * @return
     */
    T getById(@Param("keyId") K keyId);

    /**
     * 批量获取数据
     *
     * @param keyIdList
     * @return
     */
    List<T> getListByIds(@Param("keyIdList") List<K> keyIdList);
}
