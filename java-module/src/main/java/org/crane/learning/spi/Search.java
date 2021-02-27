package org.crane.learning.spi;

import java.util.List;

/**
 * description:SPI学习与实现
 * author: zhang
 * Date: 2021/2/27 12:30 下午
 */
public interface Search {
    List<String> searchDoc(String keyWord);
}
