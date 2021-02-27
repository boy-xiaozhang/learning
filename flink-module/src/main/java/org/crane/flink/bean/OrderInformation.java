package org.crane.flink.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Module:OrderInformation
 * CLassName: OrderInformation
 * Description:
 * Author: Crane
 * date: 2020/12/12 下午7:06
 */

@NoArgsConstructor
@Data
public class OrderInformation {
    /**
     * orderId : 10001
     * shopName : AJ鞋子
     * userID : 10001
     * amount : 997.0
     * sum : 1
     * orderStatus : 1
     * orderTime : 1607771431000
     */

    private String orderId;
    private String shopName;
    private String userID;
    private double amount;
    private int sum;
    private int orderStatus;
    private String orderTime;
}
