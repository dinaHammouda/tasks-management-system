package banquemisr.challenge05.tasks.management.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import banquemisr.challenge05.tasks.management.demo.models.Role;

@Repository
public interface RoleRepository extends JpaRepository <Role, Integer>{

    Optional<Role> findById(int id);

}
