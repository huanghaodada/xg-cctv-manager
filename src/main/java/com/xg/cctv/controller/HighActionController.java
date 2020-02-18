package com.xg.cctv.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xg.cctv.common.dto.HighActionVo;
import com.xg.cctv.common.util.ShiroUtils;
import com.xg.cctv.excel.impl.HighActionExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.xg.cctv.mybatis.po.HighAction;
import com.xg.cctv.service.HighActionService;
import com.xg.cctv.common.util.R;

import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lorenzo
 * @since 2020-02-05
 */
@RestController
@Validated
@Api(value = "HighActionController", description = "高额活动")
@RequestMapping("/highAction")
public class HighActionController {
    @Autowired
    public HighActionService iHighActionService;

    @Value("${local.fileserver.dir}")
    private String basePath;

    /**
     * 分页查询数据
     *
     * @param page  分页信息
     * @param highAction 查询条件
     * @return
     */
    @GetMapping("/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = false),
            @ApiImplicitParam(name = "size", value = "每页显示条数，默认 10", required = false )
    })
    @ApiOperation(value="获取信息分页", notes="信息分页接口" , httpMethod = "GET" , response = R.class)
    public R getHighActionList(Page<HighActionVo> page,Map<String , Object> highAction){
        return R.ok().put("data" , iHighActionService.selectVoPage(page, highAction));
    }

    @GetMapping("/excel")
    @ApiOperation(value="导出EXCEL", notes="导出EXCEL接口" , httpMethod = "GET" , response = R.class)
    public R getHighActionExcel(Map<String , Object> highAction) throws IOException {
        List<HighActionVo> highActions = iHighActionService.selectVoList(highAction);

        for (HighActionVo d: highActions) {
            d.initImages(basePath);
        }

        return R.ok()
                .put("key" , new HighActionExcelService().exportExcel(highActions));
    }

    /**
     * 保存和修改公用的
     * @param highAction 传递的实体
     * @return R
     */
    @PostMapping("/save")
    @ApiOperation(value="保存", notes="保存信息接口" , httpMethod = "POST" , response = R.class)
    public R highActionSave(@RequestBody @Valid HighAction highAction){
        if (highAction.getId() == null){
            highAction.setCreateUid(ShiroUtils.getUserId());
            highAction.setCreateTime(new Date());
        }
        boolean rs = iHighActionService.saveOrUpdate(highAction);
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
    @ApiImplicitParam(name = "id", value = "id", required = true )
    @ApiOperation(value="根据id删除", notes="根据id删除接口" , httpMethod = "POST" , response = R.class)
    public R highActionDelete(@PathVariable String id){
        boolean rs = iHighActionService.removeById(id);
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
    @ApiImplicitParam(name = "ids", value = "ids", required = true )
    @ApiOperation(value="批量删除", notes="批量删除接口" , httpMethod = "POST" , response = R.class)
    public R deleteBatchIds(@RequestBody Map<String,List<String>> requestMap){
        List<String> ids = requestMap.get("ids");
        boolean rs = iHighActionService.removeByIds(ids);
        if (rs){
            return R.ok().put("data" , ids.size());
        }
        return R.error();
    }
}
