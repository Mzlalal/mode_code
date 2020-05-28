package com.mzlalalal.model;

import java.io.Serializable;

/**
 * @Desc:
 * @Author: Mzlalalal
 * @Date: 2020/5/28 21:26
 **/
public class Element implements Serializable {

    private static final long serialVersionUID = 4817386941689102518L;
    private String element;

    public void setElement(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    @Override
    public String toString() {
        return "Element{" +
                "element='" + element + '\'' +
                '}';
    }
}
