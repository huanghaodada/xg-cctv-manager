package com.xg.cctv.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xg.cctv.mybatis.po.DailyLog;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 西港监控部日志表 服务类
 * </p>
 *
 * @author lorenzo
 * @since 2020-01-30
 */
public interface DailyLogService extends IService<DailyLog> {

    /**
     * 分页查询
     * @param page
     * @param dailyLog
     * @return
     */
    IPage<DailyLog> selectPage(Page<DailyLog> page,DailyLog dailyLog);

    /**
     * 查询所有
     * @param dailyLog
     */
    List<DailyLog> selectList(DailyLog dailyLog);
}