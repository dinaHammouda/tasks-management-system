package banquemisr.challenge05.tasks.management.demo.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    
    private LocalDateTime timestamp;
    private String message;
}
