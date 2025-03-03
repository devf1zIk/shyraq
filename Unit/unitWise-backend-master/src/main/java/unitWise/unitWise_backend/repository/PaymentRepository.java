package unitWise.unitWise_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unitWise.unitWise_backend.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
