package com.doyatama.university.service;

import com.doyatama.university.exception.BadRequestException;
import com.doyatama.university.exception.ResourceNotFoundException;
import com.doyatama.university.model.AppraisalForm;
import com.doyatama.university.model.AppraisalForm;
import com.doyatama.university.model.Subject;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.appraisalForm.AppraisalFormRequest;
import com.doyatama.university.payload.appraisalForm.AppraisalFormResponse;
import com.doyatama.university.repository.AppraisalFormRepository;
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
public class AppraisalFormService {
    @Autowired
    private AppraisalFormRepository appraisalFormRepository;

    private static final Logger logger = LoggerFactory.getLogger(AppraisalFormService.class);

    public PagedResponse<AppraisalFormResponse> getAllAppraisalForm(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<AppraisalForm> appraisalForms = appraisalFormRepository.findAll(pageable);

        if(appraisalForms.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), appraisalForms.getNumber(),
                    appraisalForms.getSize(), appraisalForms.getTotalElements(), appraisalForms.getTotalPages(), appraisalForms.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<AppraisalFormResponse> appraisalFormResponses = appraisalForms.map(asResponse -> {
            AppraisalFormResponse appraisalFormResponse = new AppraisalFormResponse();
            appraisalFormResponse.setId(asResponse.getId());
            appraisalFormResponse.setName(asResponse.getName());
            appraisalFormResponse.setCreatedAt(asResponse.getCreatedAt());
            appraisalFormResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return appraisalFormResponse;
        }).getContent();

        return new PagedResponse<>(appraisalFormResponses, appraisalForms.getNumber(),
                appraisalForms.getSize(), appraisalForms.getTotalElements(), appraisalForms.getTotalPages(), appraisalForms.isLast(), 200);
    }

    public AppraisalForm createAppraisalForm(UserPrincipal currentUser, AppraisalFormRequest appraisalFormRequest) {
        AppraisalForm appraisalForm = new AppraisalForm();
        appraisalForm.setName(appraisalFormRequest.getName());
        appraisalForm.setCreatedBy(currentUser.getId());
        appraisalForm.setUpdatedBy(currentUser.getId());
        return appraisalFormRepository.save(appraisalForm);
    }

    public AppraisalFormResponse getAppraisalFormById(Long appraisalFormId) {
        AppraisalForm appraisalForm = appraisalFormRepository.findById(appraisalFormId).orElseThrow(
                () -> new ResourceNotFoundException("AppraisalForm", "id", appraisalFormId));

        AppraisalFormResponse appraisalFormResponse = new AppraisalFormResponse();
        appraisalFormResponse.setId(appraisalForm.getId());
        appraisalFormResponse.setName(appraisalForm.getName());
        appraisalFormResponse.setCreatedAt(appraisalForm.getCreatedAt());
        appraisalFormResponse.setUpdatedAt(appraisalForm.getUpdatedAt());
        return appraisalFormResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public AppraisalForm updateAppraisalForm(AppraisalFormRequest appraisalFormReq, Long id, UserPrincipal currentUser){
        return appraisalFormRepository.findById(id).map(appraisalForm -> {
            appraisalForm.setName(appraisalFormReq.getName());
            appraisalForm.setUpdatedBy(currentUser.getId());
            return appraisalFormRepository.save(appraisalForm);
        }).orElseThrow(() -> new ResourceNotFoundException("AppraisalForm" , "id" , id));
    }

    public void deleteAppraisalFormById(Long id){
        Optional<AppraisalForm> appraisalForm = appraisalFormRepository.findById(id);
        if(appraisalForm.isPresent()){
            appraisalFormRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("AppraisalForm", "id", id);
        }
    }
}