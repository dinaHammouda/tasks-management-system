package banquemisr.challenge05.tasks.management.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import banquemisr.challenge05.tasks.management.demo.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String user);

}
