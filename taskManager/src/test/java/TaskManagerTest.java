import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskManager.*;

import java.time.LocalDate;
import java.util.List;

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

        TaskDTO taskDTO = new TaskDTO(task);
        taskDTO.setTitle("Updated Title");

        Task updatedTask = taskManager.updateTask(taskDTO);

        // Verify that the title was updated
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals("Description 2", updatedTask.getDescription());
        assertEquals(LocalDate.now(), updatedTask.getDueDate());
        assertEquals(Priority.MEDIUM, updatedTask.getPriority());

        taskDTO = new TaskDTO(task);
        taskDTO.setDescription("Updated Description");
        updatedTask = taskManager.updateTask(taskDTO);

        // Verify that the description was updated
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals("Updated Description", updatedTask.getDescription());
        assertEquals(LocalDate.now(), updatedTask.getDueDate());
        assertEquals(Priority.MEDIUM, updatedTask.getPriority());

        taskDTO = new TaskDTO(task);
        taskDTO.setDueDate(LocalDate.now().plusDays(1));
        updatedTask = taskManager.updateTask(taskDTO);

        // Verify that the dueDate was updated
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals("Updated Description", updatedTask.getDescription());
        assertEquals(LocalDate.now().plusDays(1), updatedTask.getDueDate());
        assertEquals(Priority.MEDIUM, updatedTask.getPriority());

        taskDTO = new TaskDTO(task);
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

    @Test
    public void testListTasks() {
        // Create tasks with different priorities
        Task highPriorityTask = taskManager.createTask("High Priority Task", "High Priority Description",
                LocalDate.now(), Priority.HIGH);
        Task mediumPriorityTask = taskManager.createTask("Medium Priority Task", "Medium Priority Description",
                LocalDate.now().plusDays(1), Priority.MEDIUM);
        Task lowPriorityTask = taskManager.createTask("Low Priority Task", "Low Priority Description",
                LocalDate.now().plusDays(2), Priority.LOW);

        List<Task> tasks = taskManager.listTasks();

        // Verify that tasks are sorted by due date and priority
        assertEquals(highPriorityTask, tasks.get(0));
        assertEquals(mediumPriorityTask, tasks.get(1));
        assertEquals(lowPriorityTask, tasks.get(2));
    }
}