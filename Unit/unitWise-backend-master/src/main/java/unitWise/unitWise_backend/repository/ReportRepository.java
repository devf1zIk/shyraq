package unitWise.unitWise_backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unitWise.unitWise_backend.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

}
