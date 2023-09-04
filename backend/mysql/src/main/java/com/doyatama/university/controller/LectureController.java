package com.doyatama.university.controller;

import com.doyatama.university.model.Lecture;
import com.doyatama.university.payload.ApiResponse;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.lecture.LectureRequest;
import com.doyatama.university.payload.lecture.LectureResponse;
import com.doyatama.university.repository.LectureRepository;
import com.doyatama.university.repository.UserRepository;
import com.doyatama.university.security.CurrentUser;
import com.doyatama.university.security.UserPrincipal;
import com.doyatama.university.service.LectureService;
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
@RequestMapping("/api/lecture")
public class LectureController {
    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureService lectureService;

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<LectureResponse> getLecture(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return lectureService.getAllLecture(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createLecture(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody LectureRequest lectureRequest) {
        Lecture lecture = lectureService.createLecture(currentUser, lectureRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{lectureId}")
                .buildAndExpand(lecture.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Lecture Created Successfully"));
    }

    @PutMapping("/{lectureId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateLectureById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "lectureId") Long lectureId, @Valid @RequestBody LectureRequest lectureRequest) {
        Lecture lecture = lectureService.updateLecture(lectureRequest, lectureId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{lectureId}")
                .buildAndExpand(lecture.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Lecture Updated Successfully"));
    }

    @GetMapping("/{lectureId}")
    @Secured("ROLE_ADMINISTRATOR")
    public LectureResponse getLectureById(@PathVariable Long lectureId) {
        return lectureService.getLectureById(lectureId);
    }

    @DeleteMapping("/{lectureId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteLecture(@PathVariable (value = "lectureId") Long lectureId){
        lectureService.deleteLectureById(lectureId);
        return HttpStatus.FORBIDDEN;
    }
}
