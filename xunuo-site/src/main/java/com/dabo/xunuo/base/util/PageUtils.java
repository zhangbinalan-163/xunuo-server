/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.base.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

/**
 * TODO: description
 * Date: 2016-12-28
 *
 * @author apple
 */
public class PageUtils {
    /**
     * 人工分页
     */
    public static <T> List<T> pageList(List<T> targetList, int page, int limit) {
        if (CollectionUtils.isEmpty(targetList)) {
            return targetList;
        }
        //再进行人工分页
        int totalCount = targetList.size();
        int startIndex = (page - 1) * limit;
        int endIndex = startIndex + limit - 1;
        if (startIndex > totalCount - 1) {
            return new ArrayList<>();
        }
        if (endIndex > totalCount - 1) {
            endIndex = totalCount - 1;
        }
        List<T> contentIdsResult = new ArrayList<>();
        for (int i = startIndex; i <= endIndex; i++) {
            contentIdsResult.add(targetList.get(i));
        }
        return contentIdsResult;
    }
}
