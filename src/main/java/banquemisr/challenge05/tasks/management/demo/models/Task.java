package banquemisr.challenge05.tasks.management.demo.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "tasks")
@Data
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;

    @JoinColumn(name = "priority", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Priority priority;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User userId;

    @Column(name = "due_date")
    private LocalDate dueDate;

}
