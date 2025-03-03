package unitWise.unitWise_backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "table_node")
public class TableNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "total_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "total_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "total_difference"))
    })
    private MonthData total;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "jan_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "jan_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "jan_difference"))
    })
    private MonthData jan;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "feb_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "feb_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "feb_difference"))
    })
    private MonthData feb;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "mar_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "mar_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "mar_difference"))
    })
    private MonthData mar;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "apr_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "apr_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "apr_difference"))
    })
    private MonthData apr;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "may_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "may_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "may_difference"))
    })
    private MonthData may;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "jun_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "jun_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "jun_difference"))
    })
    private MonthData jun;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "jul_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "jul_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "jul_difference"))
    })
    private MonthData jul;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "aug_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "aug_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "aug_difference"))
    })
    private MonthData aug;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "sep_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "sep_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "sep_difference"))
    })
    private MonthData sep;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "oct_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "oct_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "oct_difference"))
    })
    private MonthData oct;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "nov_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "nov_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "nov_difference"))
    })
    private MonthData nov;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "plan", column = @Column(name = "dec_plan")),
            @AttributeOverride(name = "fact", column = @Column(name = "dec_fact")),
            @AttributeOverride(name = "difference", column = @Column(name = "dec_difference"))
    })
    private MonthData dec;
    private boolean expanded;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id")
    private List<TableNode> children;
}
