package com.maple27.fzuyibao.model.entity;

/**
 * Created by Maple27 on 2017/11/3.
 */

public class UserEntity {

    private static String sno;
    private static String name;
    private static String nickname;
    private static String phone;
    private static String major;
    private static String grade;
    private static String avatar_path;
    private static String jwt;

    public static String getJwt() {
        return jwt;
    }

    public static void setJwt(String jwt) {
        UserEntity.jwt = jwt;
    }

    public static String getSno() {
        return sno;
    }

    public static void setSno(String sno) {
        UserEntity.sno = sno;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserEntity.name = name;
    }

    public static String getNickname() {
        return nickname;
    }

    public static void setNickname(String nickname) {
        UserEntity.nickname = nickname;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        UserEntity.phone = phone;
    }

    public static String getMajor() {
        return major;
    }

    public static void setMajor(String major) {
        UserEntity.major = major;
    }

    public static String getGrade() {
        return grade;
    }

    public static void setGrade(String grade) {
        UserEntity.grade = grade;
    }

    public static String getAvatar_path() {
        return avatar_path;
    }

    public static void setAvatar_path(String avatar_path) {
        UserEntity.avatar_path = avatar_path;
    }

}
