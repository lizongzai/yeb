package com.xxxx.server.controller;


import com.xxxx.server.pojo.Joblevel;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IJoblevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
@RestController
@Api(tags = "JoblevelController")
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

  @Autowired
  private IJoblevelService joblevelService;

  @ApiOperation(value = "获取所有职称信息")
  @GetMapping("/")
  public List<Joblevel> getAllJobLevels() {
    return joblevelService.list();
  }

  @ApiOperation(value = "添加职称信息")
  @PostMapping("/")
  public RespBean addJobLevel(@RequestBody Joblevel joblevel) {
    joblevel.setCreateDate(LocalDateTime.now());
    if (joblevelService.save(joblevel)) {
      return RespBean.success("添加成功!");
    }
    return RespBean.error("添加失败!");
  }

  @ApiOperation(value = "更新职称信息")
  @PutMapping("/")
  public RespBean updateJobLevel(@RequestBody Joblevel joblevel) {
    if (joblevelService.updateById(joblevel)) {
      return RespBean.success("更新成!");
    }
    return RespBean.error("更新失败!");
  }

  @ApiOperation(value = "删除职称信息")
  @DeleteMapping("/{id}")
  public RespBean removeJobLevelById(@PathVariable Integer id) {
    if (joblevelService.removeById(id)) {
      return RespBean.success("删除成功!");
    }
    return RespBean.error("删除失败!");
  }

  @ApiOperation(value = "批量删除职称")
  @DeleteMapping("/")
  public RespBean removeJobLevelByIds(Integer[] ids) {
    if (joblevelService.removeByIds(Arrays.asList(ids))) {
      return RespBean.success("删除成功!");
    }
    return RespBean.error("删除失败!");
  }
}
