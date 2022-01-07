package com.luckyframe.rc.entity;



import lombok.Getter;
import lombok.Setter;

/**
 * 元素行为
 */

@Getter
@Setter
public class ElementAction {
    /**
     * access
     * action
     * actionValue
     */
    String access;
    String action;
    String actionValue;

    @Override
    public String toString() {
        return "ElementAction{" +
                "access='" + access + '\'' +
                ", action='" + action + '\'' +
                ", actionValue='" + actionValue + '\'' +
                '}';
    }
}

