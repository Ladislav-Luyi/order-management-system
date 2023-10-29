package com.customer.ordermanagementsystem.company.service;

import com.customer.ordermanagementsystem.company.domain.OpenningHours;
import com.customer.ordermanagementsystem.company.repository.OpenningHoursRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class OpeningHoursServiceImpl implements OpeningHoursService {

    private final OpenningHoursRepository openningHoursRepository;

    @Autowired
    public OpeningHoursServiceImpl(OpenningHoursRepository openningHoursRepository) {
        this.openningHoursRepository = openningHoursRepository;
    }

    private static OpenningHours getDefaultRule() {
        OpenningHours openningHours = new OpenningHours();
        openningHours.setPriority(1);
        openningHours.setDescription("default");
        openningHours.setOpenning_hours(11);
        openningHours.setClosing_hours(17);
        return openningHours;
    }

    private static int getHighestPriority(List<OpenningHours> applicableRules) {
        return applicableRules.stream()
                .mapToInt(OpenningHours::getPriority)
                .peek(System.out::println)
                .min()
                .orElseThrow(() -> new IllegalStateException("Not applicable rule was found!"));
    }

    private static OpenningHours getRuleWithHighestPriority(List<OpenningHours> applicableRules) {
        return applicableRules.stream()
                .filter(o -> o.getPriority() == getHighestPriority(applicableRules))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Found multiple elements" + a + " " + b);
                }).orElse(getDefaultRule());
    }

    private boolean isStoreOpenAccordingApplicableRules(List<OpenningHours> applicableRules) {
        try {
            OpenningHours rule = getRuleWithHighestPriority(applicableRules);
            log.debug("Going to apply rule:" + rule);
            int currentHour = TimeHelper.getCurrentHour();
            return rule.getOpenning_hours() <= currentHour &&
                    rule.getClosing_hours() > currentHour;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    Predicate<OpenningHours> priorityOneMatch = o -> o.getPriority() == 1;
    Predicate<OpenningHours> matchExactDay = o -> o.getMatcher().equals(TimeHelper.getCurrentDayAndMonth());
    Predicate<OpenningHours> validValue = o -> o.getOpenning_hours() > 0 && o.getClosing_hours() > 0;
    Predicate<OpenningHours> priorityTwoMatch = o -> o.getPriority() == 2;
    Predicate<OpenningHours> matchDayFromWeek = o -> Integer.parseInt(o.getMatcher()) == TimeHelper.getCurrentDayFromWeek();
    Predicate<OpenningHours> priorityThreeMatch = o -> o.getPriority() == 3;

    private void addApplicableRule(Iterable<OpenningHours> allRules, List<OpenningHours> specificRules, Predicate<OpenningHours> predicate) {
        try {
            StreamSupport.stream(allRules.spliterator(), false)
                    .filter(predicate)
                    .reduce((a, b) -> {
                        throw new IllegalStateException("Found multiple elements" + a + " " + b);
                    }).ifPresent(specificRules::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<OpenningHours> getApplicableRules(Iterable<OpenningHours> allRules) {
        Predicate<OpenningHours> priorityOneRule = priorityOneMatch.and(matchExactDay).and(validValue);
        Predicate<OpenningHours> priorityTwoRule = priorityTwoMatch.and(matchDayFromWeek).and(validValue);
        List<OpenningHours> applicableRules = new ArrayList<>();
        addApplicableRule(allRules, applicableRules, priorityOneRule);
        addApplicableRule(allRules, applicableRules, priorityTwoRule);
        addApplicableRule(allRules, applicableRules, priorityThreeMatch);
        log.debug("Applicable rules: ");
        applicableRules.forEach(o -> log.debug(o.toString()));
        return applicableRules;
    }

    private boolean isStoreOpen(List<OpenningHours> rules) {
        boolean result = true;
        try {
            result = isStoreOpenAccordingApplicableRules(getApplicableRules(rules));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean isStoreOpen() {
        return isStoreOpen(openningHoursRepository.findAll());
    }
}
