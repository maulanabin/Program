package com.doyatama.university.service;

import com.doyatama.university.exception.BadRequestException;
import com.doyatama.university.exception.ResourceNotFoundException;
import com.doyatama.university.model.Chapter;
import com.doyatama.university.model.Question;
import com.doyatama.university.model.Subject;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.chapter.ChapterRequest;
import com.doyatama.university.payload.chapter.ChapterResponse;
import com.doyatama.university.repository.ChapterRepository;
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
public class ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    private static final Logger logger = LoggerFactory.getLogger(ChapterService.class);

    public PagedResponse<ChapterResponse> getAllChapters(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Chapter> chapters = chapterRepository.findAll(pageable);

        if(chapters.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), chapters.getNumber(),
                    chapters.getSize(), chapters.getTotalElements(), chapters.getTotalPages(), chapters.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<ChapterResponse> chapterResponses = chapters.map(asResponse -> {
            ChapterResponse chapterResponse = new ChapterResponse();
            chapterResponse.setId(asResponse.getId());
            chapterResponse.setSubject_id(asResponse.getSubject().getId());
            chapterResponse.setTitle(asResponse.getTitle());
            chapterResponse.setDescription(asResponse.getDescription());
            chapterResponse.setCreatedAt(asResponse.getCreatedAt());
            chapterResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return chapterResponse;
        }).getContent();

        return new PagedResponse<>(chapterResponses, chapters.getNumber(),
                chapters.getSize(), chapters.getTotalElements(), chapters.getTotalPages(), chapters.isLast(), 200);
    }

    public Chapter createChapter(UserPrincipal currentUser, ChapterRequest chapterRequest) {
        Chapter chapter = new Chapter();
        chapter.setSubject(new Subject(chapterRequest.getSubject_id()));
        chapter.setTitle(chapterRequest.getTitle());
        chapter.setDescription(chapterRequest.getDescription());
        chapter.setCreatedBy(currentUser.getId());
        chapter.setUpdatedBy(currentUser.getId());
        return chapterRepository.save(chapter);
    }

    public ChapterResponse getChapterById(Long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                () -> new ResourceNotFoundException("Chapter", "id", chapterId));

        ChapterResponse chapterResponse = new ChapterResponse();
        chapterResponse.setId(chapter.getId());
        chapterResponse.setSubject_id(chapter.getSubject().getId());
        chapterResponse.setTitle(chapter.getTitle());
        chapterResponse.setDescription(chapter.getDescription());
        chapterResponse.setCreatedAt(chapter.getCreatedAt());
        chapterResponse.setUpdatedAt(chapter.getUpdatedAt());
        return chapterResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Chapter updateChapter(ChapterRequest chapterReq, Long id, UserPrincipal currentUser){
        return chapterRepository.findById(id).map(chapter -> {
            chapter.setSubject(new Subject(chapterReq.getSubject_id()));
            chapter.setTitle(chapterReq.getTitle());
            chapter.setDescription(chapterReq.getDescription());
            chapter.setCreatedBy(currentUser.getId());
            chapter.setUpdatedBy(currentUser.getId());
            return chapterRepository.save(chapter);
        }).orElseThrow(() -> new ResourceNotFoundException("Chapter" , "id" , id));
    }

    public void deleteChapterById(Long id){
        Optional<Chapter> chapter = chapterRepository.findById(id);
        if(chapter.isPresent()){
            chapterRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Chapter", "id", id);
        }
    }
}