package org.crane.learning.spi;

import java.util.List;

/**
 * description:SPI服务实现类之文件搜索
 * author: zhang
 * Date: 2021/2/27 12:31 下午
 */
public class FileSearch implements Search{
    @Override
    public List<String> searchDoc(String keyWord) {
        System.out.println("文本搜索,搜索值为:"+keyWord);
        return null;
    }
}
