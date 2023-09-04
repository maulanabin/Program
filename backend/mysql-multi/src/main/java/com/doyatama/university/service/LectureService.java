package com.doyatama.university.service;

import com.doyatama.university.exception.BadRequestException;
import com.doyatama.university.exception.ResourceNotFoundException;
import com.doyatama.university.model.*;
import com.doyatama.university.model.Lecture;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.department.DepartmentResponse;
import com.doyatama.university.payload.lecture.LectureRequest;
import com.doyatama.university.payload.lecture.LectureResponse;
import com.doyatama.university.payload.religion.ReligionResponse;
import com.doyatama.university.repository.DepartmentRepository;
import com.doyatama.university.repository.LectureRepository;
import com.doyatama.university.repository.ReligionRepository;
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
public class LectureService {
    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private ReligionRepository religionRepository;

    private static final Logger logger = LoggerFactory.getLogger(LectureService.class);

    public PagedResponse<LectureResponse> getAllLecture(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Lecture> lectures = lectureRepository.findAll(pageable);

        if(lectures.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), lectures.getNumber(),
                    lectures.getSize(), lectures.getTotalElements(), lectures.getTotalPages(), lectures.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<LectureResponse> lectureResponses = lectures.map(asResponse -> {
            Religion religionRepo = religionRepository.findById(asResponse.getReligion().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("Religion", "id", asResponse.getReligion().getId()));
            ReligionResponse religion = new ReligionResponse(religionRepo.getId(), religionRepo.getName(), religionRepo.getDescription(), religionRepo.getCreatedAt(), religionRepo.getUpdatedAt());
            LectureResponse lectureResponse = new LectureResponse();
            lectureResponse.setId(asResponse.getId());
            lectureResponse.setUser_id(asResponse.getUser().getId());
            lectureResponse.setNidn(asResponse.getNidn());
            lectureResponse.setName(asResponse.getName());
            lectureResponse.setPlace_born(asResponse.getPlace_born());
            lectureResponse.setDate_born(asResponse.getDate_born());
            lectureResponse.setGender(asResponse.getGender());
            lectureResponse.setReligion(religion);
            lectureResponse.setPhone(asResponse.getPhone());
            lectureResponse.setAddress(asResponse.getAddress());
            lectureResponse.setStatus(asResponse.getStatus());
            lectureResponse.setCreatedAt(asResponse.getCreatedAt());
            lectureResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return lectureResponse;
        }).getContent();

        return new PagedResponse<>(lectureResponses, lectures.getNumber(),
                lectures.getSize(), lectures.getTotalElements(), lectures.getTotalPages(), lectures.isLast(), 200);
    }

    public Lecture createLecture(UserPrincipal currentUser, LectureRequest lectureRequest) {
        Lecture lecture = new Lecture();
        lecture.setUser(new User(lectureRequest.getUser_id()));
        lecture.setNidn(lectureRequest.getNidn());
        lecture.setName(lectureRequest.getName());
        lecture.setPlace_born(lectureRequest.getPlace_born());
        lecture.setDate_born(lectureRequest.getDate_born());
        lecture.setGender(lectureRequest.getGender());
        lecture.setReligion(new Religion(lectureRequest.getReligion_id()));
        lecture.setPhone(lectureRequest.getPhone());
        lecture.setAddress(lectureRequest.getAddress());
        lecture.setStatus(lectureRequest.getStatus());
        lecture.setCreatedBy(currentUser.getId());
        lecture.setUpdatedBy(currentUser.getId());
        return lectureRepository.save(lecture);
    }

    public LectureResponse getLectureById(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
                () -> new ResourceNotFoundException("Lecture", "id", lectureId));

        Religion religionRepo = religionRepository.findById(lecture.getReligion().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Religion", "id", lecture.getReligion().getId()));
        ReligionResponse religion = new ReligionResponse(religionRepo.getId(), religionRepo.getName(), religionRepo.getDescription(), religionRepo.getCreatedAt(), religionRepo.getUpdatedAt());

        LectureResponse lectureResponse = new LectureResponse();
        lectureResponse.setId(lecture.getId());
        lectureResponse.setUser_id(lecture.getUser().getId());
        lectureResponse.setNidn(lecture.getNidn());
        lectureResponse.setName(lecture.getName());
        lectureResponse.setPlace_born(lecture.getPlace_born());
        lectureResponse.setDate_born(lecture.getDate_born());
        lectureResponse.setGender(lecture.getGender());
        lectureResponse.setReligion(religion);
        lectureResponse.setPhone(lecture.getPhone());
        lectureResponse.setAddress(lecture.getAddress());
        lectureResponse.setStatus(lecture.getStatus());
        lectureResponse.setCreatedAt(lecture.getCreatedAt());
        lectureResponse.setUpdatedAt(lecture.getUpdatedAt());
        return lectureResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Lecture updateLecture(LectureRequest lectureReq, Long id, UserPrincipal currentUser){
        return lectureRepository.findById(id).map(lecture -> {
            lecture.setUser(new User(lectureReq.getUser_id()));
            lecture.setNidn(lectureReq.getNidn());
            lecture.setName(lectureReq.getName());
            lecture.setPlace_born(lectureReq.getPlace_born());
            lecture.setDate_born(lectureReq.getDate_born());
            lecture.setGender(lectureReq.getGender());
            lecture.setReligion(new Religion(lectureReq.getReligion_id()));
            lecture.setPhone(lectureReq.getPhone());
            lecture.setAddress(lectureReq.getAddress());
            lecture.setStatus(lectureReq.getStatus());
            lecture.setCreatedBy(currentUser.getId());
            lecture.setUpdatedBy(currentUser.getId());
            return lectureRepository.save(lecture);
        }).orElseThrow(() -> new ResourceNotFoundException("Lecture" , "id" , id));
    }

    public void deleteLectureById(Long id){
        Optional<Lecture> lecture = lectureRepository.findById(id);
        if(lecture.isPresent()){
            lectureRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Lecture", "id", id);
        }
    }
}