package com.dabo.xunuo.base.service;

import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.entity.Note;
import com.dabo.xunuo.base.entity.RowBounds;

import java.util.List;

/**
 * 小记相关业务接口
 * Created by zhangbin on 16/8/27.
 */
public interface INoteService {
    /**
     * 保存小记
     * @param note
     * @throws SysException
     */
    void createNote(Note note) throws SysException;

    /**
     * 分页获取用户的小记列表
     * @param userId
     * @param rowBounds
     * @return
     * @throws SysException
     */
    List<Note> getUserNote(long userId, RowBounds rowBounds) throws SysException;

    /**
     * 用户的小记总数计数
     * @param userId
     * @return
     * @throws SysException
     */
    int countUserNote(long userId) throws SysException;

    /**
     * 删除用户小计
     * @param noteId
     * @throws SysException
     */
    void deleteNote(long noteId) throws SysException;

    /**
     * 获取用户小计
     * @param noteId
     * @return
     * @throws SysException
     */
    Note getNoteById(long noteId) throws SysException;
}
