package banquemisr.challenge05.tasks.management.demo.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import banquemisr.challenge05.tasks.management.demo.models.Priority;
import banquemisr.challenge05.tasks.management.demo.models.Task;
import banquemisr.challenge05.tasks.management.demo.models.User;

@Repository
public interface TasksRepository extends JpaRepository<Task, Integer> {

    Page<Task> findByTitleContainingAndDescriptionContainingAndPriorityAndUserId(String title, String description,
            Priority priority, User user, Pageable pageable);

    @Query("SELECT t FROM Task t " +
            "WHERE (:title is null or t.title like %:title%) " +
            "AND (:description is null or t.description like %:description%) " +
            "AND (:priority is null or t.priority = :priority) " +
            "AND (t.userId = :userId)")
    Page<Task> findTasksByOptionalCriteria(
            @Param("title") String title,
            @Param("description") String description,
            @Param("priority") Priority priority,
            @Param("userId") User user, Pageable pageable);

    @Query("SELECT t FROM Task t " +
            "WHERE (:title is null or t.title like %:title%) " +
            "AND (:description is null or t.description like %:description%) " +
            "AND (:priority is null or t.priority = :priority) " +
            "AND (:userId is null or t.userId = :userId)")
    Page<Task> findAllTasksByOptionalCriteria(
            @Param("title") String title,
            @Param("description") String description,
            @Param("priority") Priority priority,
            @Param("userId") User user, Pageable pageable);

    void deleteById(Long taskId);

    Optional<Task> findByIdAndUserId(int taskId, User user);
    Optional<List<Task>> findByDueDate(LocalDate dueDate);
   

}
