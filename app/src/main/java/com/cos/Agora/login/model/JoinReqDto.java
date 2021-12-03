package com.cos.Agora.login.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JoinReqDto {
    private String phoneNumber; // 회원가입된 회원의 핸드폰번호를 통해 entity를 매핑시켜줘야함
    private String association;
    private int age;
    private String sex;
    private String interest;
//    private LocationReqDto locationReqDto;
    private Double latitude;
    private Double longitude;
}

