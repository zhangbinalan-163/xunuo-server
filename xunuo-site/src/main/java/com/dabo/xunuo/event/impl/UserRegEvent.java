package com.dabo.xunuo.event.impl;

import com.dabo.xunuo.entity.User;
import com.dabo.xunuo.event.Event;

/**
 * 用户注册事件
 * Created by zhangbin on 16/8/3.
 */
public class UserRegEvent extends Event<User>{

    public UserRegEvent(User source) {
        super(source);
    }

    public static class Builder{
        private User user;

        public Builder source(User user){
            this.user=user;
            return this;
        }

        public UserRegEvent build(){
            UserRegEvent userRegEvent=new UserRegEvent(user);
            return userRegEvent;
        }

        public static Builder instance(){
            return new Builder();
        }

    }
}
