package com.doyatama.university.controller;

import com.doyatama.university.model.Answer;
import com.doyatama.university.payload.ApiResponse;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.answer.AnswerRequest;
import com.doyatama.university.payload.answer.AnswerResponse;
import com.doyatama.university.repository.AnswerRepository;
import com.doyatama.university.repository.UserRepository;
import com.doyatama.university.security.CurrentUser;
import com.doyatama.university.security.UserPrincipal;
import com.doyatama.university.service.AnswerService;
import com.doyatama.university.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerService answerService;

    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<AnswerResponse> getAnswer(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return answerService.getAllAnswers(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createAnswer(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody AnswerRequest answerRequest) {
        Answer answer = answerService.createAnswer(currentUser, answerRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{answerId}")
                .buildAndExpand(answer.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Answer Created Successfully"));
    }

    @PutMapping("/{answerId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateAnswerById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "answerId") Long answerId, @Valid @RequestBody AnswerRequest answerRequest) {
        Answer answer = answerService.updateAnswer(answerRequest, answerId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{answerId}")
                .buildAndExpand(answer.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Answer Updated Successfully"));
    }

    @GetMapping("/{answerId}")
    @Secured("ROLE_ADMINISTRATOR")
    public AnswerResponse getAnswerById(@PathVariable Long answerId) {
        return answerService.getAnswerById(answerId);
    }

    @DeleteMapping("/{answerId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteAnswer(@PathVariable (value = "answerId") Long answerId){
        answerService.deleteAnswerById(answerId);
        return HttpStatus.FORBIDDEN;
    }
}
