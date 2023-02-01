package com.xxxx.server.pojo;

import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RespPageBean对象", description = "分页对象")
public class RespPageBean {

  //总行数
  private long total;
  //数据list
  private List<?> data;
}
