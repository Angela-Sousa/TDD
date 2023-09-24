package functionalTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskManager.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EquivalencePartitioningTests {

    private TaskRepository taskRepository;
    private TaskManager taskManager;

    @BeforeEach
    public void setUp() {
        taskRepository = new InMemoryTaskRepository();
        taskManager = new TaskManager(taskRepository);
    }

    @Test
    public void testCreateTask() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask("Task", "Description", LocalDate.now(), null));
        Task task1 = taskManager.createTask("Task", "Description", LocalDate.now(), Priority.LOW);
        Task task2 = taskManager.createTask("Task", "Description", LocalDate.now(), Priority.MEDIUM);
        Task task3 = taskManager.createTask("Task", "Description", LocalDate.now(), Priority.HIGH);
    }

    @Test
    public void testUpdateTask() {
        Task task = taskManager.createTask("Task", "Description", LocalDate.now(), Priority.LOW);

        TaskDTO taskDTO = new TaskDTO(task.getId());

        taskDTO.setPriority(null);
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTask(taskDTO));

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
