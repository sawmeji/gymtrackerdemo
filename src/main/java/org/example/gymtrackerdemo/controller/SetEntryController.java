package org.example.gymtrackerdemo.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.AddSetRequest;
import org.example.gymtrackerdemo.dto.SetResponse;
import org.example.gymtrackerdemo.security.CustomUserDetails;
import org.example.gymtrackerdemo.service.SetEntryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sets")
@RequiredArgsConstructor
public class SetEntryController {
    private final SetEntryService setService;

    // ✅ Add set
    @PostMapping("/addSet")
    public SetResponse addSet(@RequestBody AddSetRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return setService.addSet(request, userDetails.getUser());
    }

    // ✅ Get sets
    @GetMapping("/entry/{entryId}")
    public List<SetResponse> getSets(@PathVariable Long entryId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return setService.getSets(entryId, userDetails.getUser());
    }
}
