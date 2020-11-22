package com.customer.ordermanagementsystem.services;

import com.customer.ordermanagementsystem.orders.Company;
import com.customer.ordermanagementsystem.orders.OpenningHours;
import com.customer.ordermanagementsystem.repository.CompanyRepository;
import com.customer.ordermanagementsystem.repository.OpenningHoursRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

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
        Optional<Company> company = companyRepository.findById(1l);

        boolean s = stringStatusToBoolean(status);

        company.ifPresent(c -> c.setStatus(s));

        company.ifPresent(c->companyRepository.save(c));

    }

    private boolean stringStatusToBoolean(String status) {
        if (status.matches("^[zZ].*"))
            return false;
        else
            return true;
    }

    @Override
    public void openAndCloseStoreWithMessage(String status, String message) {
        Optional<Company> company = companyRepository.findById(1l);

        boolean s = stringStatusToBoolean(status);

        company.ifPresent(c -> c.setStatus(s));

        company.ifPresent(c-> c.setStatusMessage(message));

        company.ifPresent(c->companyRepository.save(c));
    }

    @Override
    public boolean isStoreOpen() {
        boolean companyStatus = isCompanyStatus();
        if ( isCompanyStatus() )
            return openAndCloseStoreAccordingTimeSchedule();
        else
            return companyStatus;
    }


    private boolean isCompanyStatus() {
        Optional<Company> company = companyRepository.findById(1l);
        return company.get().isStatus();
    }


    @Override
    public void addItemToModel(Model model, String nameOfAttributeForMapping) {
        if ( ! isCompanyStatus() ) {
            log.info("Reason for closed is dedicated rest api call; not opening hours; fetching message about close reason and adding it to model");
            model.addAttribute(nameOfAttributeForMapping, companyRepository.findById(1l).get().getStatusMessage());
        }
    }

    @Override
    public boolean openAndCloseStoreAccordingTimeSchedule() {

        HashMap< String,List<Optional<OpenningHours>> > rules = fetchRules();

        boolean result = procesOpenningHoursRules(rules);

        return result;
    }

    private boolean procesOpenningHoursRules(HashMap<String, List<Optional<OpenningHours>>> rules) {
        boolean result = true;

        try {
            HashMap<String, List<OpenningHours>> applicableRules = getApplicableRules(rules);

            result                                               = processApplicableRules(applicableRules);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    private boolean processApplicableRules(HashMap<String, List<OpenningHours>> applicableRules) {
        int currentHour = getCurrentHour();

        System.out.println("tu " + currentHour);
        for (Map.Entry<String, List<OpenningHours>> input : applicableRules.entrySet()) { input.getValue().forEach(System.out::println);}

        OpenningHours generalRule = null;
        if ( applicableRules.get("generalRule").size() > 0 ) generalRule = applicableRules.get("generalRule").get(0);

        OpenningHours daysOfWeekRule = null;
        if ( applicableRules.get("daysOfWeekRules").size() > 0 ) daysOfWeekRule =  applicableRules.get("daysOfWeekRules").get(0);

        OpenningHours ruleForSpecificDay = null;
        if ( applicableRules.get("rulesForSpecificDays").size() > 0 ) ruleForSpecificDay =  applicableRules.get("rulesForSpecificDays").get(0);

        System.out.println(ruleForSpecificDay);
        System.out.println(daysOfWeekRule);
        System.out.println(generalRule);

        if (ruleForSpecificDay != null) {
            log.info("Applying " + ruleForSpecificDay);
            return ruleForSpecificDay.getOpenning_hours() <= currentHour && ruleForSpecificDay.getClosing_hours() > currentHour;
        }

        else if (daysOfWeekRule != null) {
            log.info("Applying " + daysOfWeekRule);
            return daysOfWeekRule.getOpenning_hours() <= currentHour && daysOfWeekRule.getClosing_hours() > currentHour;
        }

        else if (generalRule != null) {
            log.info("Applying " + generalRule);
            return generalRule.getOpenning_hours() <= currentHour && generalRule.getClosing_hours() > currentHour;
        }

        return true;
    }

    private HashMap<String, List<OpenningHours>> getApplicableRules(HashMap<String, List<Optional<OpenningHours>>> rules) throws Exception {
        HashMap<String, List<OpenningHours>> applicableRules = new HashMap<>();

        for (Map.Entry<String, List<Optional<OpenningHours>>> input : rules.entrySet()) { input.getValue().forEach(System.out::println);}

        applicableRules.put("generalRule", new ArrayList<>());
        applicableRules.put("daysOfWeekRules", new ArrayList<>());
        applicableRules.put("rulesForSpecificDays", new ArrayList<>());

        addEntriesForGeneralRule(rules, applicableRules);

        addEntriesForDaysOfWeekRules(rules, applicableRules);

        addEntriesForRulesForSpecificDays(rules, applicableRules);

        for (Map.Entry<String, List<OpenningHours>> input : applicableRules.entrySet()) {
           input.getValue().forEach(System.out::println);
        }


       return applicableRules;
    }

    private void addEntriesForRulesForSpecificDays(HashMap<String, List<Optional<OpenningHours>>> rules, HashMap<String, List<OpenningHours>> applicableRules) {
        OpenningHours tmpHour;
        for( Optional<OpenningHours> r : rules.get("rulesForSpecificDays")){
            if (r.isPresent()) {
                tmpHour = r.get();
                log.info(getCurrentDayAndMonth());
                if (tmpHour.getDayDescription().equals(getCurrentDayAndMonth())){
                    if(tmpHour.getOpenning_hours() != -1 && tmpHour.getOpenning_hours() != -1)
                        applicableRules.get("rulesForSpecificDays").add(tmpHour);
                }
            }
        }
    }

    private void addEntriesForDaysOfWeekRules(HashMap<String, List<Optional<OpenningHours>>> rules, HashMap<String, List<OpenningHours>> applicableRules) {
        OpenningHours tmpHour;
        for( Optional<OpenningHours> r : rules.get("daysOfWeekRules")){
            if (r.isPresent()) {
                tmpHour = r.get();
                log.info( String.valueOf( getCurrentDayFromWeek() ));
                if ( Integer.parseInt(tmpHour.getDayDescription() ) ==  getCurrentDayFromWeek() ){
                    if(tmpHour.getOpenning_hours() != -1 && tmpHour.getOpenning_hours() != -1)
                        applicableRules.get("daysOfWeekRules").add(tmpHour);
                }
            }
        }
    }

    private void addEntriesForGeneralRule(HashMap<String, List<Optional<OpenningHours>>> rules, HashMap<String, List<OpenningHours>> applicableRules) throws Exception {
        if ( ! rules.get("generalRule").get(0).isPresent())
            throw new Exception("general Rules is missing");
        else
            applicableRules.get("generalRule").add( rules.get("generalRule").get(0).get() );
    }

    private HashMap<String, List<Optional<OpenningHours>>> fetchRules() {
        HashMap<String, List<Optional<OpenningHours>>> map = new HashMap<>();

        map.put("generalRule", new ArrayList<>());
        map.put("daysOfWeekRules", new ArrayList<>());
        map.put("rulesForSpecificDays", new ArrayList<>());

        Iterable<OpenningHours> openingHours = openningHoursRepository.findAll();

        int counter = 0;
        for (OpenningHours o : openingHours) {

            if (counter == 0)
                map.get("generalRule").add(Optional.of(o));
            else if (counter <= 7)
                map.get("daysOfWeekRules").add(Optional.of(o));
            else
                map.get("rulesForSpecificDays").add(Optional.of(o));

            counter++;

        }
        return map;
    }


    @Override
    public String getOpenAndCloseStoreMessage() {
        return companyRepository.findById(1l).get().toString();
    }

    private int getCurrentHour(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        return cal.get(Calendar.HOUR_OF_DAY);
    }

    private int getCurrentDayFromWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    private String getCurrentDayAndMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        String day =  cal.get(Calendar.DAY_OF_MONTH) + "." + (  cal.get(Calendar.MONTH) + 1 );

        return day;
    }





}
