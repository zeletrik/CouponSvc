package hu.zeletrik.couponsvc.data.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "territory")
@NoArgsConstructor
@EqualsAndHashCode
public class TerritoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "win_after", nullable = false)
    private Integer winAfter;

    @Column(name = "max_per_day", nullable = false)
    private Integer maxWinPerDay;

    @Column(name = "overall_max", nullable = false)
    private Integer maxWinOverall;

}
