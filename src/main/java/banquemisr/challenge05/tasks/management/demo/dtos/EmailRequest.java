package banquemisr.challenge05.tasks.management.demo.dtos;

import lombok.Data;

@Data
public class EmailRequest {
private String to;
private String subject;
private String body;
}
