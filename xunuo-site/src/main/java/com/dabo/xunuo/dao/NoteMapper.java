package com.dabo.xunuo.dao;

import com.dabo.xunuo.entity.Note;
import com.dabo.xunuo.entity.RowBounds;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户小记操作Mapper
 * Created by zhangbin on 16/7/31.
 */
public interface NoteMapper extends BaseMapper<Long,Note>{
    /**
     * 根据用户分页获取小记信息
     * 按照创建时间倒叙排序
     * 只获取正常状态的
     * @param userId
     * @param rowBounds
     * @return
     */
    List<Note> getByUser(@Param("userId")long userId,@Param("rowBounds")RowBounds rowBounds);

    /**
     * 计算用户的小记总数
     * 只获取正常状态的
     * @param userId
     * @return
     */
    int countByUser(@Param("userId")long userId);
}

