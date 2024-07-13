package banquemisr.challenge05.tasks.management.demo.dtos;



import lombok.Data;

@Data
public class ResponseDTO {

    private String status;
    private String message;
    private TaskDTO task;

}
