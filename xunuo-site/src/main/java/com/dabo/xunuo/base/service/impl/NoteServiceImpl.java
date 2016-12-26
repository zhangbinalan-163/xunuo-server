package com.dabo.xunuo.base.service.impl;

import com.dabo.xunuo.base.common.exception.SysException;
import com.dabo.xunuo.base.dao.NoteMapper;
import com.dabo.xunuo.base.entity.Note;
import com.dabo.xunuo.base.entity.RowBounds;
import com.dabo.xunuo.base.service.INoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 小记业务基础实现
 * Created by zhangbin on 16/8/27.
 */
@Service
public class NoteServiceImpl implements INoteService {

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

    @Override
    public void deleteNote(long noteId) throws SysException {
        noteMapper.delete(noteId);
    }

    @Override
    public Note getNoteById(long noteId) throws SysException {
        return noteMapper.getById(noteId);
    }
}
