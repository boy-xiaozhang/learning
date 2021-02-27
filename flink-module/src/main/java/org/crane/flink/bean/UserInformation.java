package org.crane.flink.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Module:UserInfoPojo
 * CLassName: UserInformation
 * Description:
 * Author: Crane
 * date: 2020/12/12 下午6:53
 */
@Data
@NoArgsConstructor
public class UserInformation {

    /**
     * id : 10001
     * name : crane
     * phone : 17634056586
     * address : GZ
     * age : 23
     */
    private String id;
    private String name;
    private String phone;
    private String address;
    private int age;
}
