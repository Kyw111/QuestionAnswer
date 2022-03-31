package com.myproject.board;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoardConfig {

    @Bean
    public ModelMapper modelMapper(){ // ModelMapper는 객체의 속성을 다른 객체의 속성으로 맵핑해주는 라이브러리
        return new ModelMapper();     // implementation 'org.modelmapper:modelmapper:3.0.0' _ build.grade에 등록
    }

}
