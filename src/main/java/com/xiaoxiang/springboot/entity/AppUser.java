package com.xiaoxiang.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoxiang.springboot.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * (AppUser)表实体类
 *
 * @author Stephen
 * @since 2021-05-19 14:25:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("app_user")
public class AppUser extends BaseEntity {


    /**
     * 昵称
     */
    private String nickname;


    /**
     * 唯一键值
     */
    private String unionId;


    /**
     * 签名
     */
    private String secret;


    /**
     * 手机号码
     */
    private String phone;

    public AppUser(String phone) {
        this.phone = phone;
    }

}