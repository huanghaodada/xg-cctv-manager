package com.xg.cctv.mybatis.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;

/**
 * <p>
 * 西港监控部日志表
 * </p>
 *
 * @author lorenzo
 * @since 2020-01-30
 */
public class DailyLog extends Model<DailyLog> {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 序号
     */
    private String no;

    /**
     * 日期
     */
    private Date date;

    /**
     * 台号
     */
    private String tableCode;

    /**
     * 主题
     */
    private String subject;

    /**
     * 细节
     */
    private String details;

    /**
     * 通知人
     */
    private String alerterName;

    /**
     * 部门ID
     */
    private Integer departmentId;

    /**
     * 监控部
     */
    private String monitor;

    /**
     * 结论
     */
    private String conclusion;

    /**
     * 创建人
     */
    private Long createUid;

    /**
     * 创建时间
     */
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAlerterName() {
        return alerterName;
    }

    public void setAlerterName(String alerterName) {
        this.alerterName = alerterName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DailyLog{" +
        "id=" + id +
        ", no=" + no +
        ", date=" + date +
        ", tableCode=" + tableCode +
        ", subject=" + subject +
        ", details=" + details +
        ", alerterName=" + alerterName +
        ", departmentId=" + departmentId +
        ", monitor=" + monitor +
        ", conclusion=" + conclusion +
        ", createUid=" + createUid +
        ", createTime=" + createTime +
        "}";
    }
}