package banquemisr.challenge05.tasks.management.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import banquemisr.challenge05.tasks.management.demo.dtos.RequestBodyDTO;
import banquemisr.challenge05.tasks.management.demo.dtos.TaskDTO;
import banquemisr.challenge05.tasks.management.demo.exceptions.UserNotFoundException;
import banquemisr.challenge05.tasks.management.demo.models.Priority;
import banquemisr.challenge05.tasks.management.demo.models.User;
import banquemisr.challenge05.tasks.management.demo.repositories.UserRepository;
import banquemisr.challenge05.tasks.management.demo.services.JwtService;
import banquemisr.challenge05.tasks.management.demo.services.TaskService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtService jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@NonNull HttpServletRequest request,@RequestBody RequestBodyDTO taskDTO) {
        User user = this.getUser(request);
        taskDTO.setUser(user.getEmail());
        TaskDTO createdTask = taskService.createTask(taskDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/dashboard")
    public Page<TaskDTO> getAllTasks(@RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer priorityId,
            @RequestParam(required = false) String userEmail,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Priority priority = null;
        if (priorityId != null) {
            priority = new Priority();
            priority.setId(priorityId);
        }

        User user = null;

        if(null!=userEmail&& userEmail.length()>0){
         user=   userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new UserNotFoundException("user not found"));
        }

        return taskService.getAllTasks(title, description, priority, user, page,
                size);
    }

    @GetMapping("/search")
    public Page<TaskDTO> searchTasksByTitle(@NonNull HttpServletRequest request,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer priorityId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Priority priority = null;
        if (priorityId != null) {
            priority = new Priority();
            priority.setId(priorityId);
        }

        User user = this.getUser(request);

        return taskService.searchTasksByTitleAndDescriptionAndPriority(title, description, priority, user, page, size);

    }

    private User getUser(HttpServletRequest request) {

        final String tokenHeader = request.getHeader("Authorization");
        // Remove "Bearer " prefix
        String jwtToken = tokenHeader.substring(7);

        return userRepository.findByEmail(jwtTokenUtil.extractUsername(jwtToken))
                .orElseThrow(() -> new UserNotFoundException("user not found"));

    }
@PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(HttpServletRequest request,@PathVariable Integer taskId, @RequestBody RequestBodyDTO updatedTask) {
        User user = this.getUser(request);
        TaskDTO updated = taskService.updateTask(taskId, updatedTask,user);
        return ResponseEntity.ok(updated);
    }
     @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@NonNull HttpServletRequest request,@PathVariable Integer taskId) {


            User user = this.getUser(request);
            taskService.deleteTask(taskId,user);
          
            return ResponseEntity.ok().build();
        // } catch (Exception e) {
        //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found with ID: " + taskId);
        // }
    }
}
