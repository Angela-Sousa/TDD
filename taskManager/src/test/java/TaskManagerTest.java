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

    @Test
    public void testUpdateTask() {
        Task task = taskManager.createTask("Task 2", "Description 2", LocalDate.now(), Priority.MEDIUM);

        TaskDTO taskDTO = new TaskDTO(task.getId());
        taskDTO.setTitle("Updated Title");

        Task updatedTask = taskManager.updateTask(taskDTO);

        // Verify that the title was updated
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals("Description 2", updatedTask.getDescription());
        assertEquals(LocalDate.now(), updatedTask.getDueDate());
        assertEquals(Priority.MEDIUM, updatedTask.getPriority());

        taskDTO = new TaskDTO(task.getId());
        taskDTO.setDescription("Updated Description");
        updatedTask = taskManager.updateTask(taskDTO);

        // Verify that the description was updated
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals("Updated Description", updatedTask.getDescription());
        assertEquals(LocalDate.now(), updatedTask.getDueDate());
        assertEquals(Priority.MEDIUM, updatedTask.getPriority());

        taskDTO = new TaskDTO(task.getId());
        taskDTO.setDueDate(LocalDate.now().plusDays(1));
        updatedTask = taskManager.updateTask(taskDTO);

        // Verify that the dueDate was updated
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals("Updated Description", updatedTask.getDescription());
        assertEquals(LocalDate.now().plusDays(1), updatedTask.getDueDate());
        assertEquals(Priority.MEDIUM, updatedTask.getPriority());

        taskDTO = new TaskDTO(task.getId());
        taskDTO.setPriority(Priority.LOW);
        updatedTask = taskManager.updateTask(taskDTO);

        // Verify that the priority was updated
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals("Updated Description", updatedTask.getDescription());
        assertEquals(LocalDate.now().plusDays(1), updatedTask.getDueDate());
        assertEquals(Priority.LOW, updatedTask.getPriority());

        taskDTO = new TaskDTO(task.getId(), "Updated Title2", "Updated Description2", LocalDate.now().plusDays(5), Priority.HIGH);
        updatedTask = taskManager.updateTask(taskDTO);

        // Verify that all attributes were updated
        assertEquals("Updated Title2", updatedTask.getTitle());
        assertEquals("Updated Description2", updatedTask.getDescription());
        assertEquals(LocalDate.now().plusDays(5), updatedTask.getDueDate());
        assertEquals(Priority.HIGH, updatedTask.getPriority());
    }

    @Test
    public void testDeleteTask() {
        Task task = taskManager.createTask("Task 3", "Description 3", LocalDate.now(), Priority.MEDIUM);
        taskManager.deleteTask(task.getId());

        // Verify that the task was deleted
        Task retrievedTask = taskRepository.findById(task.getId());
        assertNull(retrievedTask);
    }
}