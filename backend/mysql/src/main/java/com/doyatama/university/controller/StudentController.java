package com.doyatama.university.controller;

import com.doyatama.university.model.Student;
import com.doyatama.university.payload.ApiResponse;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.student.StudentRequest;
import com.doyatama.university.payload.student.StudentResponse;
import com.doyatama.university.repository.StudentRepository;
import com.doyatama.university.repository.UserRepository;
import com.doyatama.university.security.CurrentUser;
import com.doyatama.university.security.UserPrincipal;
import com.doyatama.university.service.StudentService;
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
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentService studentService;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<StudentResponse> getStudent(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return studentService.getAllStudent(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createStudent(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody StudentRequest studentRequest) {
        Student student = studentService.createStudent(currentUser, studentRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{studentId}")
                .buildAndExpand(student.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Student Created Successfully"));
    }

    @PutMapping("/{studentId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateStudentById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "studentId") Long studentId, @Valid @RequestBody StudentRequest studentRequest) {
        Student student = studentService.updateStudent(studentRequest, studentId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{studentId}")
                .buildAndExpand(student.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Student Updated Successfully"));
    }

    @GetMapping("/{studentId}")
    @Secured("ROLE_ADMINISTRATOR")
    public StudentResponse getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @DeleteMapping("/{studentId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteStudent(@PathVariable (value = "studentId") Long studentId){
        studentService.deleteStudentById(studentId);
        return HttpStatus.FORBIDDEN;
    }
}
