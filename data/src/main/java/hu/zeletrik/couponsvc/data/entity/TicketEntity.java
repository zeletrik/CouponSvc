package hu.zeletrik.couponsvc.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "redeemed")
@NoArgsConstructor
@EqualsAndHashCode
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "ticket", nullable = false)
    private String number;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "territory", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "zip", nullable = false)
    private String zip;

    @Column(name = "winner", nullable = false)
    private boolean winner;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

}
