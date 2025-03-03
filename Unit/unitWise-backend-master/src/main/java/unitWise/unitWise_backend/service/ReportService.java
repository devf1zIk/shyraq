package unitWise.unitWise_backend.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unitWise.unitWise_backend.dto.report.ReportRequestDto;
import unitWise.unitWise_backend.dto.report.ReportResponseDto;
import unitWise.unitWise_backend.entity.Report;
import unitWise.unitWise_backend.repository.ReportRepository;

@Service
public class ReportService {
    
    @Autowired
    private ReportRepository reportRepository;


    public ReportResponseDto create(ReportRequestDto reportRequestDto) {
        Report report = Report.builder()
                        .accrualDate(reportRequestDto.getAccrualDate())
                .transactionDate(reportRequestDto.getTransactionDate())
                .businessUnitId(reportRequestDto.getBusinessUnitId())
                .contractId(reportRequestDto.getContractId())
                .bankAccountId(reportRequestDto.getBankAccountId())
                .debitAmount(reportRequestDto.getDebitAmount())
                .creditAmount(reportRequestDto.getCreditAmount())
                .typePaymentId(reportRequestDto.getTypePaymentId())
                .comment(reportRequestDto.getComment())
                .build();

        Report save = reportRepository.save(report);
        return map(save);
    }

    private ReportResponseDto map(Report report) {
        return new ReportResponseDto(
                report.getId(),
                report.getAccrualDate(),
                report.getTransactionDate(),
                report.getBusinessUnitId(),
                report.getContractId(),
                report.getBankAccountId(),
                report.getDebitAmount(),
                report.getCreditAmount(),
                report.getTypePaymentId(),
                report.getComment()
        );
    }
}
