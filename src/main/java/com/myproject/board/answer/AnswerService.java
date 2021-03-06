package com.myproject.board.answer;

import com.myproject.board.DataNotFoundException;
import com.myproject.board.SiteUser.UserDto;
import com.myproject.board.question.QuestionDto;
import com.myproject.board.question.QuestionEntity;
import com.myproject.board.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public AnswerEntity of(AnswerDto answerDto){ // dto를 entity로 변환
        return modelMapper.map(answerDto, AnswerEntity.class); // map( 이것을 -> 이것으로 변환 );
    }

    public AnswerDto of(AnswerEntity answerEntity){ // entity를 dto로 변환
        return modelMapper.map(answerEntity, AnswerDto.class); // map( 이것을 -> 이것으로 변환 );
    }


    public AnswerDto createAnswer(QuestionDto questionDto, String content, UserDto userDto) {

        AnswerDto answerDto = new AnswerDto();
        answerDto.setContent(content);
        answerDto.setCreateDate(LocalDateTime.now());
        answerDto.setQuestion(questionDto);
        answerDto.setAuthor(userDto);
        AnswerEntity answerEntity = of(answerDto); // 답변 생성 후 저장을 할때는 dto -> entity 변환하여 저장해야함
        answerRepository.save(answerEntity);
        return answerDto;
    }

    public AnswerDto getAnswer(Integer id){
        Optional<AnswerEntity> answerEntity = answerRepository.findById(id);
        if (answerEntity.isPresent()) {
            return of(answerEntity.get());
        }
        else {
            throw new DataNotFoundException("답변을 찾을 수 없습니다.");
        }
    }

    public AnswerDto modifyAnswer(AnswerDto answerDto, String content){
        answerDto.setContent(content);
        answerDto.setModifyDate(LocalDateTime.now());
        answerRepository.save(of(answerDto));
        return answerDto;
    }

    public void deleteAnswer(AnswerDto answerDto){
        answerRepository.delete(of(answerDto));
    }
}
