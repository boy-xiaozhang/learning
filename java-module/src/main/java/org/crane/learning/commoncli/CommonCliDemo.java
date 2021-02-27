package org.crane.learning.commoncli;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * description: 学习Apache Common Cli
 * author: zhang
 * Date: 2021/2/27 3:08 下午
 */
public class CommonCliDemo {
    public static void main(String[] args) throws Exception{
        Options options = new Options();
        //参数说明
        //arg1: 参数名称
        //arg2: 参数是否需要传值  true 需要 false 不需要
        //arg3: 对参数的说明
        options.addOption("t",false,"display current time");
        options.addOption("h", false, "Print help for this application");
        options.addOption("u", true, "set phone get author info");
        options.addOption("v",false,"get application version");
        //获得参数解析器
        CommandLineParser parser = new BasicParser();
        //解析命令行传入的参数
        CommandLine parse = parser.parse(options, args);
        //如果有参数t打印,如果没有打印可选参数文档
        if (parse.hasOption("t")){
            System.out.println(System.currentTimeMillis());
        }else {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("OptionsTip", options);
        }
    }
}
