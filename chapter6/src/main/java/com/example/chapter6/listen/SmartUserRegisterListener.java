package com.example.chapter6.listen;

import com.example.chapter6.event.UserRegisterEvent;
import com.example.chapter6.model.UserBean;
import com.example.chapter6.service.UserService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * SmartApplicationListener接口继承了全局监听ApplicationListener，
 * 并且泛型对象使用的ApplicationEvent来作为全局监听，
 * 可以理解为使用SmartApplicationListener作为监听父接口的实现，监听所有事件发布。
 *
 * 既然是监听所有的事件发布，那么SmartApplicationListener接口添加了两个方法supportsEventType、supportsSourceType来作为区分是否是我们监听的事件，
 * 只有这两个方法同时返回true时才会执行onApplicationEvent方法。
 *
 * @author: Kunzite
 * @Date: 2018-09-01 17:53
 **/
@Component
public class SmartUserRegisterListener implements SmartApplicationListener {

    /**
     * 该方法返回true&supportsSourceType同样返回true时，才会调用该监听内的onApplicationEvent方法
     *
     * @param aClass 接收到的监听事件类型
     * @return
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        //只有UserRegisterEvent监听类型才会执行下面逻辑
        return aClass == UserRegisterEvent.class;
    }

    /**
     * 该方法返回true&supportsEventType同样返回true时，才会调用该监听内的onApplicationEvent方法
     *
     * @param aClass
     * @return
     */
    @Override
    public boolean supportsSourceType(@Nullable Class<?> aClass) {
        //只有在UserService内发布的UserRegisterEvent事件时才会执行下面逻辑
        return aClass == UserService.class;
    }

    /**
     * supportsEventType & supportsSourceType 两个方法返回true时调用该方法执行业务逻辑
     *
     * @param applicationEvent 具体监听实例，这里是UserRegisterEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        //转换事件类型
        UserRegisterEvent userRegisterEvent = (UserRegisterEvent) applicationEvent;
        //获取注册用户对象信息
        UserBean user = userRegisterEvent.getUserBean();
        //.../完成注册业务逻辑
        System.out.println("注册信息，用户名：" + user.getName() + "，密码：" + user.getPassword());
    }

    /**
     * 同步情况下监听执行的顺序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
