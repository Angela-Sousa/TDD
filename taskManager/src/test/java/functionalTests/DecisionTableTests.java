package functionalTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import taskManager.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DecisionTableTests {

    private TaskManager taskManager;

    @BeforeEach
    public void setUp() {
        TaskRepository taskRepository = new InMemoryTaskRepository();
        taskManager = new TaskManager(taskRepository);
    }

    @Test
    public void testCreateTask() {
        Task task1 = taskManager.createTask("Task", "Description", LocalDate.now(), Priority.HIGH);

        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask("", "Description", LocalDate.now(), Priority.HIGH));
        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask("Task", "", LocalDate.now(), Priority.HIGH));
        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask("Task", "Description", null, Priority.HIGH));
        assertThrows(IllegalArgumentException.class, () -> taskManager.createTask("Task", "Description", LocalDate.now(), null));

    }

    @Test
    public void testUpdateTask() {
        Task task = taskManager.createTask("Task", "Description", LocalDate.now(), Priority.HIGH);

        TaskDTO taskDTO = new TaskDTO(task);

        taskDTO.setTitle("");
        TaskDTO finalTaskDTO = taskDTO;
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTask(finalTaskDTO));

        taskDTO = new TaskDTO(task.getId());
        taskDTO.setDescription("");
        TaskDTO finalTaskDTO2 = taskDTO;
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTask(finalTaskDTO2));

        taskDTO = new TaskDTO(task.getId());
        taskDTO.setDueDate(null);
        TaskDTO finalTaskDTO3 = taskDTO;
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTask(finalTaskDTO3));

        taskDTO = new TaskDTO(task.getId());
        taskDTO.setDueDate(null);
        TaskDTO finalTaskDTO4 = taskDTO;
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTask(finalTaskDTO4));

        taskDTO = new TaskDTO(15L);
        taskDTO.setDueDate(null);
        TaskDTO finalTaskDTO5 = taskDTO;
        assertThrows(IllegalArgumentException.class, () -> taskManager.updateTask(finalTaskDTO5));

    }
}
