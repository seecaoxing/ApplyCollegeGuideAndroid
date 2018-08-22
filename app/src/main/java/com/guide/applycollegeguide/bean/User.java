package com.guide.applycollegeguide.bean;

/**
 * Created by see on 2018/1/16.
 */

public class User extends BaseBean {

    /**
     * userId    	String	是	用户id
     * username   	String	否	手机号
     * email	String	否	邮箱
     * phone	String	否	手机号
     * avatar	String	否	头像
     * rank  	 排名	是	1
     * totalKm	 总共公里数	是	3254
     * totalTime	总共骑行时间	是	546
     * cyclingNumber	骑行条数	是	9
     * associatedNumber	关注数	是	1
     * fansNumber	粉丝数	是	2
     * sex  0男 1女
     */

    private String userId;
    private String username;
    private String email;
    private String phone;
    private String avatar;

    private String rank;
    private String totalKm;
    private String totalTime;
    private String cyclingNumber;
    private String associatedNumber;
    private String fansNumber;
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(String totalKm) {
        this.totalKm = totalKm;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getCyclingNumber() {
        return cyclingNumber;
    }

    public void setCyclingNumber(String cyclingNumber) {
        this.cyclingNumber = cyclingNumber;
    }

    public String getAssociatedNumber() {
        return associatedNumber;
    }

    public void setAssociatedNumber(String associatedNumber) {
        this.associatedNumber = associatedNumber;
    }

    public String getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(String fansNumber) {
        this.fansNumber = fansNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", rank='" + rank + '\'' +
                ", totalKm='" + totalKm + '\'' +
                ", totalTime='" + totalTime + '\'' +
                ", cyclingNumber='" + cyclingNumber + '\'' +
                ", associatedNumber='" + associatedNumber + '\'' +
                ", fansNumber='" + fansNumber + '\'' +
                '}';
    }
}
