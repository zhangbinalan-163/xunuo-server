package com.dabo.xunuo.base.dao;

import com.dabo.xunuo.base.entity.RowBounds;
import com.dabo.xunuo.base.entity.UserEventClass;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户事件类型操作Mapper
 */
public interface UserEventClassMapper extends BaseMapper<Integer,UserEventClass>{

    /**
     * 计数
     * @param userId
     * @param sourceType
     * @return
     */
    int countByUser(@Param("userId") long userId, @Param("sourceType") int sourceType);

    /**
     * 获取指定用户+指定来源的事件类型
     * @param userId
     * @param sourceType
     * @return
     */
    List<UserEventClass> getEventClassByUser(@Param("userId") long userId,
                                            @Param("sourceType") int sourceType,
                                            @Param("field") String field,
                                            @Param("direction") String direction,
                                            @Param("rowBounds") RowBounds rowBounds);

    /**
     * 获取用户+指定来源的事件类型的最大sort_index
     * @param userId
     * @param sourceType
     * @return
     */
    int getMaxSortIndex(@Param("userId") long userId,
                        @Param("sourceType") int sourceType);
}

