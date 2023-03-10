package com.xxxx.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
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
@TableName("t_department")
@ApiModel(value = "Department对象", description = "")
public class Department implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "id")
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty(value = "部门名称")
  @Excel(name = "部门")
  @NonNull //表示非空
  private String name;

  @ApiModelProperty(value = "父id")
  private Integer parentId;

  @ApiModelProperty(value = "路径")
  private String depPath;

  @ApiModelProperty(value = "是否启用")
  private Boolean enabled;

  @ApiModelProperty(value = "是否上级")
  private Boolean isParent;

  @ApiModelProperty(value = "子部门列表")
  @TableField(exist = false)
  private List<Department> children;

  @ApiModelProperty(value = "返回结果,仅供存储过程使用")
  @TableField(exist = false)
  private Integer result;


}
