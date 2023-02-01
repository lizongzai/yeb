package com.xxxx.server.controller;


import com.xxxx.server.pojo.Position;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "PositionController")
@RequestMapping("/system/basic/pos")
public class PositionController {

  @Autowired
  private IPositionService positionService;

  @ApiOperation(value = "获取所有职位信息")
  @GetMapping("/")
  public List<Position> getAllPositions() {
    return positionService.list();
  }

  @ApiOperation(value = "添加职位信息")
  @PostMapping("/")
  public RespBean addPosition(@RequestBody Position position) {
    position.setCreateDate(LocalDateTime.now());
    if (positionService.save(position)) {
      return RespBean.success("添加成功");
    }
    return RespBean.error("添加失败!");
  }

  @ApiOperation(value = "更新职位信息")
  @PutMapping("/")
  public RespBean updatePosition(@RequestBody Position position) {
    if (positionService.updateById(position)) {
      return RespBean.success("更新成功!");
    }
    return RespBean.error("更新失败!");
  }

  @ApiOperation(value = "删除职位信息")
  @DeleteMapping("/{id}")
  public RespBean removePositionById(@PathVariable Integer id) {
    if (positionService.removeById(id)) {
      return RespBean.success("删除成功");
    }
    return RespBean.error("删除成功!");
  }

  @ApiOperation(value = "批量删除职位")
  @DeleteMapping("/")
  public RespBean removePositionByIds(Integer[] ids) {
    if (positionService.removeByIds(Arrays.asList(ids))) { //需要转数组
      return RespBean.success("删除成功!");
    }
    return RespBean.error("删除失败!");
  }

}
