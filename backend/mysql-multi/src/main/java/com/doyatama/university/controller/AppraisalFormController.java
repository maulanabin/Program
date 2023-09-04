package com.doyatama.university.controller;

import com.doyatama.university.model.AppraisalForm;
import com.doyatama.university.payload.ApiResponse;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.appraisalForm.AppraisalFormRequest;
import com.doyatama.university.payload.appraisalForm.AppraisalFormResponse;
import com.doyatama.university.repository.AppraisalFormRepository;
import com.doyatama.university.repository.UserRepository;
import com.doyatama.university.security.CurrentUser;
import com.doyatama.university.security.UserPrincipal;
import com.doyatama.university.service.AppraisalFormService;
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
@RequestMapping("/api/appraisal-form")
public class AppraisalFormController {
    @Autowired
    private AppraisalFormRepository appraisalFormRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppraisalFormService appraisalFormService;

    private static final Logger logger = LoggerFactory.getLogger(AppraisalFormController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<AppraisalFormResponse> getAppraisalForm(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return appraisalFormService.getAllAppraisalForm(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createAppraisalForm(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody AppraisalFormRequest appraisalFormRequest) {
        AppraisalForm appraisalForm = appraisalFormService.createAppraisalForm(currentUser, appraisalFormRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{appraisalFormId}")
                .buildAndExpand(appraisalForm.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "AppraisalForm Created Successfully"));
    }

    @PutMapping("/{appraisalFormId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateAppraisalFormById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "appraisalFormId") Long appraisalFormId, @Valid @RequestBody AppraisalFormRequest appraisalFormRequest) {
        AppraisalForm appraisalForm = appraisalFormService.updateAppraisalForm(appraisalFormRequest, appraisalFormId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{appraisalFormId}")
                .buildAndExpand(appraisalForm.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "AppraisalForm Updated Successfully"));
    }

    @GetMapping("/{appraisalFormId}")
    @Secured("ROLE_ADMINISTRATOR")
    public AppraisalFormResponse getAppraisalFormById(@PathVariable Long appraisalFormId) {
        return appraisalFormService.getAppraisalFormById(appraisalFormId);
    }

    @DeleteMapping("/{appraisalFormId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteAppraisalForm(@PathVariable (value = "appraisalFormId") Long appraisalFormId){
        appraisalFormService.deleteAppraisalFormById(appraisalFormId);
        return HttpStatus.FORBIDDEN;
    }
}
