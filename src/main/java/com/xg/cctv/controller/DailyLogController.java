package com.xg.cctv.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import com.xg.cctv.mybatis.po.DailyLog;
import com.xg.cctv.service.DailyLogService;
import com.xg.cctv.common.util.R;

import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Map;

/**
 *
 * @author lorenzo
 * @since 2020-01-30
 */
@RestController
@RequestMapping("/dailyLog")
public class DailyLogController {
    @Autowired
    public DailyLogService iDailyLogService;

    /**
     * 分页查询数据
     *
     * @param page  分页信息
     * @param dailyLog 查询条件
     * @return
     */
    @GetMapping("/page")
    public R getDailyLogList(Page<DailyLog> page,DailyLog dailyLog){
        return R.ok().put("data" , iDailyLogService.selectPage(page, dailyLog));
    }

    /**
     * 保存和修改公用的
     * @param dailyLog 传递的实体
     * @return R
     */
    @PostMapping("/save")
    public R dailyLogSave(@RequestBody DailyLog dailyLog){
        boolean rs = iDailyLogService.saveOrUpdate(dailyLog);
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
    public R dailyLogDelete(@PathVariable String id){
        boolean rs = iDailyLogService.removeById(id);
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
    public R deleteBatchIds(@RequestBody Map<String,List<String>> requestMap){
        List<String> ids = requestMap.get("ids");
        boolean rs = iDailyLogService.removeByIds(ids);
        if (rs){
            return R.ok().put("data" , ids.size());
        }
        return R.error();
    }
}