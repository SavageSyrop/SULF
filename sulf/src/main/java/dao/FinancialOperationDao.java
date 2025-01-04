package dao;

import entity.FinancialOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialOperationDao extends JpaRepository<FinancialOperation, Long> {
}
