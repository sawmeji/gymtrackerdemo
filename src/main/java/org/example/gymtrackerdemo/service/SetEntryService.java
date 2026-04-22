package org.example.gymtrackerdemo.service;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.AddSetRequest;
import org.example.gymtrackerdemo.dto.SetResponse;
import org.example.gymtrackerdemo.entity.SetEntry;
import org.example.gymtrackerdemo.entity.User;
import org.example.gymtrackerdemo.entity.WorkoutEntry;
import org.example.gymtrackerdemo.exception.BadRequestException;
import org.example.gymtrackerdemo.exception.ForbiddenException;
import org.example.gymtrackerdemo.exception.NotFoundException;
import org.example.gymtrackerdemo.repository.SetEntryRepository;
import org.example.gymtrackerdemo.repository.WorkoutEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetEntryService {

    private final SetEntryRepository setRepository;
    private final WorkoutEntryRepository entryRepository;

    // ✅ Add set
    public SetResponse addSet(AddSetRequest request, User user) {

        // 1. Find entry
        WorkoutEntry entry = entryRepository.findById(request.getWorkoutEntryId())
                .orElseThrow(() -> new NotFoundException("Workout entry not found"));

        if (request.getReps() == null && request.getDuration() == null) {
            throw new BadRequestException("Either reps or duration must be provided");
        }

        // 🔐 Security check (VERY IMPORTANT)
        if (!entry.getSession().getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("Not your workout");
        }

        // 2. Calculate set number automatically
        int nextSetNumber = setRepository
                .findByWorkoutEntryId(entry.getId())
                .size() + 1;

        // 3. Create set
        SetEntry set = SetEntry.builder()
                .workoutEntry(entry)
                .setNumber(nextSetNumber)
                .reps(request.getReps())
                .weight(request.getWeight())
                .duration(request.getDuration())
                .build();

        setRepository.save(set);

        // 4. Response
        return SetResponse.builder()
                .setNumber(set.getSetNumber())
                .reps(set.getReps())
                .weight(set.getWeight())
                .duration(set.getDuration())
                .build();
    }

    // ✅ Get sets of an exercise
    public List<SetResponse> getSets(Long workoutEntryId, User user) {

        WorkoutEntry entry = entryRepository.findById(workoutEntryId)
                .orElseThrow(() -> new NotFoundException("Workout entry not found"));

        // 🔐 Security check
        if (!entry.getSession().getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("Not your workout");
        }

        return setRepository.findByWorkoutEntryId(workoutEntryId)
                .stream()
                .map(set -> SetResponse.builder()
                        .setNumber(set.getSetNumber())
                        .reps(set.getReps())
                        .weight(set.getWeight())
                        .duration(set.getDuration())
                        .build())
                .toList();
    }
}
