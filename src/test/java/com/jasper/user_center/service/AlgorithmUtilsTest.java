package com.jasper.user_center.service;

import com.jasper.user_center.utils.AlgorithmUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jasper
 * @create 2023-09-06 13:36
 */
public class AlgorithmUtilsTest {
    @Test
    void testComparTags() {
        List<String> list1 = Arrays.asList("java", "大一", "男");
        List<String> list2 = Arrays.asList("python", "大二", "男");
        List<String> list3 = Arrays.asList("java", "大一", "女");
        int i1 = AlgorithmUtils.minDistance(list1, list2);
        int i2 = AlgorithmUtils.minDistance(list1, list3);

        System.out.println(i1);
        System.out.println(i2);
    }
}
