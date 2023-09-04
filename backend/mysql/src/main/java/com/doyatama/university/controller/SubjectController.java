package com.doyatama.university.controller;

import com.doyatama.university.model.Subject;
import com.doyatama.university.payload.ApiResponse;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.subject.SubjectRequest;
import com.doyatama.university.payload.subject.SubjectResponse;
import com.doyatama.university.repository.SubjectRepository;
import com.doyatama.university.repository.UserRepository;
import com.doyatama.university.security.CurrentUser;
import com.doyatama.university.security.UserPrincipal;
import com.doyatama.university.service.SubjectService;
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
@RequestMapping("/api/subject")
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectService subjectService;

    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<SubjectResponse> getSubject(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return subjectService.getAllSubject(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createSubject(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody SubjectRequest subjectRequest) {
        Subject subject = subjectService.createSubject(currentUser, subjectRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{subjectId}")
                .buildAndExpand(subject.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Subject Created Successfully"));
    }

    @PutMapping("/{subjectId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateSubjectById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "subjectId") Long subjectId, @Valid @RequestBody SubjectRequest subjectRequest) {
        Subject subject = subjectService.updateSubject(subjectRequest, subjectId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{subjectId}")
                .buildAndExpand(subject.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Subject Updated Successfully"));
    }

    @GetMapping("/{subjectId}")
    @Secured("ROLE_ADMINISTRATOR")
    public SubjectResponse getSubjectById(@PathVariable Long subjectId) {
        return subjectService.getSubjectById(subjectId);
    }

    @DeleteMapping("/{subjectId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteSubject(@PathVariable (value = "subjectId") Long subjectId){
        subjectService.deleteSubjectById(subjectId);
        return HttpStatus.FORBIDDEN;
    }
}
