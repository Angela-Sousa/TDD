package taskManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    @Override
    public Task save(Task task) {
        if (task.getId() == 0) {
            task.setId(nextId++);
        } else {
            // If the task already has an ID, it's an update
            update(task);
        }
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

    @Override
    public void delete(long taskId) {
        tasks.removeIf(task -> task.getId() == taskId);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    public Task update(Task updatedTask) {
        long taskId = updatedTask.getId();
        for (int i = 0; i < tasks.size(); i++) {
            Task existingTask = tasks.get(i);
            if (existingTask.getId() == taskId) {
                tasks.set(i, updatedTask);
                return updatedTask;
            }
        }
        throw new IllegalArgumentException("Task not found for update with ID: " + taskId);
    }
}