package com.urise.webapp.storage;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class ResumeTestData {
    private final static Random rnd = new Random();


    private final static WorkPeriod[] workPeriod_shit = {
            new WorkPeriod(DateUtil.of(1995, Month.APRIL),
                    DateUtil.of(1996, Month.AUGUST),
                    "Консультант по утилизации отходов",
                    "Административная работа"),
            new WorkPeriod(DateUtil.of(1996, Month.AUGUST), DateUtil.of(2011, Month.JULY),
                    "Певец",
                    "Группа линкин парк")
    };
    private final static WorkPeriod[] workPeriod_soprano = {
            new WorkPeriod(DateUtil.of(2001, Month.DECEMBER),
                    DateUtil.of(2005, Month.APRIL),
                    "Штатный киллр", "" +
                    "Ликвидация неугодных!"),
            new WorkPeriod(DateUtil.of(2005, Month.APRIL),
                    DateUtil.of(2010, Month.AUGUST),
                    "Продавец мафии", ""),
            new WorkPeriod(DateUtil.of(2010, Month.AUGUST),
                    DateUtil.of(2015, Month.FEBRUARY), "" +
                    "Шкуроход", "Шкуроходная деятельность!")
    };
    private final static WorkPeriod[] workPeriod_study = {
            new WorkPeriod(DateUtil.of(1995, Month.JUNE),
                    DateUtil.of(2005, Month.OCTOBER),
                    "Старший научный сотрудник", "" +
                    "Научная деятельность!")
    };

    private ResumeTestData() {
    }

    public static Resume getResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        final Map<ContactType, String> contacts = resume.getContacts();
        final Map<SectionType, Section> sections = resume.getSections();

        for (ContactType contactType : ContactType.values()) {
            contacts.put(contactType, generateRandomString(contactType.toString().length()));
        }

        //sections.put(SectionType.EXPERIENCE)

        sections.put(SectionType.PERSONAL, new ListSection(getListValue()));
        sections.put(SectionType.OBJECTIVE, new TextSection(generateRandomString(10)));
        sections.put(SectionType.ACHIEVEMENT, new ListSection(getListValue()));


        return new Resume(uuid, fullName);
    }

    public static WorkPeriod[] getWorkPeriod() {
        int num = rnd.nextInt(100);
        if (num > 0 && num < 30) {
            return workPeriod_shit;
        } else if (num > 30 && num < 61) {
            return workPeriod_soprano;
        } else {
            return workPeriod_study;
        }
    }

    private static String generateRandomNumber(int length) {
        final String charList = "1234567890";
        return generateRandomStringUsingSecureRandom(length, charList);
    }

    public static String generateRandomString(int length) {
        final String charList = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return generateRandomStringUsingSecureRandom(length, charList);
    }

    private static String generateRandomStringUsingSecureRandom(int length, String charList) {
        StringBuffer randStr = new StringBuffer(length);
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            randStr.append(charList.charAt(secureRandom.nextInt(charList.length())));
        }
        return randStr.toString();
    }

    public static List<String> getListValue() {
        List<String> values = Arrays.asList("", "", "", "", "", "", "", "");
        values = values.stream().map(val -> generateRandomString(10)).collect(Collectors.toList());
        return values;
    }
}
