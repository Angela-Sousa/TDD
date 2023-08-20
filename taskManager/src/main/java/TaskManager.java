import java.time.LocalDate;

public class TaskManager {
    private final TaskRepository taskRepository;

    public TaskManager(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String title, String description, LocalDate dueDate, Priority priority) {
        Task task = new Task(title, description, dueDate, priority);
        return taskRepository.save(task);
    }
}