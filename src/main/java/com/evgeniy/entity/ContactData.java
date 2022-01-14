package com.evgeniy.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Getter
@RequiredArgsConstructor
@Setter
@ToString
@Table(name = "contactdata")
public class ContactData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "typecontact")
    private String typeContact;
    @Column(name = "fio")
    private String fio;
    @Column(name = "phone_number")
    private String phone_number;
    @Column(name = "address")
    private String address;
    @Column(name = "bankdetails")
    private long bankDetails;
    @Column(name = "email")
    private String email;
    @Column(name = "description")
    private String description;
    @Column(name = "profession")
    private String profession;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ContactData that = (ContactData) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
