package org.crane.learning.spi;

import java.util.List;

/**
 * description: SPI服务发现学习实现类之数据库搜索
 * author: zhang
 * Date: 2021/2/27 12:34 下午
 */
public class DataBaseSearch implements Search{
    @Override
    public List<String> searchDoc(String keyWord) {
        System.out.println("数据库搜索,搜索的值为:"+keyWord);
        return null;
    }
}
