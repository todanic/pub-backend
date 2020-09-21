package pub.pubbackend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pub.pubbackend.model.Photo;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
