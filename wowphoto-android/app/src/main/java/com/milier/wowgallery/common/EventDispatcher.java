package com.milier.wowgallery.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件分发器
 * Created by guofe on 2015/11/12.
 */
public class EventDispatcher {

    /**
     * 保存所有添加的事件
     */
    private Map<String, List<EventListener>> eventMap = new HashMap<>();

    /**
     * 添加事件监听器
     * @param eventType 事件类型
     * @param eventListener 监听器
     */
    public void addEventListener(String eventType, EventListener eventListener) {
        if (!eventMap.containsKey(eventType)) {
            eventMap.put(eventType, new ArrayList<EventListener>());
        }

        eventMap.get(eventType).add(eventListener);
    }

    /**
     * 移除一个监听器
     * @param eventListener
     */
    public void removeEventListener(EventListener eventListener) {
        for (String eventType: eventMap.keySet()) {
            List<EventListener> events = eventMap.get(eventType);
            for (EventListener event: events) {
                if (event == eventListener) {
                    events.remove(event);
                }
            }

            if (events.isEmpty()) {
                eventMap.remove(eventType);
            }
        }
    }

    /**
     * 触发一类事件
     * @param eventType 事件类型
     * @param payload 承载的数据
     */
    public void emit(String eventType, Map<String, Object> payload) {
        if (eventMap.containsKey(eventType)) {
            for (EventListener eventListener: eventMap.get(eventType)) {
                eventListener.handle(eventType, payload);
            }
        }
    }

    /**
     * 触发一类事件，不传递数据
     * @param eventType 事件类型
     */
    public void emit(String eventType) {
        if (eventMap.containsKey(eventType)) {
            for (EventListener eventListener: eventMap.get(eventType)) {
                eventListener.handle(eventType, null);
            }
        }
    }

    /**
     * 通用的事件监听接口
     */
    public interface EventListener {
        void handle(String eventType, Map<String, Object> payload);
    }

}
