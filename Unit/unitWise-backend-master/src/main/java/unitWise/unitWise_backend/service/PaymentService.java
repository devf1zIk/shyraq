package unitWise.unitWise_backend.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unitWise.unitWise_backend.dto.cashFlow.PaymentRequestDto;
import unitWise.unitWise_backend.dto.cashFlow.PaymentResponseDto;
import unitWise.unitWise_backend.entity.Payment;
import unitWise.unitWise_backend.repository.PaymentRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private static final String UPLOAD_DIR = "uploads/";
    private final PaymentRepository paymentRepository;
    private final ContractService contractService;

    public PaymentService(PaymentRepository paymentRepository,
                          ContractService contractService) {
        this.paymentRepository = paymentRepository;
        this.contractService = contractService;
    }

    public void processFile(MultipartFile file, Long projectId, Long userId) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Файл пуст!");
        }

        Path uploadDir = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path savePath = uploadDir.resolve(uniqueFileName);

        Files.copy(file.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);

        List<Payment> payments;
        if (file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            payments = parsePdf(savePath.toString(), projectId, userId);
        } else if (file.getOriginalFilename().toLowerCase().endsWith(".xlsx") || file.getOriginalFilename().toLowerCase().endsWith(".xls")) {
            payments = parseExcel(savePath.toString(), projectId, userId);
        } else {
            throw new IOException("Неподдерживаемый формат файла: " + file.getOriginalFilename());
        }
        paymentRepository.saveAll(payments);
    }

    public static List<Payment> parsePdf(String filePath, Long projectId, Long userId) throws IOException {
        List<Payment> payments = new ArrayList<>();
        File fileToParse = new File(filePath);

        if (!fileToParse.exists()) {
            throw new IOException("Файл не найден: " + filePath);
        }

        try (PDDocument document = PDDocument.load(fileToParse)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            String[] lines = text.split("\n");

            for (String line : lines) {
                if (line.matches("\\d{2}\\.\\d{2}\\.\\d{4}.*")) {
                    String[] parts = line.split("\\s+", 4);
                    Payment payment = new Payment();
                    payment.setDateAccrual(java.sql.Date.valueOf(LocalDate.parse(parts[0], java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
                    payment.setDateTransfer(java.sql.Date.valueOf(LocalDate.parse(parts[0], java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
                    payment.setCompanyId(Long.parseLong(parts[1]));
                    payment.setAccountId(Long.parseLong(parts[1]));
                    payment.setAmountDt(Double.parseDouble(parts[2].replace(",", ".")));
                    payment.setAmountKt(Double.parseDouble(parts[2].replace(",", ".")));
                    payment.setArticleId(Long.parseLong(parts[3]));
                    payment.setComment(parts[3]);
                    payment.setVersionId(Long.parseLong(parts[4]));
                    payment.setUserId(userId);
                    payment.setProjectId(projectId);
                    payments.add(payment);
                }
            }
        }
        return payments;
    }

    public static List<Payment> parseExcel(String filePath, Long projectId, Long userId) throws IOException {
        List<Payment> payments = new ArrayList<>();
        File fileToParse = new File(filePath);

        if (!fileToParse.exists()) {
            throw new IOException("Файл не найден: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(fileToParse);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Пропускаем заголовок
                Payment payment = new Payment();
                payment.setDateAccrual(java.sql.Date.valueOf(getCellDateValue(row.getCell(0))));
                payment.setDateTransfer(java.sql.Date.valueOf(getCellDateValue(row.getCell(1))));
                payment.setCompanyId(getCellLongValue(row.getCell(2)));
                payment.setAccountId(getCellLongValue(row.getCell(3)));
                payment.setContractId(getCellLongValue(row.getCell(4)));
                payment.setAmountDt(getCellDoubleValue(row.getCell(5)));
                payment.setAmountKt(getCellDoubleValue(row.getCell(6)));
                payment.setArticleId(getCellLongValue(row.getCell(7)));
                payment.setVersionId(getCellLongValue(row.getCell(9)));
                payment.setUserId(userId);
                payment.setProjectId(projectId);
                payment.setComment(getCellStringValue(row.getCell(8)));
                payments.add(payment);
            }
        }
        return payments;
    }

    private static String getCellStringValue(Cell cell) {
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    private static Double getCellDoubleValue(Cell cell) {
        if (cell == null) return 0.0;
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue().replace(",", "."));
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }

    private static Long getCellLongValue(Cell cell) {
        if (cell == null) return null;
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (long) cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                return Long.parseLong(cell.getStringCellValue().trim());
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return null;
    }

    private static LocalDate getCellDateValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        } else {
            try {
                return LocalDate.parse(cell.getStringCellValue().trim());
            } catch (Exception e) {
                return null;
            }
        }
    }

    public Payment createCashFlow(PaymentRequestDto paymentRequestDto) {
        Long articleId = contractService.getContractById(paymentRequestDto.getContractId()).getCf_item_id();

        Payment payment = new Payment();
        payment.setDateAccrual(paymentRequestDto.getDateAccrual());
        payment.setDateTransfer(paymentRequestDto.getDateTransfer());
        payment.setCompanyId(paymentRequestDto.getCompanyId());
        payment.setAccountId(paymentRequestDto.getAccountId());
        payment.setContractId(paymentRequestDto.getContractId());
        payment.setAmountDt(paymentRequestDto.getAmountDt());
        payment.setAmountKt(paymentRequestDto.getAmountKt());
        payment.setArticleId(articleId);
        payment.setVersionId(paymentRequestDto.getVersionId());
        payment.setUserId(paymentRequestDto.getUserId());
        payment.setProjectId(paymentRequestDto.getProjectId());
        payment.setComment(paymentRequestDto.getComment());
        return paymentRepository.save(payment);
    }


    public PaymentResponseDto getPaymentsByProject(Long projectId) {
        List<Payment> payments = paymentRepository.findByProjectId(projectId);

        // Calculate total amounts
        double totalAmountDt = payments.stream().mapToDouble(Payment::getAmountDt).sum();
        double totalAmountKt = payments.stream().mapToDouble(Payment::getAmountKt).sum();
        double totalSum = totalAmountDt - totalAmountKt;

        // Wrap in DTO
        PaymentResponseDto.FactCfRecords records = new PaymentResponseDto.FactCfRecords(payments, 1);
        PaymentResponseDto.FactCfData data = new PaymentResponseDto.FactCfData(records, totalAmountDt, totalAmountKt, totalSum);

        return new PaymentResponseDto(data);
    }

    public List<Payment> getPaymentsByProjectIdAndYear(Long projectId, int year) {
        List<Payment> payments = paymentRepository.findPaymentsByYearAndProjectId(year, projectId);
        return payments;
    }

    public List<Payment> getPaymentsByYearAndProjectIdAndVersionId(Long projectId, int year, Long versionId) {
        List<Payment> payments = paymentRepository
                .findPaymentsByYearAndProjectIdAndVersionId(year, projectId, versionId);
        return payments;
    }

    public void deletePayments(List<Long> paymentIds) {
        for (Long id : paymentIds) {
            paymentRepository.deleteById(id);
        }
    }
}
