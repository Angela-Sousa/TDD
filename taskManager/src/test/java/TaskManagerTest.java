import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    private TaskRepository taskRepository;
    private TaskManager taskManager;

    @BeforeEach
    public void setUp() {
        taskRepository = new InMemoryTaskRepository();
        taskManager = new TaskManager(taskRepository);
    }

    @Test
    public void testCreateTask() {

        Task task = taskManager.createTask("Task 1", "Description 1", LocalDate.now(), Priority.HIGH);
        assertNotNull(task);
        assertTrue(task.getId() > 0);

        // Verify that the task is in the repository
        Task retrievedTask = taskRepository.findById(task.getId());
        assertNotNull(retrievedTask);
        assertEquals("Task 1", retrievedTask.getTitle());
        assertEquals("Description 1", retrievedTask.getDescription());
        assertEquals(LocalDate.now(), retrievedTask.getDueDate());
        assertEquals(Priority.HIGH, retrievedTask.getPriority());
    }
}