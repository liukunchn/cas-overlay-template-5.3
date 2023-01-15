package com.maoyou.cas.authentication.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author maoyou
 * @since 2022-07-03
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private String userid;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 密码错误次数
     */
    private Integer passworddefaultnum;

    /**
     * 密码最后修改时间
     */
    private Date pwdlastmodifydate;

    /**
     * 是否锁定
     */
    private String locked;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 证件号码
     */
    private String idcardno;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 创建人
     */
    private String createuser;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 销毁标识
     */
    private String destory;

    /**
     * 排序号
     */
    private Integer orderno;

    /**
     * 有效标识
     */
    private String deleted;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPassworddefaultnum() {
        return passworddefaultnum;
    }

    public void setPassworddefaultnum(Integer passworddefaultnum) {
        this.passworddefaultnum = passworddefaultnum;
    }

    public Date getPwdlastmodifydate() {
        return pwdlastmodifydate;
    }

    public void setPwdlastmodifydate(Date pwdlastmodifydate) {
        this.pwdlastmodifydate = pwdlastmodifydate;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getDestory() {
        return destory;
    }

    public void setDestory(String destory) {
        this.destory = destory;
    }

    public Integer getOrderno() {
        return orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
