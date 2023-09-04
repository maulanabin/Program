package com.doyatama.university.service;

import com.doyatama.university.exception.BadRequestException;
import com.doyatama.university.exception.ResourceNotFoundException;
import com.doyatama.university.model.SubjectGroup;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.subjectGroup.SubjectGroupRequest;
import com.doyatama.university.payload.subjectGroup.SubjectGroupResponse;
import com.doyatama.university.repository.SubjectGroupRepository;
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
public class SubjectGroupService {
    @Autowired
    private SubjectGroupRepository subjectGroupRepository;


    private static final Logger logger = LoggerFactory.getLogger(SubjectGroupService.class);

    public PagedResponse<SubjectGroupResponse> getAllSubjectGroup(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<SubjectGroup> subjectGroups = subjectGroupRepository.findAll(pageable);

        if(subjectGroups.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), subjectGroups.getNumber(),
                    subjectGroups.getSize(), subjectGroups.getTotalElements(), subjectGroups.getTotalPages(), subjectGroups.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<SubjectGroupResponse> subjectGroupResponses = subjectGroups.map(thResponse -> {
            SubjectGroupResponse subjectGroupResponse = new SubjectGroupResponse();
            subjectGroupResponse.setId(thResponse.getId());
            subjectGroupResponse.setName(thResponse.getName());
            subjectGroupResponse.setDescription(thResponse.getDescription());
            subjectGroupResponse.setCreatedAt(thResponse.getCreatedAt());
            subjectGroupResponse.setUpdatedAt(thResponse.getUpdatedAt());
            return subjectGroupResponse;
        }).getContent();

        return new PagedResponse<>(subjectGroupResponses, subjectGroups.getNumber(),
                subjectGroups.getSize(), subjectGroups.getTotalElements(), subjectGroups.getTotalPages(), subjectGroups.isLast(), 200);
    }

    public SubjectGroup createSubjectGroup(UserPrincipal currentUser, SubjectGroupRequest subjectGroupRequest) {
        SubjectGroup subjectGroup = new SubjectGroup();
        subjectGroup.setName(subjectGroupRequest.getName());
        subjectGroup.setDescription(subjectGroupRequest.getDescription());
        subjectGroup.setCreatedBy(currentUser.getId());
        subjectGroup.setUpdatedBy(currentUser.getId());
        return subjectGroupRepository.save(subjectGroup);
    }

    public SubjectGroupResponse getSubjectGroupById(Long subjectGroupId) {
        SubjectGroup subjectGroup = subjectGroupRepository.findById(subjectGroupId).orElseThrow(
                () -> new ResourceNotFoundException("SubjectGroup", "id", subjectGroupId));

        SubjectGroupResponse subjectGroupResponse = new SubjectGroupResponse();
        subjectGroupResponse.setId(subjectGroup.getId());
        subjectGroupResponse.setName(subjectGroup.getName());
        subjectGroupResponse.setDescription(subjectGroup.getDescription());
        subjectGroupResponse.setCreatedAt(subjectGroup.getCreatedAt());
        subjectGroupResponse.setUpdatedAt(subjectGroup.getUpdatedAt());
        return subjectGroupResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public SubjectGroup updateSubjectGroup(SubjectGroupRequest subjectGroupReq, Long id, UserPrincipal currentUser){
        return subjectGroupRepository.findById(id).map(subjectGroup -> {
            subjectGroup.setName(subjectGroupReq.getName());
            subjectGroup.setDescription(subjectGroupReq.getDescription());
            subjectGroup.setUpdatedBy(currentUser.getId());
            subjectGroup.setUpdatedAt(Instant.now());
            return subjectGroupRepository.save(subjectGroup);
        }).orElseThrow(() -> new ResourceNotFoundException("SubjectGroup" , "id" , id));
    }

    public void deleteSubjectGroupById(Long id){
        Optional<SubjectGroup> subjectGroup = subjectGroupRepository.findById(id);
        if(subjectGroup.isPresent()){
            subjectGroupRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("SubjectGroup", "id", id);
        }
    }
}
