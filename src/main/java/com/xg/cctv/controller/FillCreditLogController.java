package com.xg.cctv.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xg.cctv.common.util.ShiroUtils;
import com.xg.cctv.excel.impl.FillCreditLogExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.xg.cctv.mybatis.po.FillCreditLog;
import com.xg.cctv.service.FillCreditLogService;
import com.xg.cctv.common.util.R;

import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lorenzo
 * @since 2020-02-01
 */
@RestController
@Validated
@Api(value = "FillCreditLogController", description = "加彩与缴码日志")
@RequestMapping("/fillCreditLog")
public class FillCreditLogController {
    @Autowired
    public FillCreditLogService iFillCreditLogService;

    /**
     * 分页查询数据
     *
     * @param page  分页信息
     * @param fillCreditLog 查询条件
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("fillCreditLog:list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = false),
            @ApiImplicitParam(name = "size", value = "每页显示条数，默认 10", required = false )
    })
    @ApiOperation(value="获取信息分页", notes="信息分页接口" , httpMethod = "GET" , response = R.class)
    public R getFillCreditLogList(Page<FillCreditLog> page,@RequestParam Map<String , Object> fillCreditLog){
        return R.ok().put("data" , iFillCreditLogService.selectPage(page, fillCreditLog));
    }

    @GetMapping("/excel")
    @RequiresPermissions("fillCreditLog:excel")
    @ApiOperation(value="导出EXCEL", notes="导出EXCEL接口" , httpMethod = "GET" , response = R.class)
    public R getFillCreditLogExcel(@RequestParam Map<String , Object> fillCreditLog){
        List<FillCreditLog> fillCreditLogs = iFillCreditLogService.selectList(fillCreditLog);
        return R.ok()
                .put("key" , new FillCreditLogExcelService().exportExcel(fillCreditLogs));
    }

    /**
     * 保存和修改公用的
     * @param fillCreditLog 传递的实体
     * @return R
     */
    @PostMapping("/save")
    @RequiresPermissions("fillCreditLog:save")
    @ApiOperation(value="保存", notes="保存信息接口" , httpMethod = "POST" , response = R.class)
    public R fillCreditLogSave(@RequestBody @Valid FillCreditLog fillCreditLog){
        if (fillCreditLog.getId() == null){
            fillCreditLog.setCreateUid(ShiroUtils.getUserId());
            fillCreditLog.setCreateTime(new Date());
        }
        boolean rs = iFillCreditLogService.saveOrUpdate(fillCreditLog);
        if (rs){
            return R.ok();
        }
        return R.error();
    }

    /**
     * 根据id删除对象
     * @param id  实体ID
     * @return R
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("fillCreditLog:delete")
    @ApiImplicitParam(name = "id", value = "id", required = true )
    @ApiOperation(value="根据id删除", notes="根据id删除接口" , httpMethod = "POST" , response = R.class)
    public R fillCreditLogDelete(@PathVariable String id){
        boolean rs = iFillCreditLogService.removeById(id);
        if (rs) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 批量删除对象
     * @param requestMap 实体集合ID
     * @return R
     */
    @PostMapping("/batchDelete")
    @RequiresPermissions("fillCreditLog:delete")
    @ApiImplicitParam(name = "ids", value = "ids", required = true )
    @ApiOperation(value="批量删除", notes="批量删除接口" , httpMethod = "POST" , response = R.class)
    public R deleteBatchIds(@RequestBody Map<String,List<String>> requestMap){
        List<String> ids = requestMap.get("ids");
        boolean rs = iFillCreditLogService.removeByIds(ids);
        if (rs){
            return R.ok().put("data" , ids.size());
        }
        return R.error();
    }
}
