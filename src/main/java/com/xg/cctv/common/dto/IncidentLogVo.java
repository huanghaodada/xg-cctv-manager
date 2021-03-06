package com.xg.cctv.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xg.cctv.mybatis.po.IncidentLog;
import org.apache.commons.lang3.StringUtils;
import org.jxls.util.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IncidentLogVo extends IncidentLog{

    /********************************部门相关字段*********************************/
    /**
     * 部门中文名称
     */
    private String name;


    /**
     * 部门编号
     */
    private String departmentCode;
    /********************************部门相关字段end*********************************/

    /********************************员工相关字段*********************************/
    private String username;
    /********************************员工相关字段end*********************************/

    /********************************支持使用Jxls的excel模板导出图片相关字段*********************************/
    @TableField(exist=false)
    private List<byte[]> imgs;

    public List<byte[]> getImgs() {
        return imgs;
    }

    public void setImgs(List<byte[]> imgs) {
        this.imgs = imgs;
    }

    /**
     * 将实体记载的图片路径解析成文件，并转换成byte数组以供jxls模板渲染
     * @param basePath
     * @throws IOException
     */
    public void initImages(String basePath) throws IOException {
        String urls = getUrls();
        if (!StringUtils.isNotEmpty(urls)){
            return;
        }
        String[] urlArr = urls.split(",");
        List<byte[]> imgs = new ArrayList<>();
        for (int i = 0; i < urlArr.length; i++) {
            String url = urlArr[i];
            FileInputStream inputStream = new FileInputStream(basePath + url);
            byte[] bytes = Util.toByteArray(inputStream);
            imgs.add(bytes);
        }
        setImgs(imgs);
    }
    /********************************支持使用Jxls的excel模板导出图片相关字段end*********************************/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
