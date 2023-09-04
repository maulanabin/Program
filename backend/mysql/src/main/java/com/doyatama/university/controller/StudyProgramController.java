package com.doyatama.university.controller;

import com.doyatama.university.model.StudyProgram;
import com.doyatama.university.payload.ApiResponse;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.studyProgram.StudyProgramRequest;
import com.doyatama.university.payload.studyProgram.StudyProgramResponse;
import com.doyatama.university.repository.StudyProgramRepository;
import com.doyatama.university.repository.UserRepository;
import com.doyatama.university.security.CurrentUser;
import com.doyatama.university.security.UserPrincipal;
import com.doyatama.university.service.StudyProgramService;
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
@RequestMapping("/api/study-program")
public class StudyProgramController {
    @Autowired
    private StudyProgramRepository studyProgramRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyProgramService studyProgramService;

    private static final Logger logger = LoggerFactory.getLogger(StudyProgramController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<StudyProgramResponse> getStudyProgram(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return studyProgramService.getAllStudyProgram(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createStudyProgram(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody StudyProgramRequest studyProgramRequest) {
        StudyProgram studyProgram = studyProgramService.createStudyProgram(currentUser, studyProgramRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{studyProgramId}")
                .buildAndExpand(studyProgram.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "StudyProgram Created Successfully"));
    }

    @PutMapping("/{studyProgramId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateStudyProgramById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "studyProgramId") Long studyProgramId, @Valid @RequestBody StudyProgramRequest studyProgramRequest) {
        StudyProgram studyProgram = studyProgramService.updateStudyProgram(studyProgramRequest, studyProgramId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{studyProgramId}")
                .buildAndExpand(studyProgram.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "StudyProgram Updated Successfully"));
    }

    @GetMapping("/{studyProgramId}")
    @Secured("ROLE_ADMINISTRATOR")
    public StudyProgramResponse getStudyProgramById(@PathVariable Long studyProgramId) {
        return studyProgramService.getStudyProgramById(studyProgramId);
    }

    @DeleteMapping("/{studyProgramId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteStudyProgram(@PathVariable (value = "studyProgramId") Long studyProgramId){
        studyProgramService.deleteStudyProgramById(studyProgramId);
        return HttpStatus.FORBIDDEN;
    }
}
