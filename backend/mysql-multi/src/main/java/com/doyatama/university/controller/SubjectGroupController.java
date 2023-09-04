package com.doyatama.university.controller;

import com.doyatama.university.model.SubjectGroup;
import com.doyatama.university.payload.ApiResponse;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.subjectGroup.SubjectGroupRequest;
import com.doyatama.university.payload.subjectGroup.SubjectGroupResponse;
import com.doyatama.university.repository.SubjectGroupRepository;
import com.doyatama.university.repository.UserRepository;
import com.doyatama.university.security.CurrentUser;
import com.doyatama.university.security.UserPrincipal;
import com.doyatama.university.service.SubjectGroupService;
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
@RequestMapping("/api/subject-group")
public class SubjectGroupController {
    @Autowired
    private SubjectGroupRepository subjectGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectGroupService subjectGroupService;

    private static final Logger logger = LoggerFactory.getLogger(SubjectGroupController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<SubjectGroupResponse> getSubjectGroup(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return subjectGroupService.getAllSubjectGroup(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createSubjectGroup(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody SubjectGroupRequest subjectGroupRequest) {
        SubjectGroup subjectGroup = subjectGroupService.createSubjectGroup(currentUser, subjectGroupRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{subjectGroupId}")
                .buildAndExpand(subjectGroup.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "SubjectGroup Created Successfully"));
    }

    @PutMapping("/{subjectGroupId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateSubjectGroupById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "subjectGroupId") Long subjectGroupId, @Valid @RequestBody SubjectGroupRequest subjectGroupRequest) {
        SubjectGroup subjectGroup = subjectGroupService.updateSubjectGroup(subjectGroupRequest, subjectGroupId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{subjectGroupId}")
                .buildAndExpand(subjectGroup.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "SubjectGroup Updated Successfully"));
    }

    @GetMapping("/{subjectGroupId}")
    @Secured("ROLE_ADMINISTRATOR")
    public SubjectGroupResponse getSubjectGroupById(@PathVariable Long subjectGroupId) {
        return subjectGroupService.getSubjectGroupById(subjectGroupId);
    }

    @DeleteMapping("/{subjectGroupId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteSubjectGroup(@PathVariable (value = "subjectGroupId") Long subjectGroupId){
        subjectGroupService.deleteSubjectGroupById(subjectGroupId);
        return HttpStatus.FORBIDDEN;
    }
}
