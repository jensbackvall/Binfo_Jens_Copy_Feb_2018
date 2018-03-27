package dk.binfo.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_preferences")
public class UserPreferences implements Serializable {

    @Id
    @Column(name = "user_id")
    @JoinColumn(name = "user_id")
    private int userId;

    @Id
    @Column(name = "apartment_id")
    private int apartmentId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }
}
