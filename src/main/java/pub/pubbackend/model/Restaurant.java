package pub.pubbackend.model;
import javax.persistence.*;

@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "address")
    private String address;
    
    @Column(name = "restaurantName")
    private String restaurantName;

    @Column(name = "userId")
    private String userId;

    @Column(name = "description")
    private String description;

    @Column(name = "phone")
    private String phone;

    public Restaurant(String userId, String restaurantName) {
        this.userId = userId;
        this.restaurantName = restaurantName;
    }
    public Restaurant() {

    }

    public Restaurant(String address, String userId, String description, String restaurantName, String phone) {
        this.address = address;
        this.userId = userId;
        this.restaurantName = restaurantName;
        this.phone = phone;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Tutorial [id=" + id + ", address=" + address + ", userId=" + userId + ", phone=" + phone + "]";
    }
}
