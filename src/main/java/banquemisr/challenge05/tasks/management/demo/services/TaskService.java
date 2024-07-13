package banquemisr.challenge05.tasks.management.demo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import banquemisr.challenge05.tasks.management.demo.dtos.RequestBodyDTO;
import banquemisr.challenge05.tasks.management.demo.dtos.TaskDTO;
import banquemisr.challenge05.tasks.management.demo.exceptions.UserNotFoundException;
import banquemisr.challenge05.tasks.management.demo.mapper.RequestBodyMapper;
import banquemisr.challenge05.tasks.management.demo.mapper.TaskMapper;
import banquemisr.challenge05.tasks.management.demo.models.Priority;
import banquemisr.challenge05.tasks.management.demo.models.Task;
import banquemisr.challenge05.tasks.management.demo.models.User;
import banquemisr.challenge05.tasks.management.demo.repositories.PriorityRepository;
import banquemisr.challenge05.tasks.management.demo.repositories.StatusRepository;
import banquemisr.challenge05.tasks.management.demo.repositories.TasksRepository;
import banquemisr.challenge05.tasks.management.demo.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TaskService {

    private final TasksRepository taskRepository;
    private final TaskMapper taskMapper;
    private final RequestBodyMapper requestMapper;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final PriorityRepository priorityRepository;

    @Autowired
    private EmailService emailService;

    private final String DUEDATE_NOTIFICATION = "Tasks due date";
    private final String DUEDATE_NOTIFICATION_BODY = "you task %s is due date";

    @Autowired
    public TaskService(TasksRepository taskRepository, TaskMapper taskMapper, RequestBodyMapper requestMapper,
            UserRepository userRepository, StatusRepository statusRepository, PriorityRepository priorityRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.requestMapper = requestMapper;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.priorityRepository = priorityRepository;

    }

    @Transactional
    public TaskDTO createTask(RequestBodyDTO taskDTO) {

        Task task = requestMapper.requestBodyDTOToTask(taskDTO);

        User user = userRepository.findByEmail(taskDTO.getUser())
                .orElseThrow(() -> new UserNotFoundException("User with email " + taskDTO.getUser() + " not found"));

        task.setUserId(user);

        Task savedTask = taskRepository.save(task);
        return taskMapper.taskToTaskDTO(savedTask);
    }

    public Page<TaskDTO> searchTasksByTitle(String title, int page, int size) {

        PageRequest pageable = PageRequest.of(page, size);
        Page<Task> taskPage = taskRepository.findAll(pageable);
        return taskPage.map(taskMapper::taskToTaskDTO);
    }

    public Page<TaskDTO> searchTasksByTitleAndDescriptionAndPriority(String title, String description,
            Priority priority, User user, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Task> taskPage = taskRepository.findTasksByOptionalCriteria(title, description, priority, user, pageable);
        return taskPage.map(taskMapper::taskToTaskDTO);
    }

    public Page<TaskDTO> getAllTasks(String title, String description, Priority priority, User user, int page,
            int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Task> taskPage = taskRepository.findAllTasksByOptionalCriteria(title, description, priority, user,
                pageable);
        return taskPage.map(taskMapper::taskToTaskDTO);
    }

    public void deleteTask(int taskId, User user) {
        Task taskToDelete = taskRepository.findByIdAndUserId(taskId, user)
                .orElseThrow(() -> new BadCredentialsException("user not authorized to delete task"));
        taskRepository.deleteById(taskToDelete.getId());
    }

    public TaskDTO updateTask(Integer taskId, RequestBodyDTO updatedTask, User user) {

        Task existingTask = taskRepository.findByIdAndUserId(taskId, user)
                .orElseThrow(() -> new BadCredentialsException("Task not found with id: " + taskId));
        if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getStatus() != 0) {
            existingTask.setStatus(statusRepository.findById(updatedTask.getStatus())
                    .orElseThrow(() -> new EntityNotFoundException("entityNotFound")));

        }
        if (updatedTask.getPriority() != 0) {
            existingTask.setPriority(priorityRepository.findById(updatedTask.getPriority())
                    .orElseThrow(() -> new EntityNotFoundException("entityNotFound"))); // Assuming priorityService can
                                                                                        // fetch Priority by ID
        }

        if (updatedTask.getDueDate() != null) {
            existingTask.setDueDate(updatedTask.getDueDate());
        }
   
        return  taskMapper.taskToTaskDTO(taskRepository.save(existingTask));
    }

    public void processTasksDueForTodayAndSendEmails() {
        LocalDate today = LocalDate.now();
       
        
        Optional<List<Task>> tasksDueToday = taskRepository.findByDueDate(today);

        tasksDueToday.ifPresent(tasks -> {
            for (Task task : tasks) {
                String userEmail = task.getUserId().getEmail();
                String taskTitle = task.getTitle();
                String body = String.format(DUEDATE_NOTIFICATION_BODY, taskTitle);
                emailService.sendEmail(userEmail,DUEDATE_NOTIFICATION, body);
            }
        });
    }
}
