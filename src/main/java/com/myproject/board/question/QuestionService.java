package com.myproject.board.question;

import com.myproject.board.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;
    
    public QuestionDto of(QuestionEntity questionEntity){ // 엔티티 -> dto로 반환
        return modelMapper.map(questionEntity, QuestionDto.class);
    }
    public QuestionEntity of(QuestionDto questionDto){ // 엔티티 -> dto로 반환
        return modelMapper.map(questionDto, QuestionEntity.class);
    }

    public List<QuestionDto> getList(){
        List<QuestionEntity> questionEntityList = questionRepository.findAll();
        List<QuestionDto> questionDtoList = questionEntityList.stream().map(question -> of(question)).collect(Collectors.toList());
        return questionDtoList;
    }

    public QuestionDto getQuestion(Integer id){
        Optional<QuestionEntity> questionEntity = questionRepository.findById(id);
        if(questionEntity.isPresent()){
            return of(questionEntity.get());
        } else {
            throw new DataNotFoundException(" 해당하는 데이터가 없습니다."); // DataNotFoundException.java 를 따로 생성해줌
        }
    }

    public QuestionDto createQuestion(String title, String content) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setTitle(title);
        questionEntity.setContent(content);
        questionEntity.setCreateDate(LocalDateTime.now());
        questionRepository.save(questionEntity);
        return of(questionEntity); // 위에서 저장은 entity로 했지만 return은 dto로 변환해서 반환 - service의 반환은 dto로
    }

    public QuestionDto modifyQuestion(String title, String content, Integer id){
        Optional<QuestionEntity> questionEntity = questionRepository.findById(id);
        QuestionDto questionDto = of(questionEntity.get());
        questionDto.setTitle(title);
        questionDto.setContent(content);
        questionDto.setCreateDate(LocalDateTime.now());
        QuestionEntity savedEntity = of(questionDto);
        questionRepository.save(savedEntity);
        return questionDto;

    }

}
