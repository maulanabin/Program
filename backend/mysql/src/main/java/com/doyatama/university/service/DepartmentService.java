package com.doyatama.university.service;

import com.doyatama.university.exception.BadRequestException;
import com.doyatama.university.exception.ResourceNotFoundException;
import com.doyatama.university.model.Department;
import com.doyatama.university.model.Department;
import com.doyatama.university.model.Subject;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.department.DepartmentRequest;
import com.doyatama.university.payload.department.DepartmentResponse;
import com.doyatama.university.repository.DepartmentRepository;
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
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    public PagedResponse<DepartmentResponse> getAllDepartment(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Department> departments = departmentRepository.findAll(pageable);

        if(departments.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), departments.getNumber(),
                    departments.getSize(), departments.getTotalElements(), departments.getTotalPages(), departments.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<DepartmentResponse> departmentResponses = departments.map(asResponse -> {
            DepartmentResponse departmentResponse = new DepartmentResponse();
            departmentResponse.setId(asResponse.getId());
            departmentResponse.setName(asResponse.getName());
            departmentResponse.setDescription(asResponse.getDescription());
            departmentResponse.setCreatedAt(asResponse.getCreatedAt());
            departmentResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return departmentResponse;
        }).getContent();

        return new PagedResponse<>(departmentResponses, departments.getNumber(),
                departments.getSize(), departments.getTotalElements(), departments.getTotalPages(), departments.isLast(), 200);
    }

    public Department createDepartment(UserPrincipal currentUser, DepartmentRequest departmentRequest) {
        Department department = new Department();
        department.setName(departmentRequest.getName());
        department.setDescription(departmentRequest.getDescription());
        department.setCreatedBy(currentUser.getId());
        department.setUpdatedBy(currentUser.getId());
        return departmentRepository.save(department);
    }

    public DepartmentResponse getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new ResourceNotFoundException("Department", "id", departmentId));

        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.setId(department.getId());
        departmentResponse.setName(department.getName());
        departmentResponse.setDescription(department.getDescription());
        departmentResponse.setCreatedAt(department.getCreatedAt());
        departmentResponse.setUpdatedAt(department.getUpdatedAt());
        return departmentResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Department updateDepartment(DepartmentRequest departmentReq, Long id, UserPrincipal currentUser){
        return departmentRepository.findById(id).map(department -> {
            department.setName(departmentReq.getName());
            department.setDescription(departmentReq.getDescription());
            department.setUpdatedBy(currentUser.getId());
            return departmentRepository.save(department);
        }).orElseThrow(() -> new ResourceNotFoundException("Department" , "id" , id));
    }

    public void deleteDepartmentById(Long id){
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()){
            departmentRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Department", "id", id);
        }
    }
}