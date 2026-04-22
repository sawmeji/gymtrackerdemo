package org.example.gymtrackerdemo.config;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.entity.ActiveStatus;
import org.example.gymtrackerdemo.entity.Exercise;
import org.example.gymtrackerdemo.repository.ExerciseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ExerciseRepository exerciseRepository;

    @Override
    public void run(String... args) {

        // CHEST
        add("Bench Press", "Chest");
        add("Incline Bench Press", "Chest");
        add("Decline Bench Press", "Chest");
        add("Chest Press Machine", "Chest");
        add("Cable Fly", "Chest");
        add("Push Up", "Chest");

        // BACK
        add("Pull Up", "Back");
        add("Lat Pulldown", "Back");
        add("Seated Cable Row", "Back");
        add("Barbell Row", "Back");
        add("Deadlift", "Back");

        // SHOULDERS
        add("Shoulder Press", "Shoulder");
        add("Arnold Press", "Shoulder");
        add("Lateral Raise", "Shoulder");
        add("Front Raise", "Shoulder");
        add("Rear Delt Fly", "Shoulder");

        // ARMS
        add("Barbell Curl", "Arms");
        add("Dumbbell Curl", "Arms");
        add("Hammer Curl", "Arms");
        add("Tricep Pushdown", "Arms");
        add("Skull Crusher", "Arms");
        add("Dips", "Arms");

        // LEGS
        add("Squat", "Legs");
        add("Leg Press", "Legs");
        add("Lunges", "Legs");
        add("Leg Extension", "Legs");
        add("Leg Curl", "Legs");
        add("Calf Raise", "Legs");

        // CORE
        add("Crunch", "Core");
        add("Plank", "Core");
        add("Leg Raise", "Core");
        add("Russian Twist", "Core");

        // CARDIO
        add("Treadmill", "Cardio");
        add("Cycling", "Cardio");
        add("Jump Rope", "Cardio");
        add("Rowing Machine", "Cardio");
    }

    private void add(String name, String category) {
        if (!exerciseRepository.existsByNameIgnoreCase(name)) {
            exerciseRepository.save(
                    Exercise.builder()
                            .name(name)
                            .category(category)
                            .createdBy(null) // SYSTEM
                            .status(ActiveStatus.ACTIVE)
                            .createdAt(LocalDateTime.now())
                            .build()
            );
        }
    }
}

