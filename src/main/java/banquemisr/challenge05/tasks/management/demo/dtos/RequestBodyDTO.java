package banquemisr.challenge05.tasks.management.demo.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RequestBodyDTO {
 private String title;
    private String description;
    private int status;
    private int priority;
    private LocalDate dueDate;
    private String user;
}
