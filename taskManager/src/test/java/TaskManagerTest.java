import org.junit.jupiter.api.BeforeEach;

public class TaskManagerTest {

    private TaskRepository taskRepository;
    private TaskManager taskManager;

    @BeforeEach
    public void setUp() {
        taskRepository = new InMemoryTaskRepository();
        taskManager = new TaskManager(taskRepository);
    }
}