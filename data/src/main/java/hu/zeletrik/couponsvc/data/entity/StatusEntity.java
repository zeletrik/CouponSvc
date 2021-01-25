package hu.zeletrik.couponsvc.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "actual_status")
@NoArgsConstructor
@EqualsAndHashCode
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "redeemed_today", nullable = false)
    private Integer redeemedToday;

    @Column(name = "win_overall", nullable = false)
    private Integer winOverall;

}
