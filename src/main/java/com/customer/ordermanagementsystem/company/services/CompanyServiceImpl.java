package com.customer.ordermanagementsystem.company.services;

import com.customer.ordermanagementsystem.company.domain.Company;
import com.customer.ordermanagementsystem.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final OpeningHoursService openingHoursService;

    @Override
    public void openAndCloseStore(String status) {
        // there will be always only one company in current state
        Optional<Company> company = companyRepository.findByName(Company.defaultName);
        boolean s = stringStatusToBoolean(status);
        company.ifPresent(c -> c.setStatus(s));
        company.ifPresent(companyRepository::save);
    }

    private boolean stringStatusToBoolean(String status) {
        return !status.matches("^[zZ].*");
    }

    @Override
    public void openAndCloseStoreWithMessage(String status, String message) {
        Optional<Company> company = companyRepository.findByName(Company.defaultName);
        boolean s = stringStatusToBoolean(status);
        company.ifPresent(c -> c.setStatus(s));
        company.ifPresent(c -> c.setStatusMessage(s ? "" : message));
        company.ifPresent(companyRepository::save);
    }

    @Override
    public boolean isStoreOpen() {
        return isStoreOpenAccordingManualAction() && openingHoursService.isStoreOpen();
    }

    private Company getDefaultCompany() {
        log.info("Using default company");
        Company company = new Company();
        company.setStatus(true);
        company.setStatusMessage("Z technických príčin zatvorené");
        return company;
    }

    private boolean isStoreOpenAccordingManualAction() {
        Optional<Company> company = companyRepository.findByName(Company.defaultName);
        return company.orElse(getDefaultCompany()).isStatus();
    }

    @Override
    public String getOpenAndCloseStoreMessage() {
        return companyRepository.findByName(Company.defaultName).orElse(getDefaultCompany())
                .getStatusMessage();
    }
}
