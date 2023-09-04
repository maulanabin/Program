package com.doyatama.university.service;

import com.doyatama.university.exception.BadRequestException;
import com.doyatama.university.exception.ResourceNotFoundException;
import com.doyatama.university.model.*;
import com.doyatama.university.model.Student;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.department.DepartmentResponse;
import com.doyatama.university.payload.student.StudentRequest;
import com.doyatama.university.payload.student.StudentResponse;
import com.doyatama.university.payload.religion.ReligionResponse;
import com.doyatama.university.repository.DepartmentRepository;
import com.doyatama.university.repository.StudentRepository;
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
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ReligionRepository religionRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public PagedResponse<StudentResponse> getAllStudent(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Student> students = studentRepository.findAll(pageable);

        if(students.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), students.getNumber(),
                    students.getSize(), students.getTotalElements(), students.getTotalPages(), students.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<StudentResponse> studentResponses = students.map(asResponse -> {
            Religion religionRepo = religionRepository.findById(asResponse.getReligion().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("Religion", "id", asResponse.getReligion().getId()));
            ReligionResponse religion = new ReligionResponse(religionRepo.getId(), religionRepo.getName(), religionRepo.getDescription(), religionRepo.getCreatedAt(), religionRepo.getUpdatedAt());
            StudentResponse studentResponse = new StudentResponse();
            studentResponse.setId(asResponse.getId());
            studentResponse.setUser_id(asResponse.getUser().getId());
            studentResponse.setNim(asResponse.getNim());
            studentResponse.setName(asResponse.getName());
            studentResponse.setPlace_born(asResponse.getPlace_born());
            studentResponse.setDate_born(asResponse.getDate_born());
            studentResponse.setGender(asResponse.getGender());
            studentResponse.setReligion(religion);
            studentResponse.setPhone(asResponse.getPhone());
            studentResponse.setAddress(asResponse.getAddress());
            studentResponse.setStatus(asResponse.getStatus());
            studentResponse.setCreatedAt(asResponse.getCreatedAt());
            studentResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return studentResponse;
        }).getContent();

        return new PagedResponse<>(studentResponses, students.getNumber(),
                students.getSize(), students.getTotalElements(), students.getTotalPages(), students.isLast(), 200);
    }

    public Student createStudent(UserPrincipal currentUser, StudentRequest studentRequest) {
        Student student = new Student();
        student.setUser(new User(studentRequest.getUser_id()));
        student.setNim(studentRequest.getNim());
        student.setName(studentRequest.getName());
        student.setPlace_born(studentRequest.getPlace_born());
        student.setDate_born(studentRequest.getDate_born());
        student.setGender(studentRequest.getGender());
        student.setReligion(new Religion(studentRequest.getReligion_id()));
        student.setPhone(studentRequest.getPhone());
        student.setAddress(studentRequest.getAddress());
        student.setStatus(studentRequest.getStatus());
        student.setCreatedBy(currentUser.getId());
        student.setUpdatedBy(currentUser.getId());
        return studentRepository.save(student);
    }

    public StudentResponse getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", studentId));

        Religion religionRepo = religionRepository.findById(student.getReligion().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Religion", "id", student.getReligion().getId()));
        ReligionResponse religion = new ReligionResponse(religionRepo.getId(), religionRepo.getName(), religionRepo.getDescription(), religionRepo.getCreatedAt(), religionRepo.getUpdatedAt());

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setUser_id(student.getUser().getId());
        studentResponse.setNim(student.getNim());
        studentResponse.setName(student.getName());
        studentResponse.setPlace_born(student.getPlace_born());
        studentResponse.setDate_born(student.getDate_born());
        studentResponse.setGender(student.getGender());
        studentResponse.setReligion(religion);
        studentResponse.setPhone(student.getPhone());
        studentResponse.setAddress(student.getAddress());
        studentResponse.setStatus(student.getStatus());
        studentResponse.setCreatedAt(student.getCreatedAt());
        studentResponse.setUpdatedAt(student.getUpdatedAt());
        return studentResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Student updateStudent(StudentRequest studentReq, Long id, UserPrincipal currentUser){
        return studentRepository.findById(id).map(student -> {
            student.setUser(new User(studentReq.getUser_id()));
            student.setNim(studentReq.getNim());
            student.setName(studentReq.getName());
            student.setPlace_born(studentReq.getPlace_born());
            student.setDate_born(studentReq.getDate_born());
            student.setGender(studentReq.getGender());
            student.setReligion(new Religion(studentReq.getReligion_id()));
            student.setPhone(studentReq.getPhone());
            student.setAddress(studentReq.getAddress());
            student.setStatus(studentReq.getStatus());
            student.setCreatedBy(currentUser.getId());
            student.setUpdatedBy(currentUser.getId());
            return studentRepository.save(student);
        }).orElseThrow(() -> new ResourceNotFoundException("Student" , "id" , id));
    }

    public void deleteStudentById(Long id){
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            studentRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Student", "id", id);
        }
    }
}