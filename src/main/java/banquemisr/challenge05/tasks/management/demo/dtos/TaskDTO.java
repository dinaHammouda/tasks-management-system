package banquemisr.challenge05.tasks.management.demo.dtos;



import java.time.LocalDate;

import lombok.Data;

@Data
public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private StatusDTO status;
    private PriorityDTO priority;
    private LocalDate dueDate;
    private UserDTO user;
}
