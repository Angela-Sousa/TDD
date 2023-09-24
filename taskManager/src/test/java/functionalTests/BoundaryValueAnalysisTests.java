package functionalTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskManager.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoundaryValueAnalysisTests {

    private TaskRepository taskRepository;
    private TaskManager taskManager;

    @BeforeEach
    public void setUp() {
        taskRepository = new InMemoryTaskRepository();
        taskManager = new TaskManager(taskRepository);
    }

    @Test
    public void testCreateTask() {
        Task task1 = taskManager.createTask("Task", "Description", LocalDate.now(), Priority.LOW);
        Task task2 = taskManager.createTask("Task", "Description", LocalDate.now(), Priority.MEDIUM);
        Task task3 = taskManager.createTask("Task", "Description", LocalDate.now(), Priority.HIGH);
    }

    @Test
    public void testUpdateTask() {
        Task task = taskManager.createTask("Task", "Description", LocalDate.now(), null);

        TaskDTO taskDTO = new TaskDTO(task.getId());
        taskDTO.setPriority(Priority.LOW);
        Task updatedTask = taskManager.updateTask(taskDTO);
        assertEquals(Priority.LOW, updatedTask.getPriority());

        taskDTO.setPriority(Priority.MEDIUM);
        updatedTask = taskManager.updateTask(taskDTO);
        assertEquals(Priority.MEDIUM, updatedTask.getPriority());

        taskDTO.setPriority(Priority.HIGH);
        updatedTask = taskManager.updateTask(taskDTO);
        assertEquals(Priority.HIGH, updatedTask.getPriority());

    }
}
