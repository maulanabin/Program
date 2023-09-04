package com.doyatama.university.service;

import com.doyatama.university.exception.BadRequestException;
import com.doyatama.university.exception.ResourceNotFoundException;
import com.doyatama.university.model.Religion;
import com.doyatama.university.model.StudyProgram;
import com.doyatama.university.model.Subject;
import com.doyatama.university.model.SubjectGroup;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.religion.ReligionResponse;
import com.doyatama.university.payload.studyProgram.StudyProgramResponse;
import com.doyatama.university.payload.studyProgram.StudyProgramWithoutDepartmentResponse;
import com.doyatama.university.payload.subject.SubjectRequest;
import com.doyatama.university.payload.subject.SubjectResponse;
import com.doyatama.university.payload.subjectGroup.SubjectGroupResponse;
import com.doyatama.university.repository.StudyProgramRepository;
import com.doyatama.university.repository.SubjectGroupRepository;
import com.doyatama.university.repository.SubjectRepository;
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

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Doyatama
 */
@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SubjectGroupRepository subjectGroupRepository;
    @Autowired
    private StudyProgramRepository studyProgramRepository;

    private static final Logger logger = LoggerFactory.getLogger(SubjectService.class);

    public PagedResponse<SubjectResponse> getAllSubject(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Subject> subjects = subjectRepository.findAll(pageable);

        if(subjects.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), subjects.getNumber(),
                    subjects.getSize(), subjects.getTotalElements(), subjects.getTotalPages(), subjects.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<SubjectResponse> subjectResponses = subjects.map(asResponse -> {
            StudyProgram studyProgramRepo = studyProgramRepository.findById(asResponse.getStudyProgram().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("Study Program", "id", asResponse.getStudyProgram().getId()));
            StudyProgramWithoutDepartmentResponse studyProgram = new StudyProgramWithoutDepartmentResponse(studyProgramRepo.getId(), studyProgramRepo.getName(), studyProgramRepo.getDescription(), studyProgramRepo.getCreatedAt(), studyProgramRepo.getUpdatedAt());

            SubjectGroup subjectGroupRepo = subjectGroupRepository.findById(asResponse.getSubjectGroup().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("Subject Group", "id", asResponse.getSubjectGroup().getId()));
            SubjectGroupResponse subjectGroup = new SubjectGroupResponse(subjectGroupRepo.getId(), subjectGroupRepo.getName(), subjectGroupRepo.getDescription(), subjectGroupRepo.getCreatedAt(), subjectGroupRepo.getUpdatedAt());

            SubjectResponse subjectResponse = new SubjectResponse();
            subjectResponse.setId(asResponse.getId());
            subjectResponse.setStudy_program(studyProgram);
            subjectResponse.setSubject_group(subjectGroup);
            subjectResponse.setName(asResponse.getName());
            subjectResponse.setDescription(asResponse.getDescription());
            subjectResponse.setCreatedAt(asResponse.getCreatedAt());
            subjectResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return subjectResponse;
        }).getContent();

        return new PagedResponse<>(subjectResponses, subjects.getNumber(),
                subjects.getSize(), subjects.getTotalElements(), subjects.getTotalPages(), subjects.isLast(), 200);
    }

    public Subject createSubject(UserPrincipal currentUser, SubjectRequest subjectRequest) {
        Subject subject = new Subject();
        subject.setStudyProgram(new StudyProgram(subjectRequest.getStudy_program_id()));
        subject.setSubjectGroup(new SubjectGroup(subjectRequest.getSubject_group_id()));
        subject.setName(subjectRequest.getName());
        subject.setDescription(subjectRequest.getDescription());
        subject.setCreatedBy(currentUser.getId());
        subject.setUpdatedBy(currentUser.getId());
        return subjectRepository.save(subject);
    }

    public SubjectResponse getSubjectById(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "id", subjectId));

        StudyProgram studyProgramRepo = studyProgramRepository.findById(subject.getStudyProgram().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Study Program", "id", subject.getStudyProgram().getId()));
        StudyProgramWithoutDepartmentResponse studyProgram = new StudyProgramWithoutDepartmentResponse(studyProgramRepo.getId(), studyProgramRepo.getName(), studyProgramRepo.getDescription(), studyProgramRepo.getCreatedAt(), studyProgramRepo.getUpdatedAt());

        SubjectGroup subjectGroupRepo = subjectGroupRepository.findById(subject.getSubjectGroup().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Subject Group", "id", subject.getSubjectGroup().getId()));
        SubjectGroupResponse subjectGroup = new SubjectGroupResponse(subjectGroupRepo.getId(), subjectGroupRepo.getName(), subjectGroupRepo.getDescription(), subjectGroupRepo.getCreatedAt(), subjectGroupRepo.getUpdatedAt());


        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setId(subject.getId());
        subjectResponse.setName(subject.getName());
        subjectResponse.setSubject_group(subjectGroup);
        subjectResponse.setStudy_program(studyProgram);
        subjectResponse.setDescription(subject.getDescription());
        subjectResponse.setCreatedAt(subject.getCreatedAt());
        subjectResponse.setUpdatedAt(subject.getUpdatedAt());
        return subjectResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Subject updateSubject(SubjectRequest subjectReq, Long id, UserPrincipal currentUser){
        return subjectRepository.findById(id).map(subject -> {
            subject.setStudyProgram(new StudyProgram(subjectReq.getStudy_program_id()));
            subject.setSubjectGroup(new SubjectGroup(subjectReq.getSubject_group_id()));
            subject.setName(subjectReq.getName());
            subject.setDescription(subjectReq.getDescription());
            subject.setUpdatedBy(currentUser.getId());
            subject.setUpdatedAt(Instant.now());
            return subjectRepository.save(subject);
        }).orElseThrow(() -> new ResourceNotFoundException("Subject" , "id" , id));
    }

    public void deleteSubjectById(Long id){
        Optional<Subject> subject = subjectRepository.findById(id);
        if(subject.isPresent()){
            subjectRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Subject", "id", id);
        }
    }
}
