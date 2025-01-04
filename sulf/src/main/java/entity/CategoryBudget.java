package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "category_budgets")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryBudget extends AbstractEntity {
    @Column
    private Float budgetSize;
    @Column
    private String categoryName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;
}
