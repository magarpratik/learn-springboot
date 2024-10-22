package learning.runnerz.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunRepository runRepository;

    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @GetMapping()
    List<Run> findAll() {
        return runRepository.findAll();
    }

    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id) {
        var run = runRepository.findById(id);

        if (run.isEmpty()) {
            throw new RunNotFoundException(id);
        }

        return run.get();
    }

    @GetMapping("/locations/{location}")
    List<Run> findByLocation(@PathVariable String location) {
        return runRepository.findAllByLocation(location);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody Run run) {
        runRepository.save(run);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody Run run, @PathVariable Integer id) {
        runRepository.save(run);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Integer id) {
        runRepository.deleteById(id);
    }
}
