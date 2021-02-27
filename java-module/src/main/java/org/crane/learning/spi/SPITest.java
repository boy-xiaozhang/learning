package org.crane.learning.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * description: 创建方法直接使用文件搜索
 * author: zhang
 * Date: 2021/2/27 12:38 下午
 */
public class SPITest {
    public static void main(String[] args) {
        ServiceLoader<Search> load = ServiceLoader.load(Search.class);
        Iterator<Search> iterator = load.iterator();
        iterator.forEachRemaining(t-> t.searchDoc("hello crane"));
    }
}
