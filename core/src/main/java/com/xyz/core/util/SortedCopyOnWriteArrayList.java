package com.xyz.core.util;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @title:
 * @description:
 * @company: Netease
 * @author: GlanWang
 * @version: Created on 17/4/28.
 */

public class SortedCopyOnWriteArrayList<T extends Comparable> extends CopyOnWriteArrayList {

    /**
     * 逆序从大到小添加，相等时添加到相等后边
     * @param value
     */
    public void addReversedOrder(T value) {
        addByOrder(value, false);
    }

    /**
     * 顺序从小到大添加，相等时添加都相等元素后
     * @param value
     */
    public void addPositiveOrder(T value) {
        addByOrder(value, true);
    }



    private void addByOrder(T value, boolean positiveOrder){
        if (size() == 0) {
            add(0, value);
        } else {
            int i = 0;
            for (i = size(); i > 0; i--) {
                if(positiveOrder){
                    //正序
                    if(value.compareTo(get(i -1)) >= 0){
                        break;
                    }
                } else {
                    if (value.compareTo(get(i - 1)) <= 0) {
                        //逆序
                        break;
                    }
                }
            }
            add(i, value);
        }

    }

}
