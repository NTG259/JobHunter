package com.ntg.JobHunter.domain.dto;

import com.ntg.JobHunter.domain.constant.GenderEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {
    private Long id;

    private String name;

    private int age;

    private String address;

    private GenderEnum gender;

}