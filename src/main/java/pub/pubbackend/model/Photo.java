package pub.pubbackend.model;
import javax.persistence.*;

@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "restaurantId")
    private String restaurantId;

    @Column(name = "path")
    private String path;

    @Column(name = "filename")
    private String filename;

    public Photo() {

    }

    public Photo(String restaurantId, String path, String filename) {
        this.restaurantId = restaurantId;
        this.path = path;
        this.filename = filename;
    }

    public Photo(String setRestaurantId, String setPath) {
    }

    public long getId() { return id; }

    public String getRestaurantId() { return restaurantId; }

    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }
}
