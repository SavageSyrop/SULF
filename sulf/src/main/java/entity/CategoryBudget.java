package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "category_budgets")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryBudget extends AbstractEntity {
    @Column
    private Double budgetSize;
    @Column
    private String categoryName;
    dobavit user
}
