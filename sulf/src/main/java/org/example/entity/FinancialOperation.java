package org.example.entity;

import org.example.enums.FinancialOperationType;
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
    @Column(name = "operation_type", columnDefinition = "enum('INCOME','EXPENSE')")
    @Enumerated(EnumType.STRING)
    private FinancialOperationType operationType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    public void setOperationType(String operationType) {
        this.operationType = FinancialOperationType.valueOf(operationType);
    }

    public void setOperationType(FinancialOperationType operationType) {
        this.operationType = operationType;
    }
}
