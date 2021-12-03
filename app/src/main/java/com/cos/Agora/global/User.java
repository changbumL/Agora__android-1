package com.cos.Agora.global;

import android.app.Application;

import lombok.Data;

@Data
public class User extends Application {
    // Actity와 상관없는 전역변수 activity에 필요한 사용자의 위도와 경도
    // e.g. int id = ((User) getApplication() ).getId();
    private int userId;
    private String phoneNumber;
    private String nickName;
    private double latitude;
    private double longitude;
}
