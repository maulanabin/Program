package com.doyatama.university.controller;

import com.doyatama.university.model.Religion;
import com.doyatama.university.payload.ApiResponse;
import com.doyatama.university.payload.PagedResponse;
import com.doyatama.university.payload.religion.ReligionRequest;
import com.doyatama.university.payload.religion.ReligionResponse;
import com.doyatama.university.repository.ReligionRepository;
import com.doyatama.university.repository.UserRepository;
import com.doyatama.university.security.CurrentUser;
import com.doyatama.university.security.UserPrincipal;
import com.doyatama.university.service.ReligionService;
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
@RequestMapping("/api/religion")
public class ReligionController {
    @Autowired
    private ReligionRepository religionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReligionService religionService;

    private static final Logger logger = LoggerFactory.getLogger(ReligionController.class);

    @GetMapping
    @Secured("ROLE_ADMINISTRATOR")
    public PagedResponse<ReligionResponse> getReligion(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return religionService.getAllReligion(page, size);
    }

    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> createReligion(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ReligionRequest religionRequest) {
        Religion religion = religionService.createReligion(currentUser, religionRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{religionId}")
                .buildAndExpand(religion.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Religion Created Successfully"));
    }

    @PutMapping("/{religionId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ResponseEntity<?> updateReligionById(@CurrentUser UserPrincipal currentUser, @PathVariable (value = "religionId") Long religionId, @Valid @RequestBody ReligionRequest religionRequest) {
        Religion religion = religionService.updateReligion(religionRequest, religionId, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{religionId}")
                .buildAndExpand(religion.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Religion Updated Successfully"));
    }

    @GetMapping("/{religionId}")
    @Secured("ROLE_ADMINISTRATOR")
    public ReligionResponse getReligionById(@PathVariable Long religionId) {
        return religionService.getReligionById(religionId);
    }

    @DeleteMapping("/{religionId}")
    @Secured("ROLE_ADMINISTRATOR")
    public HttpStatus deleteReligion(@PathVariable (value = "religionId") Long religionId){
        religionService.deleteReligionById(religionId);
        return HttpStatus.FORBIDDEN;
    }
}
