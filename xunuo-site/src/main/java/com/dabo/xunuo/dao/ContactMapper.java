package com.dabo.xunuo.dao;

import com.dabo.xunuo.entity.Contact;
import org.apache.ibatis.annotations.Param;

/**
 * 联系人表MAPPER
 */
public interface ContactMapper extends BaseMapper<Long,Contact>{
    /**
     * 设置联系人的形象
     * @param contactId
     * @param figureId
     */
    void setFigureId(@Param("contactId")long contactId,@Param("figureId")int figureId,@Param("updateTime")long updateTime);

    /**
     * 设置修改时间
     * @param contactId
     * @param updateTime
     */
    void setUpdateTime(@Param("contactId")long contactId,@Param("updateTime")long updateTime);

    /**
     * 统计数量
     * @param contactType
     * @return
     */
    long countByType(@Param("typeId") long contactType);
}
