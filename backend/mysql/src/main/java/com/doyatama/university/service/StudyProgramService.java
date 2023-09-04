package com.doyatama.university.service;

import com.doyatama.university.exception.BadRequestException;
import com.doyatama.university.exception.ResourceNotFoundException;
import com.doyatama.university.model.StudyProgram;
import com.doyatama.university.model.Department;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.department.DepartmentResponse;
import com.doyatama.university.payload.studyProgram.StudyProgramRequest;
import com.doyatama.university.payload.studyProgram.StudyProgramResponse;
import com.doyatama.university.repository.DepartmentRepository;
import com.doyatama.university.repository.StudyProgramRepository;
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
public class StudyProgramService {
    @Autowired
    private StudyProgramRepository studyProgramRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudyProgramService.class);

    public PagedResponse<StudyProgramResponse> getAllStudyProgram(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<StudyProgram> studyPrograms = studyProgramRepository.findAll(pageable);

        if(studyPrograms.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), studyPrograms.getNumber(),
                    studyPrograms.getSize(), studyPrograms.getTotalElements(), studyPrograms.getTotalPages(), studyPrograms.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<StudyProgramResponse> studyProgramResponses = studyPrograms.map(asResponse -> {
            Department departmentRepo = departmentRepository.findById(asResponse.getDepartment().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("Department", "id", asResponse.getDepartment().getId()));
            DepartmentResponse department = new DepartmentResponse(departmentRepo.getId(), departmentRepo.getName(), departmentRepo.getDescription(), departmentRepo.getCreatedAt(), departmentRepo.getUpdatedAt());
            StudyProgramResponse studyProgramResponse = new StudyProgramResponse();
            studyProgramResponse.setId(asResponse.getId());
            studyProgramResponse.setName(asResponse.getName());
            studyProgramResponse.setDescription(asResponse.getDescription());
            studyProgramResponse.setDepartment(department);
            studyProgramResponse.setCreatedAt(asResponse.getCreatedAt());
            studyProgramResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return studyProgramResponse;
        }).getContent();

        return new PagedResponse<>(studyProgramResponses, studyPrograms.getNumber(),
                studyPrograms.getSize(), studyPrograms.getTotalElements(), studyPrograms.getTotalPages(), studyPrograms.isLast(), 200);
    }

    public StudyProgram createStudyProgram(UserPrincipal currentUser, StudyProgramRequest studyProgramRequest) {
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.setDepartment(new Department(studyProgramRequest.getDepartment_id()));
        studyProgram.setName(studyProgramRequest.getName());
        studyProgram.setDescription(studyProgramRequest.getDescription());
        studyProgram.setCreatedBy(currentUser.getId());
        studyProgram.setUpdatedBy(currentUser.getId());
        return studyProgramRepository.save(studyProgram);
    }

    public StudyProgramResponse getStudyProgramById(Long studyProgramId) {
        StudyProgram studyProgram = studyProgramRepository.findById(studyProgramId).orElseThrow(
                () -> new ResourceNotFoundException("StudyProgram", "id", studyProgramId));
        Department departmentRepo = departmentRepository.findById(studyProgram.getDepartment().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Department", "id", studyProgram.getDepartment().getId()));
        DepartmentResponse department = new DepartmentResponse(departmentRepo.getId(), departmentRepo.getName(), departmentRepo.getDescription(), departmentRepo.getCreatedAt(), departmentRepo.getUpdatedAt());

        StudyProgramResponse studyProgramResponse = new StudyProgramResponse();
        studyProgramResponse.setId(studyProgram.getId());
        studyProgramResponse.setName(studyProgram.getName());
        studyProgramResponse.setDescription(studyProgram.getDescription());
        studyProgramResponse.setDepartment(department);
        studyProgramResponse.setCreatedAt(studyProgram.getCreatedAt());
        studyProgramResponse.setUpdatedAt(studyProgram.getUpdatedAt());
        return studyProgramResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public StudyProgram updateStudyProgram(StudyProgramRequest studyProgramReq, Long id, UserPrincipal currentUser){
        return studyProgramRepository.findById(id).map(studyProgram -> {
            studyProgram.setDepartment(new Department(studyProgramReq.getDepartment_id()));
            studyProgram.setName(studyProgramReq.getName());
            studyProgram.setDescription(studyProgramReq.getDescription());
            studyProgram.setCreatedBy(currentUser.getId());
            studyProgram.setUpdatedBy(currentUser.getId());
            return studyProgramRepository.save(studyProgram);
        }).orElseThrow(() -> new ResourceNotFoundException("StudyProgram" , "id" , id));
    }

    public void deleteStudyProgramById(Long id){
        Optional<StudyProgram> studyProgram = studyProgramRepository.findById(id);
        if(studyProgram.isPresent()){
            studyProgramRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("StudyProgram", "id", id);
        }
    }
}