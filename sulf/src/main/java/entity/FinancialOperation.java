package entity;

import enums.FinancialOperationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
    private Float price;
    @Column
    private FinancialOperationType operationType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;
}
