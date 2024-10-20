package learning.runnerz.run;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {

    private final List<Run> runs = new ArrayList<>();

    List<Run> getAll() {
        return runs;
    }

    Run getById(Integer id) {
        Optional<Run> existingRun = runs.stream()
                .filter(run -> run.id().equals(id))
                .findFirst();

        if (existingRun.isEmpty()) {
            throw new RunNotFoundException(id);
        }

        return existingRun.get();
    }

    void create(Run run) {
        runs.add(run);
    }

    void update(Run run, Integer id) {
        Run existingRun = getById(id);

        runs.set(runs.indexOf(existingRun), run);
    }

    void delete(Integer id) {
        Run existingRun = getById(id);

        runs.remove(existingRun);
    }

    @PostConstruct
    private void init() {
        runs.add(new Run(
                1,
                "First Run",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                3,
                Location.OUTDOOR));

        runs.add(new Run(
                2,
                "Second Run",
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2),
                3,
                Location.OUTDOOR));
    }
}
