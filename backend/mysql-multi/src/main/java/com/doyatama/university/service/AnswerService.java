package com.doyatama.university.service;

import com.doyatama.university.exception.BadRequestException;
import com.doyatama.university.exception.ResourceNotFoundException;
import com.doyatama.university.model.Answer;
import com.doyatama.university.model.Question;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.answer.AnswerRequest;
import com.doyatama.university.payload.answer.AnswerResponse;
import com.doyatama.university.repository.AnswerRepository;
import com.doyatama.university.security.UserPrincipal;
import com.doyatama.university.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Doyatama
 */
@Service
public class AnswerService {
    @Autowired
    private AnswerRepository anwerRepository;

    private static final Logger logger = LoggerFactory.getLogger(AnswerService.class);

    public PagedResponse<AnswerResponse> getAllAnswers(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Answer> answers = anwerRepository.findAll(pageable);

        if(answers.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), answers.getNumber(),
                    answers.getSize(), answers.getTotalElements(), answers.getTotalPages(), answers.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<AnswerResponse> answerResponses = answers.map(asResponse -> {
            AnswerResponse answerResponse = new AnswerResponse();
            answerResponse.setId(asResponse.getId());
            answerResponse.setQuestion_id(asResponse.getQuestion().getId());
            answerResponse.setDescription(asResponse.getDescription());
            answerResponse.setExplanation(asResponse.getExplanation());
            answerResponse.setIs_right(asResponse.getIs_right());
            answerResponse.setCreatedAt(asResponse.getCreatedAt());
            answerResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return answerResponse;
        }).getContent();

        return new PagedResponse<>(answerResponses, answers.getNumber(),
                answers.getSize(), answers.getTotalElements(), answers.getTotalPages(), answers.isLast(), 200);
    }

    public Answer createAnswer(UserPrincipal currentUser, AnswerRequest answerRequest) {
        Answer answer = new Answer();
        answer.setQuestion(new Question(answerRequest.getQuestion_id()));
        answer.setCreatedBy(currentUser.getId());
        answer.setUpdatedBy(currentUser.getId());
        return anwerRepository.save(answer);
    }

    public AnswerResponse getAnswerById(Long answerId) {
        Answer answer = anwerRepository.findById(answerId).orElseThrow(
                () -> new ResourceNotFoundException("Answer", "id", answerId));

        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setId(answer.getId());
        answerResponse.setQuestion_id(answer.getQuestion().getId());
        answerResponse.setDescription(answer.getDescription());
        answerResponse.setExplanation(answer.getExplanation());
        answerResponse.setIs_right(answer.getIs_right());
        answerResponse.setCreatedAt(answer.getCreatedAt());
        answerResponse.setUpdatedAt(answer.getUpdatedAt());
        return answerResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Answer updateAnswer(AnswerRequest answerReq, Long id, UserPrincipal currentUser){
        return anwerRepository.findById(id).map(answer -> {
            answer.setQuestion(new Question(answerReq.getQuestion_id()));
            answer.setDescription(answerReq.getDescription());
            answer.setExplanation(answerReq.getExplanation());
            answer.setIs_right(answerReq.getIs_right());
            answer.setCreatedBy(currentUser.getId());
            answer.setUpdatedBy(currentUser.getId());
            return anwerRepository.save(answer);
        }).orElseThrow(() -> new ResourceNotFoundException("Answer" , "id" , id));
    }

    public void deleteAnswerById(Long id){
        Optional<Answer> answer = anwerRepository.findById(id);
        if(answer.isPresent()){
            anwerRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Answer", "id", id);
        }
    }
}