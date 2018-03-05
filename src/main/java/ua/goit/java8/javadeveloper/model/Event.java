package ua.goit.java8.javadeveloper.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Proxy;
import ua.goit.java8.javadeveloper.json.CustomEventDeserializer;
import ua.goit.java8.javadeveloper.json.CustomEventSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by t.oleksiv on 27/02/2018.
 */

@JsonSerialize(using = CustomEventSerializer.class)
@JsonDeserialize(using = CustomEventDeserializer.class)
@Entity
@Table(name = "events")
@Proxy(lazy = false)
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition = "BIGINT")
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "duration")
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "event_type", referencedColumnName = "id")
    private EventType eventType;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "event_users", joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
