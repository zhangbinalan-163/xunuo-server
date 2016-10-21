package com.dabo.xunuo.web.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.Note;
import com.dabo.xunuo.entity.RowBounds;
import com.dabo.xunuo.entity.User;
import com.dabo.xunuo.service.INoteService;
import com.dabo.xunuo.service.IUserService;
import com.dabo.xunuo.util.RequestUtils;
import com.dabo.xunuo.web.vo.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 小记相关的controller
 */
@RequestMapping("/note")
@Controller
public class NoteController extends BaseController{

    @Autowired
    private INoteService noteService;

    /**
     * 发布用户小记
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/publish")
    @ResponseBody
    public String publishNote() throws Exception {
        //当前登录userId
        long userId=RequestContext.getUserId();
        String title=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "title");
        //TODO 检查title长度、过滤表情
        String content=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "content");
        //TODO 检查content长度、过滤表情

        Note note=new Note();
        note.setUserId(userId);
        note.setContent(content);
        note.setDelFlag(Constants.DEL_FLAG_NORMAL);
        note.setCreateTime(System.currentTimeMillis());
        note.setTitle(title);

        noteService.createNote(note);

        return createDefaultSuccessResponse();
    }

    /**
     * 查询用户小记列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list/byuser")
    @ResponseBody
    public String noteListByUser() throws Exception {
        //当前登录userId
        long userId=RequestContext.getUserId();
        int page=RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "page",1);
        int limit=RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "limit",10);

        int total=noteService.countUserNote(userId);

        RowBounds rowBounds=new RowBounds(page,limit);
        List<Note> noteList=noteService.getUserNote(userId,rowBounds);

        JSONObject resultObject=new JSONObject();
        resultObject.put("total",total);

        JSONArray jsonArray=new JSONArray();
        if(!CollectionUtils.isEmpty(noteList)){
            noteList.forEach(note -> {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("id",note.getId());
                jsonObject.put("title",note.getTitle());
                jsonObject.put("content",note.getContent());
                jsonArray.add(jsonObject);
            });
        }
        resultObject.put("data",jsonArray);

        return createSuccessResponse(resultObject);
    }

    /**
     * 删除用户小记
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String deleteNote() throws Exception {
        //当前登录userId
        long userId=RequestContext.getUserId();
        long noteId=RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "note_id");

        Note note = noteService.getNoteById(noteId);
        if(note!=null&&note.getUserId()==userId){
            noteService.deleteNote(noteId);
        }
        return createDefaultSuccessResponse();
    }
}
