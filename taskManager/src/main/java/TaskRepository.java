public interface TaskRepository {
    Task save(Task task);

    Task findById(long taskId);
}
