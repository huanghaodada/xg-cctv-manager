package com.xg.cctv.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import com.xg.cctv.mybatis.po.JsException;
import com.xg.cctv.service.JsExceptionService;
import com.xg.cctv.common.util.R;

import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Map;

/**
 *
 * @author lorenzo
 * @since 2020-01-19
 */
@RestController
@RequestMapping("/jsException")
public class JsExceptionController {
    @Autowired
    public JsExceptionService iJsExceptionService;

    /**
     * 分页查询数据
     *
     * @param page  分页信息
     * @param jsException 查询条件
     * @return
     */
    @GetMapping("/getJsExceptionPageList")
    public R getJsExceptionList(Page<JsException> page,JsException jsException){
        return R.ok().put("page" , iJsExceptionService.selectPage(page, jsException));
    }

    /**
     * 保存和修改公用的
     * @param jsException 传递的实体
     * @return R
     */
    @PostMapping("/jsExceptionSave")
    public R jsExceptionSave(@RequestBody JsException jsException){
        boolean rs = iJsExceptionService.saveOrUpdate(jsException);
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
    @PostMapping("/jsExceptionDelete/{id}")
    public R jsExceptionDelete(@PathVariable String id){
        boolean rs = iJsExceptionService.removeById(id);
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
    @PostMapping("/jsExceptionBatchDelete")
    public R deleteBatchIds(@RequestBody Map<String,List<String>> requestMap){
        List<String> ids = requestMap.get("ids");
        boolean rs = iJsExceptionService.removeByIds(ids);
        if (rs){
            return R.ok().put("row" , ids.size());
        }
        return R.error();
    }
}