package com.xxxx.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
@Data
@NoArgsConstructor  //该注解表示添加无参构造方法
@RequiredArgsConstructor //该注解表示添加有参构造方法
@EqualsAndHashCode(callSuper = false,of = "name") //of = "name"表示重写Equals和HashCode
@TableName("t_position")
@ApiModel(value = "Position对象", description = "")
public class Position implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "id")
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty(value = "职位")
  @Excel(name = "职位")
  @NonNull //表示非空
  private String name;

  @ApiModelProperty(value = "创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
  private LocalDateTime createDate;

  @ApiModelProperty(value = "是否启用")
  private Boolean enabled;

}
