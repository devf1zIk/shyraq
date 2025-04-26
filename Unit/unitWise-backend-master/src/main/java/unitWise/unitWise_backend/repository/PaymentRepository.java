package unitWise.unitWise_backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unitWise.unitWise_backend.entity.Payment;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByProjectId(Long id);

    @Query("SELECT p FROM Payment p WHERE p.projectId = :projectId AND p.versionId = :versionId")
    List<Payment> findPaymentsByProjectIdAndVersionId(@Param("projectId") Long projectId, @Param("versionId") Long versionId);

    @Query("SELECT p FROM Payment p WHERE " +
            "p.projectId = :projectId AND " +
            "EXTRACT(YEAR FROM p.dateTransfer) = :year AND " +
            "(:unitIds IS NULL OR p.companyId IN :unitIds) AND " +
            "(:versionId IS NULL OR p.versionId = :versionId)")
    List<Payment> findPaymentsByProjectIdAndYearAndCompanyIdsAndVersionId(@Param("projectId") Long projectId,
                                                                          @Param("year") int year,
                                                                          @Param("unitIds") List<Long> unitIds,
                                                                          @Param("versionId") int versionId);

    @Query("SELECT p FROM Payment p WHERE " +
            "p.projectId = :projectId AND " +
            "EXTRACT(YEAR FROM p.dateTransfer) = :year AND " +
            "(:unitIds IS NULL OR p.companyId IN :unitIds)")
    List<Payment> findPaymentsByProjectIdAndYearAndCompanyIds(@Param("projectId") Long projectId,
                                                                          @Param("year") int year,
                                                                          @Param("unitIds") List<Long> unitIds);
    @Query("SELECT p FROM Payment p WHERE " +
            "p.projectId = :projectId AND " +
            "EXTRACT(YEAR FROM p.dateTransfer) = :year AND " +
            "(:versionId IS NULL OR p.versionId = :versionId)")
    List<Payment> findPaymentsByProjectIdAndYearAndVersionId(@Param("projectId") Long projectId,
                                                             @Param("year") int year,
                                                             @Param("versionId") int versionId);

    @Query("SELECT p FROM Payment p WHERE " +
            "p.projectId = :projectId AND " +
            "p.accountId = :accountId AND " +
            "p.companyId = :companyId AND " +
            "p.articleId = :articleId AND " +
            "p.versionId <> :versionId")
    List<Payment> findPaymentsByIds(
            @Param("projectId") Long projectId,
            @Param("accountId") Long accountId,
            @Param("companyId") Long companyId,
            @Param("articleId") Long articleId,
            @Param("versionId") Long versionId
    );

//    @Query("SELECT p FROM Payment p WHERE " +
//            "p.projectId = :projectId AND " +
//            "p.accountId = :accountId AND " +
//            "p.companyId = :companyId AND " +
//            "p.articleId = :articleId AND " +
//            "p.versionId <> :versionId AND " +
//            "FUNCTION('EXTRACT', 'MONTH', p.dateAccrual) = FUNCTION('EXTRACT', 'MONTH', :dateAccrual)")
//    List<Payment> findPaymentIdsBySameMonth(
//            @Param("projectId") Long projectId,
//            @Param("accountId") Long accountId,
//            @Param("companyId") Long companyId,
//            @Param("articleId") Long articleId,
//            @Param("versionId") Long versionId,
//            @Param("dateAccrual") LocalDate dateAccrual
//    );




    @Query("SELECT p FROM Payment p WHERE " +
            "p.projectId = :projectId AND " +
            "EXTRACT(YEAR FROM p.dateTransfer) = :year AND " +
            "(:unitIds IS NULL OR p.companyId IN :unitIds) AND " +
            "(:accountId IS NULL OR p.accountId = :accountId)")
    List<Payment> findPaymentsByProjectAndYear(
            @Param("projectId") Long projectId,
            @Param("year") int year,
            @Param("unitIds") List<Long> unitIds,
            @Param("accountId") Long accountId
    );

    @Query("SELECT p FROM Payment p WHERE " +
            "p.projectId = :projectId AND " +
            "p.versionId = :versionId AND " +
            "(:dateAccrualStart IS NULL OR p.dateAccrual >= :dateAccrualStart) AND " +
            "(:dateAccrualEnd IS NULL OR p.dateAccrual <= :dateAccrualEnd) AND " +
            "(:dateTransferStart IS NULL OR p.dateTransfer >= :dateTransferStart) AND " +
            "(:dateTransferEnd IS NULL OR p.dateTransfer <= :dateTransferEnd) AND " +
            "(:companyIds IS NULL OR p.companyId IN :companyIds) AND " +
            "(:articleId IS NULL OR p.articleId = :articleId) AND " +
            "(:contractId IS NULL OR p.contractId = :contractId) AND " +
            "(:accountId IS NULL OR p.accountId = :accountId) AND " +
            "(:amountFrom IS NULL OR p.amountDt >= :amountFrom OR p.amountKt >= :amountFrom) AND " +
            "(:amountTo IS NULL OR p.amountDt <= :amountTo OR p.amountKt <= :amountTo)")
    Page<Payment> findPaymentsByFilters(
            @Param("projectId") Long projectId,
            @Param("versionId") Long versionId,
            @Param("dateAccrualStart") LocalDate dateAccrualStart,
            @Param("dateAccrualEnd") LocalDate dateAccrualEnd,
            @Param("dateTransferStart") LocalDate dateTransferStart,
            @Param("dateTransferEnd") LocalDate dateTransferEnd,
            @Param("companyIds") List<Long> companyIds,
            @Param("articleId") Long articleId,
            @Param("contractId") Long contractId,
            @Param("accountId") Long accountId,
            @Param("amountFrom") Double amountFrom,
            @Param("amountTo") Double amountTo,
            Pageable pageable
    );

}
