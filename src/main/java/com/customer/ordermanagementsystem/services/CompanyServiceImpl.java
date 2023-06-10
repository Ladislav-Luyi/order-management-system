package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.domain.company.Company;
import com.customer.ordermanagementsystem.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final OpeningHoursService openingHoursService;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, OpeningHoursServiceImpl openingHoursService) {
        this.companyRepository = companyRepository;
        this.openingHoursService = openingHoursService;
    }

    @Override
    public void openAndCloseStore(String status) {
        Optional<Company> company = companyRepository.findById("1");
        boolean s = stringStatusToBoolean(status);
        company.ifPresent(c -> c.setStatus(s));
        company.ifPresent(companyRepository::save);
    }

    private boolean stringStatusToBoolean(String status) {
        return !status.matches("^[zZ].*");
    }

    @Override
    public void openAndCloseStoreWithMessage(String status, String message) {
        Optional<Company> company = companyRepository.findById("1");
        boolean s = stringStatusToBoolean(status);
        company.ifPresent(c -> c.setStatus(s));
        company.ifPresent(c -> c.setStatusMessage(s ? "" : message));
        company.ifPresent(companyRepository::save);
    }

    @Override
    public boolean isStoreOpen() {
        return isStoreOpenAccordingManualAction() && openingHoursService.isStoreOpen();
    }

    private Company getDefaultCompany(){
        log.info("Using default company");
        Company company = new Company();
        company.setStatus(true);
        company.setName("");
        company.setStatusMessage("Z technických príčin zatvorené");
        return company;
    }

    private boolean isStoreOpenAccordingManualAction() {
        Optional<Company> company = companyRepository.findById("1");
        return company.orElse(getDefaultCompany()).isStatus();
    }


    @Override
    public void addItemToModel(Model model, String nameOfAttributeForMapping) {
        if (!isStoreOpenAccordingManualAction()) {
            log.debug("Reason for closed is dedicated rest api call; not opening hours; fetching message about close reason and adding it to model");
            model.addAttribute( nameOfAttributeForMapping,
                                companyRepository.findById("1").orElse(getDefaultCompany())
                                    .getStatusMessage());
        }
    }

    @Override
    public String getOpenAndCloseStoreMessage() {
        return companyRepository.findById("1").orElse(getDefaultCompany())
                                .toString();
    }
}
