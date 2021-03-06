package com.xg.cctv.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xg.cctv.common.dto.HighActionVo;
import com.xg.cctv.common.dto.VoService;
import com.xg.cctv.mybatis.po.HighAction;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 西港监控部高额活动表 服务类
 * </p>
 *
 * @author lorenzo
 * @since 2020-02-05
 */
public interface HighActionService extends IService<HighAction>, VoService<HighActionVo> {

    /**
     * 分页查询
     * @param page
     * @param highAction
     * @return
     */
    IPage<HighAction> selectPage(Page<HighAction> page,HighAction highAction);

    IPage<HighAction> selectPage(Page<HighAction> page,Map<String , Object> params);

    /**
     * 查询所有
     * @param highAction
     */
    List<HighAction> selectList(HighAction highAction);

    List<HighAction> selectList(Map<String , Object> params);
}
