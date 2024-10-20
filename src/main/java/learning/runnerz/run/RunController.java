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
    List<Run> getAll() {
        return runRepository.getAll();
    }

    @GetMapping("/{id}")
    Run getById(@PathVariable Integer id) {
        return runRepository.getById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody Run run) {
        runRepository.create(run);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody Run run, @PathVariable Integer id) {
        runRepository.update(run, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Integer id) {
        runRepository.delete(id);
    }
}
