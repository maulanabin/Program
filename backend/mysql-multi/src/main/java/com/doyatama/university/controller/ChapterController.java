package com.doyatama.university.controller;

import com.doyatama.university.model.Chapter;
import com.doyatama.university.payload.ApiResponse;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.chapter.ChapterRequest;
import com.doyatama.university.payload.chapter.ChapterResponse;
import com.doyatama.university.repository.ChapterRepository;
import com.doyatama.university.repository.UserRepository;
import com.doyatama.university.security.CurrentUser;
import com.doyatama.university.security.UserPrincipal;
import com.doyatama.university.service.ChapterService;
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
@RequestMapping("/api/chapter")
public class ChapterController {
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChapterService chapterService;

    private static final Logger logger = LoggerFactory.getLogger(ChapterController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<ChapterResponse> getChapter(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                   @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return chapterService.getAllChapters(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createChapter(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ChapterRequest chapterRequest) {
        Chapter chapter = chapterService.createChapter(currentUser, chapterRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{chapterId}")
                .buildAndExpand(chapter.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Chapter Created Successfully"));
    }

    @PutMapping("/{chapterId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateChapterById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "chapterId") Long chapterId, @Valid @RequestBody ChapterRequest chapterRequest) {
        Chapter chapter = chapterService.updateChapter(chapterRequest, chapterId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{chapterId}")
                .buildAndExpand(chapter.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Chapter Updated Successfully"));
    }

    @GetMapping("/{chapterId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ChapterResponse getChapterById(@PathVariable Long chapterId) {
        return chapterService.getChapterById(chapterId);
    }

    @DeleteMapping("/{chapterId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteChapter(@PathVariable (value = "chapterId") Long chapterId){
        chapterService.deleteChapterById(chapterId);
        return HttpStatus.FORBIDDEN;
    }
}
