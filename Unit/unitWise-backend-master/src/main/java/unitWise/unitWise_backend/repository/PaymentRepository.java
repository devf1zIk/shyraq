package unitWise.unitWise_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unitWise.unitWise_backend.entity.Payment;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByProjectId(Long id);

    @Query("SELECT p FROM Payment p WHERE EXTRACT(YEAR FROM p.dateTransfer) = :year AND p.projectId = :projectId")
    List<Payment> findPaymentsByYearAndProjectId(@Param("year") int year, @Param("projectId") Long projectId);

    @Query("SELECT p FROM Payment p WHERE EXTRACT(YEAR FROM p.dateTransfer) = :year AND p.projectId = :projectId AND p.versionId = :versionId")
    List<Payment> findPaymentsByYearAndProjectIdAndVersionId(@Param("year") int year, @Param("projectId") Long projectId, @Param("versionId") Long versionId);

}
