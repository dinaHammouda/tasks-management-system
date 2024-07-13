package banquemisr.challenge05.tasks.management.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import banquemisr.challenge05.tasks.management.demo.models.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status,Integer> {

}
