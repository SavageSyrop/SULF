package entity;

import enums.FinancialOperationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "financial_operations")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FinancialOperation extends AbstractEntity {
    @Column
    private String categoryName;
    @Column
    private Double price;
    @Column
    private FinancialOperationType operationType;
    dobavit user
}
