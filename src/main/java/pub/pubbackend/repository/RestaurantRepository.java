package pub.pubbackend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pub.pubbackend.model.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
