package com.myproject.board.question;

import com.myproject.board.DataNotFoundException;
import com.myproject.board.SiteUser.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public QuestionEntity of(QuestionDto questionDto){ // dto -> 엔티티로 반환
        return modelMapper.map(questionDto, QuestionEntity.class);
    }


    public Page<QuestionDto> getList(int page){ // 페이징처리를 위해 기존반환타입을 List타입에서 Page타입으로 수정함
//        List<QuestionEntity> questionEntityList = questionRepository.findAll();
//        List<QuestionDto> questionDtoList = questionEntityList.stream().map(question -> of(question)).collect(Collectors.toList());
        List<Sort.Order> sorts = new ArrayList<>(); // 게시물 최근순으로 보이게 하기위함
        sorts.add(Sort.Order.desc("createDate")); // createDate를 역순으로
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<QuestionEntity> questionList = questionRepository.findAll(pageable);
        Page<QuestionDto> questionDtoList = questionList.map(question -> of(question));
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

    public QuestionDto createQuestion(String title, String content, UserDto userDto) {
//        QuestionEntity questionEntity = new QuestionEntity();
//        questionEntity.setTitle(title);
//        questionEntity.setContent(content);
//        questionEntity.setCreateDate(LocalDateTime.now());
//        questionRepository.save(questionEntity);
//        return of(questionEntity); // 위에서 저장은 entity로 했지만 return은 dto로 변환해서 반환 - service의 반환은 dto로

        QuestionDto questionDto = new QuestionDto();
        questionDto.setTitle(title);
        questionDto.setContent(content);
        questionDto.setCreateDate(LocalDateTime.now());
        questionDto.setAuthor(userDto);
        QuestionEntity questionEntity = of(questionDto);
        this.questionRepository.save(questionEntity);
        return questionDto;
    }

    public QuestionDto modifyQuestion(String title, String content, QuestionDto questionDto) {
        questionDto.setTitle(title);
        questionDto.setContent(content);
        questionDto.setModifyDate(LocalDateTime.now());
        QuestionEntity questionEntity = of(questionDto);
        questionRepository.save(questionEntity);
        return questionDto;
    }

    public void deleteQuestion(QuestionDto questionDto) {
        questionRepository.delete(of(questionDto));
    }



}
