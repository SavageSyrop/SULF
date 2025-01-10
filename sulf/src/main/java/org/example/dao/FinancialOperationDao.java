package org.example.dao;

import org.example.entity.FinancialOperation;
import org.example.entity.User;
import org.example.enums.FinancialOperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialOperationDao extends JpaRepository<FinancialOperation, Long> {

    List<FinancialOperation> findAllByOwnerAndOperationType(User owner, FinancialOperationType operationType);

    List<FinancialOperation> findAllByOwner(User owner);

    List<FinancialOperation> findAllByOwnerAndCategoryName(User owner , String categoryName);
}
