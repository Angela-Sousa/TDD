package taskManager;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class TaskManager {
    private final TaskRepository taskRepository;

    public TaskManager(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String title, String description, LocalDate dueDate, Priority priority) {
        Task task = new Task(title, description, dueDate, priority);
        return taskRepository.save(task);
    }

    public Task updateTask(TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(taskDTO.getId());
        if (existingTask == null) {
            throw new IllegalArgumentException("Task not found");
        }

        String title = taskDTO.getTitle();
        String description = taskDTO.getDescription();
        LocalDate dueDate = taskDTO.getDueDate();
        Priority priority = taskDTO.getPriority();

        // Update task details
        if (title != null) {
            existingTask.setTitle(title);
        }
        if (description != null) {
            existingTask.setDescription(description);
        }
        if (dueDate != null) {
            existingTask.setDueDate(dueDate);
        }
        if (priority != null) {
            existingTask.setPriority(priority);
        }

        return taskRepository.save(existingTask);
    }

    public void deleteTask(long taskId) {
        Task task = taskRepository.findById(taskId);
        if (task != null) {
            taskRepository.delete(taskId);
        }
    }

    public List<Task> listTasks() {
        List<Task> tasks = taskRepository.findAll();

        // Sort tasks by due date and priority
        tasks.sort(Comparator.comparing(Task::getDueDate).thenComparing(Task::getPriority));

        return tasks;
    }
}