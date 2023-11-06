package com.ylan.quartzdemo1.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by pepsi-wyl
 * @date 2022-03-05 9:45
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Component(value = "user")

@TableName(schema = "mybatisplus", value = "user")
public class User extends Model<User> implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 6401942840459021558L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private Integer age;

    private String email;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Version
    private Integer version;

    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

}