package dk.binfo.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "list_and_seniority")
public class ListAndSeniority implements Serializable {

    @Id
    @Column(name = "user_id")
    @JoinColumn(name = "user_id")
    private int userId;

    @Id
    @Column(name = "list_priority")
    private int listPriority;

    @Basic(optional = false)
    @Column(name = "seniority", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date seniority;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getListPriority() {
        return listPriority;
    }

    public void setListPriority(int listPriority) {
        this.listPriority = listPriority;
    }

    public Date getSeniority() {
        return seniority;
    }

    public void setSeniority(Date seniority) {
        this.seniority = seniority;
    }
}
