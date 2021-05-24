package com.xiaoxiang.springboot.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 可查证性的实体类
 *
 * @author Stephen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuditableEntity extends AbstractEntity {

    @TableField(value = "creator", fill = FieldFill.INSERT)
    protected String creator;

    @TableField(value = "created_time", fill = FieldFill.INSERT)
    protected LocalDateTime createdTime;

    @TableField(value = "operator", fill = FieldFill.INSERT_UPDATE)
    protected String operator;

    @TableField(value = "operated_time", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime operatedTime;

}
