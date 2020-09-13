package pub.pubbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pub.pubbackend.model.User;

import java.util.List;

//JpaRepository gives support for creating instances, updating, deleting and finding them
public interface AuthRepository extends JpaRepository<User, Long> {
    List<User> findUserByEmailAndPassword(String email, String password);
}
