package unitWise.unitWise_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import unitWise.unitWise_backend.entity.*;
import unitWise.unitWise_backend.repository.*;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final ProjectRepository projectRepository;
    private final ArticleRepository articleRepository;
    private final ContractorRepository contractorRepository;
    private final ContractRepository contractRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BusinessUnitRepository businessUnitRepository;

    public ApplicationStartup(ProjectRepository projectRepository,
                              ArticleRepository articleRepository, ContractorRepository contractorRepository, ContractRepository contractRepository, BankAccountRepository bankAccountRepository, BusinessUnitRepository businessUnitRepository) {
        this.projectRepository = projectRepository;
        this.articleRepository = articleRepository;
        this.contractorRepository = contractorRepository;
        this.contractRepository = contractRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.businessUnitRepository = businessUnitRepository;
    }

    @Override
    public void run(String... args) {
        if (projectRepository.count() == 0) { // Check if projects exist
            Project projectA = new Project(null, "Default Project A", "This is the default project A", 1,  null);
            Project projectB = new Project(null, "Default Project B", "This is the default project B", 1,  null);
            projectRepository.saveAll(List.of(projectA, projectB));
        }
        List<Project> projects = projectRepository.findAll();


        businessUnitRepository.save(BusinessUnit.builder().name("Apple Inc.").branch("Cupertino").projectId(projects.get(1).getId()).build());
        businessUnitRepository.save(BusinessUnit.builder().name("Google LLC").branch("Mountain View").projectId(projects.get(1).getId()).build());
        businessUnitRepository.save(BusinessUnit.builder().name("Microsoft Corporation").branch("Redmond").projectId(projects.get(1).getId()).build());
        businessUnitRepository.save(BusinessUnit.builder().name("Amazon.com, Inc.").branch("Seattle").projectId(projects.get(1).getId()).build());
        businessUnitRepository.save(BusinessUnit.builder().name("Facebook, Inc.").branch("Menlo Park").projectId(projects.get(1).getId()).build());
        businessUnitRepository.save(BusinessUnit.builder().name("Tesla, Inc.").branch("Palo Alto").projectId(projects.get(1).getId()).build());
        businessUnitRepository.save(BusinessUnit.builder().name("Netflix, Inc.").branch("Los Gatos").projectId(projects.get(1).getId()).build());
        businessUnitRepository.save(BusinessUnit.builder().name("Intel Corporation").branch("Santa Clara").projectId(projects.get(1).getId()).build());
        businessUnitRepository.save(BusinessUnit.builder().name("IBM Corporation").branch("Armonk").projectId(projects.get(1).getId()).build());
        businessUnitRepository.save(BusinessUnit.builder().name("Oracle Corporation").branch("Redwood City").projectId(projects.get(1).getId()).build());

        articleRepository.save(Article.builder().category("Внутренние переводы").groupName("Group1").item("Доходы").projectId(projects.get(0).getId()).build());
        articleRepository.save(Article.builder().category("Инвестиционные поступления").groupName("Group2").item("Доходы").projectId(projects.get(0).getId()).build());
        articleRepository.save(Article.builder().category("Внутренние переводы").groupName("Group3").item("Доходы").projectId(projects.get(1).getId()).build());
        articleRepository.save(Article.builder().category("Операционные поступления").groupName("Group4").item("Расходы").projectId(projects.get(0).getId()).build());
        articleRepository.save(Article.builder().category("Финансовые выбытия").groupName("Group5").item("Расходы").projectId(projects.get(1).getId()).build());
        articleRepository.save(Article.builder().category("Инвестиционные поступления").groupName("Group6").item("Поступления кредитов").projectId(projects.get(0).getId()).build());
        articleRepository.save(Article.builder().category("Операционные поступления").groupName("Group7").item("Расходы").projectId(projects.get(0).getId()).build());
        articleRepository.save(Article.builder().category("Операционные поступления").groupName("Group8").item("Инвестиции").projectId(projects.get(0).getId()).build());
        articleRepository.save(Article.builder().category("Инвестиционные поступления").groupName("Group9").item("Поступления кредитов").projectId(projects.get(1).getId()).build());
        articleRepository.save(Article.builder().category("Инвестиционные поступления").groupName("Group10").item("Поступления кредитов").projectId(projects.get(1).getId()).build());

        contractorRepository.save(new Contractor(null, "ABC Construction Ltd.", "ABC Ltd.", projects.get(1).getId()));
        contractorRepository.save(new Contractor(null, "XYZ Engineering Co.", "XYZ Co.", projects.get(1).getId()));
        contractorRepository.save(new Contractor(null, "LMN Builders", "LMN Build", projects.get(1).getId()));
        contractorRepository.save(new Contractor(null, "OPQ Constructions", "OPQ Con", projects.get(1).getId()));
        contractorRepository.save(new Contractor(null, "RST Developers", "RST Dev", projects.get(1).getId()));
        contractorRepository.save(new Contractor(null, "UVW Architects", "UVW Arch", projects.get(1).getId()));
        contractorRepository.save(new Contractor(null, "GHI Contractors", "GHI Cont", projects.get(1).getId()));
        contractorRepository.save(new Contractor(null, "JKL Infrastructure", "JKL Infra", projects.get(1).getId()));
        contractorRepository.save(new Contractor(null, "MNO Builders", "MNO Build", projects.get(1).getId()));
        contractorRepository.save(new Contractor(null, "PQR Engineering", "PQR Eng", projects.get(1).getId()));




        contractRepository.save(Contract.builder().name("ABC Construction Ltd.").shortName("ABC Ltd.").contractorId(1L).cfItemId(1L).combination("combination").projectId(projects.get(1).getId()).build());
        contractRepository.save(Contract.builder().name("X Engineering Co.").shortName("X Co.").contractorId(3L).cfItemId(1L).combination("combination").projectId(projects.get(1).getId()).build());
        contractRepository.save(Contract.builder().name("LMN Builders").shortName("LMN Build").contractorId(2L).cfItemId(2L).combination("combination").projectId(projects.get(1).getId()).build());
        contractRepository.save(Contract.builder().name("OPQ Constructions").shortName("OPQ Con").contractorId(4L).cfItemId(3L).combination("combination").projectId(projects.get(1).getId()).build());
        contractRepository.save(Contract.builder().name("RST Developers").shortName("RST Dev").contractorId(5L).cfItemId(4L).combination("combination").projectId(projects.get(1).getId()).build());
        contractRepository.save(Contract.builder().name("UVW Architects").shortName("UVW Arch").contractorId(6L).cfItemId(5L).combination("combination").projectId(projects.get(1).getId()).build());
        contractRepository.save(Contract.builder().name("GHI Contractors").shortName("GHI Cont").contractorId(7L).cfItemId(6L).combination("combination").projectId(projects.get(1).getId()).build());
        contractRepository.save(Contract.builder().name("JKL Infrastructure").shortName("JKL Infra").contractorId(8L).cfItemId(7L).combination("combination").projectId(projects.get(1).getId()).build());
        contractRepository.save(Contract.builder().name("MNO Builders").shortName("MNO Build").contractorId(9L).cfItemId(8L).combination("combination").projectId(projects.get(1).getId()).build());
        contractRepository.save(Contract.builder().name("PQR Engineering").shortName("PQR Eng").contractorId(9L).cfItemId(9L).combination("combination").projectId(projects.get(1).getId()).build());


        bankAccountRepository.save(BankAccount.builder().account("123456789").name("Bank of America").type("USD").currencyId(322516).projectId(projects.get(1).getId()).build());
        bankAccountRepository.save(BankAccount.builder().account("987654321").name("Chase Bank").type("EUR").currencyId(322551).projectId(projects.get(1).getId()).build());
        bankAccountRepository.save(BankAccount.builder().account("112233445").name("Wells Fargo").type("USD").currencyId(322516).projectId(projects.get(1).getId()).build());
        bankAccountRepository.save(BankAccount.builder().account("556677889").name("Citibank").type("GBP").currencyId(322541).projectId(projects.get(1).getId()).build());
        bankAccountRepository.save(BankAccount.builder().account("998877665").name("HSBC").type("AUD").currencyId(322537).projectId(projects.get(1).getId()).build());
        bankAccountRepository.save(BankAccount.builder().account("443322110").name("Deutsche Bank").type("EUR").currencyId(322551).projectId(projects.get(1).getId()).build());
        bankAccountRepository.save(BankAccount.builder().account("667788990").name("Barclays").type("GBP").currencyId(322541).projectId(projects.get(1).getId()).build());
        bankAccountRepository.save(BankAccount.builder().account("223344556").name("UBS").type("CHF").currencyId(322532).projectId(projects.get(1).getId()).build());
        bankAccountRepository.save(BankAccount.builder().account("334455667").name("Credit Suisse").type("CHF").currencyId(322532).projectId(projects.get(1).getId()).build());
        bankAccountRepository.save(BankAccount.builder().account("445566778").name("BNP Paribas").type("EUR").currencyId(322551).projectId(projects.get(1).getId()).build());
    }
}