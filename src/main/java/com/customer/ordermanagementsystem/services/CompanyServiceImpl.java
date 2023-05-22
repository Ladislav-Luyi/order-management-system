package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.pojos.company.Company;
import com.customer.ordermanagementsystem.pojos.company.OpenningHours;
import com.customer.ordermanagementsystem.repository.CompanyRepository;
import com.customer.ordermanagementsystem.repository.OpenningHoursRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;
    private final OpenningHoursRepository openningHoursRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, OpenningHoursRepository openningHoursRepository) {
        this.companyRepository = companyRepository;
        this.openningHoursRepository = openningHoursRepository;
    }


    @Override
    public void openAndCloseStore(String status) {
        Optional<Company> company = companyRepository.findById("1");
        boolean s = stringStatusToBoolean(status);
        company.ifPresent(c -> c.setStatus(s));
        company.ifPresent(c -> companyRepository.save(c));
    }

    private boolean stringStatusToBoolean(String status) {
        return !status.matches("^[zZ].*");
    }

    @Override
    public void openAndCloseStoreWithMessage(String status, String message) {
        Optional<Company> company = companyRepository.findById("1");
        boolean s = stringStatusToBoolean(status);
        company.ifPresent(c -> c.setStatus(s));
        company.ifPresent(c -> c.setStatusMessage(  s ? "" : message ));
        company.ifPresent(c -> companyRepository.save(c));
    }

    @Override
    public boolean isStoreOpen() {
        return isStoreOpenAccordingManualAction() && isStoreOpenAccordingTimeSchedule();
    }
    
    private boolean isStoreOpenAccordingManualAction() {
        Optional<Company> company = companyRepository.findById("1");
        return company.get().isStatus();
    }


    @Override
    public void addItemToModel(Model model, String nameOfAttributeForMapping) {
        if ( ! isStoreOpenAccordingManualAction() ) {
            log.debug("Reason for closed is dedicated rest api call; not opening hours; fetching message about close reason and adding it to model");
            model.addAttribute(nameOfAttributeForMapping, companyRepository.findById("1").get().getStatusMessage());
        }
    }

    @Override
    public boolean isStoreOpenAccordingTimeSchedule() {
        Iterable<OpenningHours> rules = fetchRules();
        return isStoreOpenAccordingRules(rules);
    }

    @Override
    public String getOpenAndCloseStoreMessage() {
        return companyRepository.findById("1").get().toString();
    }

    private boolean isStoreOpenAccordingRules(Iterable<OpenningHours> rules) {
        boolean result = true;
        try {
            List<OpenningHours> applicableRules = getApplicableRules(rules);
            result = isStoreOpenAccordingApplicableRules(applicableRules);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean isStoreOpenAccordingApplicableRules(List<OpenningHours> applicableRules) {
        int currentHour = getCurrentHour();

        try {
            int priority = applicableRules.stream()
                    .mapToInt(o -> o.getPriority())
                    .peek(System.out::println)
                    .min()
                    .orElseThrow(() -> new IllegalStateException("Not applicable rule was found!"));

            OpenningHours ruleWithHighestPriority = applicableRules.stream()
                    .filter(o -> o.getPriority() == priority)
                    .reduce((a, b) -> {
                        throw new IllegalStateException("Found multiple elements" + a + " " + b);
                    })
                    .get();

            log.debug("Going to apply rule:" + ruleWithHighestPriority);

            return ruleWithHighestPriority.getOpenning_hours() <= currentHour &&
                    ruleWithHighestPriority.getClosing_hours() > currentHour;

        } catch (Exception e){
            e.printStackTrace();
        }


        return true;
    }


    Predicate<OpenningHours> prio1Match = o -> o.getPriority() == 1;
    Predicate<OpenningHours> matchExactDay = o -> o.getMatcher().equals( getCurrentDayAndMonth() );
    Predicate<OpenningHours> notMinusValue = o -> o.getOpenning_hours() > 0 && o.getClosing_hours() > 0;
    Predicate<OpenningHours> prio2Match = o -> o.getPriority() == 2;
    Predicate<OpenningHours> matchDayFromWeek = o -> Integer.valueOf( o.getMatcher() ) == getCurrentDayFromWeek();
    Predicate<OpenningHours> prio3Rule = o -> o.getPriority() == 3;

    private List<OpenningHours> getApplicableRules(Iterable<OpenningHours> allRules) throws Exception {
        Predicate<OpenningHours> prio1Rule = prio1Match.and(matchExactDay).and(notMinusValue);
        Predicate<OpenningHours> prio2Rule = prio2Match.and(matchDayFromWeek).and(notMinusValue);
        List<OpenningHours> applicableRules = new ArrayList<>();
        addApplicableRule(allRules, applicableRules, prio1Rule);
        addApplicableRule(allRules, applicableRules, prio2Rule);
        addApplicableRule(allRules, applicableRules, prio3Rule);
        log.debug("Applicable rules: ");
        applicableRules.forEach(o -> log.debug( o.toString() ));
        return applicableRules;
    }

    private void addApplicableRule(Iterable<OpenningHours> allRules, List<OpenningHours> specificRules, Predicate<OpenningHours> predicate) {
        try {
            StreamSupport.stream(allRules.spliterator(), false)
                    .filter(predicate)
                    .reduce((a, b) -> {
                        throw new IllegalStateException("Found multiple elements" + a + " " + b);
                    }).ifPresent( o -> specificRules.add(o));

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private Iterable<OpenningHours> fetchRules() {
        Iterable<OpenningHours> allRules = openningHoursRepository.findAll();
        log.debug("All fetched rules: ");
        allRules.forEach(o -> log.debug( o.toString() ));
        return allRules;
    }

    private int getCurrentHour(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    private int getCurrentDayFromWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day == 0) day = 7;
        return day;
    }

    private String getCurrentDayAndMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String day =  cal.get(Calendar.DAY_OF_MONTH) + "." + (  cal.get(Calendar.MONTH) + 1 );
        return day;
    }
}
