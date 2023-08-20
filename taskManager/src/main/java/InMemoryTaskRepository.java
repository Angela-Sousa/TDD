import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    @Override
    public Task save(Task task) {
        task.setId(nextId++);
        tasks.add(task);
        return task;
    }

    @Override
    public Task findById(long taskId) {
        return tasks.stream()
                .filter(task -> task.getId() == taskId)
                .findFirst()
                .orElse(null);
    }
}