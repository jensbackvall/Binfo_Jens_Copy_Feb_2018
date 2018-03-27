package dk.binfo.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "apartment_neighbours")
public class ApartmentNeighbours implements Serializable{

    @Id
    @Column(name = "id")
    @JoinColumn(name = "id")
    private int id;

    @Id
    @Column(name = "neighbour_id")
    @JoinColumn(name = "id")
    private int neighbour_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNeighbour_id() {
        return neighbour_id;
    }

    public void setNeighbour_id(int neighbour_id) {
        this.neighbour_id = neighbour_id;
    }
}
