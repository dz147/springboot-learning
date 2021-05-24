package com.xiaoxiang.springboot.core.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Stephen
 */

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractEntity extends BaseEntity {

    @TableLogic
    protected Boolean del = Boolean.FALSE;
}
