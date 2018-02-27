package ua.goit.java8.javadeveloper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by Taras on 23.02.2018.
 */

@Entity
@Table(name = "event_types")
@Proxy(lazy = false)
public class EventType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "salary_coef")
    private BigDecimal salary_coef;

    @JsonIgnore
    @OneToMany(mappedBy = "eventType", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Event> events;

    public EventType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary_coef() {
        return salary_coef;
    }

    public void setSalary_coef(BigDecimal salary_coef) {
        this.salary_coef = salary_coef;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
