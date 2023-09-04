package com.doyatama.university.service;

import com.doyatama.university.exception.BadRequestException;
import com.doyatama.university.exception.ResourceNotFoundException;
import com.doyatama.university.model.Religion;
import com.doyatama.university.model.Religion;
import com.doyatama.university.model.Subject;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.religion.ReligionRequest;
import com.doyatama.university.payload.religion.ReligionResponse;
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
public class ReligionService {
    @Autowired
    private ReligionRepository religionRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReligionService.class);

    public PagedResponse<ReligionResponse> getAllReligion(int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Religion> religions = religionRepository.findAll(pageable);

        if(religions.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), religions.getNumber(),
                    religions.getSize(), religions.getTotalElements(), religions.getTotalPages(), religions.isLast(), 200);
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        List<ReligionResponse> religionResponses = religions.map(asResponse -> {
            ReligionResponse religionResponse = new ReligionResponse();
            religionResponse.setId(asResponse.getId());
            religionResponse.setName(asResponse.getName());
            religionResponse.setDescription(asResponse.getDescription());
            religionResponse.setCreatedAt(asResponse.getCreatedAt());
            religionResponse.setUpdatedAt(asResponse.getUpdatedAt());
            return religionResponse;
        }).getContent();

        return new PagedResponse<>(religionResponses, religions.getNumber(),
                religions.getSize(), religions.getTotalElements(), religions.getTotalPages(), religions.isLast(), 200);
    }

    public Religion createReligion(UserPrincipal currentUser, ReligionRequest religionRequest) {
        Religion religion = new Religion();
        religion.setName(religionRequest.getName());
        religion.setDescription(religionRequest.getDescription());
        religion.setCreatedBy(currentUser.getId());
        religion.setUpdatedBy(currentUser.getId());
        return religionRepository.save(religion);
    }

    public ReligionResponse getReligionById(Long religionId) {
        Religion religion = religionRepository.findById(religionId).orElseThrow(
                () -> new ResourceNotFoundException("Religion", "id", religionId));

        ReligionResponse religionResponse = new ReligionResponse();
        religionResponse.setId(religion.getId());
        religionResponse.setName(religion.getName());
        religionResponse.setDescription(religion.getDescription());
        religionResponse.setCreatedAt(religion.getCreatedAt());
        religionResponse.setUpdatedAt(religion.getUpdatedAt());
        return religionResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public Religion updateReligion(ReligionRequest religionReq, Long id, UserPrincipal currentUser){
        return religionRepository.findById(id).map(religion -> {
            religion.setName(religionReq.getName());
            religion.setDescription(religionReq.getDescription());
            religion.setUpdatedBy(currentUser.getId());
            return religionRepository.save(religion);
        }).orElseThrow(() -> new ResourceNotFoundException("Religion" , "id" , id));
    }

    public void deleteReligionById(Long id){
        Optional<Religion> religion = religionRepository.findById(id);
        if(religion.isPresent()){
            religionRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Religion", "id", id);
        }
    }
}