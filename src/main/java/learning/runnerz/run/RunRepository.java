package learning.runnerz.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {

    private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> getAll() {
        return jdbcClient.sql("SELECT * FROM Run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> getById(Integer id) {
        return jdbcClient.sql("SELECT * FROM Run WHERE id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        var updatedRows = jdbcClient
                .sql("INSERT INTO RUN " +
                        "(id, title, started_on, completed_on, miles, location) " +
                        "values (?, ?, ?, ?, ?, ?)")
                .params(List.of(
                        run.id(),
                        run.title(),
                        run.startedOn(),
                        run.completedOn(),
                        run.miles(),
                        run.location().toString()))
                .update();

        Assert.state(updatedRows == 1, "Failed to create run " + run.title());
    }

    public void update(Run run, Integer id) {
        var updatedRows = jdbcClient
                .sql("UPDATE RUN SET " +
                        "title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? " +
                        "WHERE id = ?")
                .params(List.of(
                        run.title(),
                        run.startedOn(),
                        run.completedOn(),
                        run.miles(),
                        run.location().toString(),
                        id))
                .update();

        Assert.state(updatedRows == 1, "Failed to update run " + run.title());
    }

    public void delete(Integer id) {
        var updatedRows = jdbcClient
                .sql("DELETE FROM Run WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(updatedRows == 1, "Failed to delete run " + id);
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM Run").query().listOfRows().size();
    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }

    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("SELECT * FROM Run WHERE location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }
}
