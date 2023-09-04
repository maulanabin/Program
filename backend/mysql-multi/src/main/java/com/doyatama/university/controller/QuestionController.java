package com.doyatama.university.controller;

import com.doyatama.university.model.Question;
import com.doyatama.university.payload.ApiResponse;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.question.QuestionRequest;
import com.doyatama.university.payload.question.QuestionResponse;
import com.doyatama.university.repository.QuestionRepository;
import com.doyatama.university.repository.UserRepository;
import com.doyatama.university.security.CurrentUser;
import com.doyatama.university.security.UserPrincipal;
import com.doyatama.university.service.QuestionService;
import com.doyatama.university.util.AppConstants;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionService questionService;

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<QuestionResponse> getQuestion(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return questionService.getAllQuestions(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createQuestion(@CurrentUser UserPrincipal currentUser,
                                            @RequestParam("rps_detail_id") Long rps_detail_id,
                                            @RequestParam("name") String name,
                                            @RequestParam("description") String description,
                                            @RequestParam("answer_type") String answer_type,
                                            @RequestParam("question_type") String question_type,
                                            @RequestParam("file") MultipartFile file) throws IOException, JSchException, SftpException {

        QuestionRequest questionRequest = new QuestionRequest(rps_detail_id, name, description, answer_type, question_type);
        Question question = questionService.createQuestion(currentUser, questionRequest, file);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{questionId}")
                .buildAndExpand(question.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Question Created Successfully"));
    }

    @PutMapping("/{questionId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateQuestionById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "questionId") Long questionId, @Valid @RequestBody QuestionRequest questionRequest) {
        Question question = questionService.updateQuestion(questionRequest, questionId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{questionId}")
                .buildAndExpand(question.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Question Updated Successfully"));
    }

    @GetMapping("/{questionId}")
    @Secured("ROLE_ADMINISTRATOR")
    public QuestionResponse getQuestionById(@PathVariable Long questionId) {
        return questionService.getQuestionById(questionId);
    }

    @DeleteMapping("/{questionId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteQuestion(@PathVariable (value = "questionId") Long questionId){
        questionService.deleteQuestionById(questionId);
        return HttpStatus.FORBIDDEN;
    }
}
