package com.dabo.xunuo.service.impl;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.dao.NoteMapper;
import com.dabo.xunuo.entity.Note;
import com.dabo.xunuo.entity.RowBounds;
import com.dabo.xunuo.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 小记业务基础实现
 * Created by zhangbin on 16/8/27.
 */
@Service
public class NoteServiceImpl implements INoteService{

    @Autowired
    private NoteMapper noteMapper;

    @Override
    public void createNote(Note note) throws SysException {
        noteMapper.insert(note);
    }

    @Override
    public List<Note> getUserNote(long userId, RowBounds rowBounds) throws SysException {
        return noteMapper.getByUser(userId,rowBounds);
    }

    @Override
    public int countUserNote(long userId) throws SysException {
        return noteMapper.countByUser(userId);
    }
}
