package dao;

import entity.FinancialOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialOperationDao extends JpaRepository<FinancialOperation, Long> {

    List<FinancialOperation> findAllByOwnerAndOperationType(Long id, String operationType);

    List<FinancialOperation> findAllByOwner(Long id);

    List<FinancialOperation> findAllByOwnerAndCategoryName(Long id, String selectedCategory);
}
