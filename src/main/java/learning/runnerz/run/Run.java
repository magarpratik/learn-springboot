package learning.runnerz.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record Run(
        Integer id,
        @NotEmpty
        String title,
        LocalDateTime startedOn,
        LocalDateTime completedOn,
        @Positive
        Integer miles,
        Location location
) {
    public Run {
        if (completedOn.isBefore(startedOn)) {
            throw new IllegalArgumentException("Completed On must be after Started On");
        }
    }
}
