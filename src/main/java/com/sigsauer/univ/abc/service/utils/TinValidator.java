package com.sigsauer.univ.abc.service.utils;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class TinValidator {

    private static final LocalDate startDate = LocalDate.parse("1899-12-31");


    public static boolean validate(String tin, String dob, boolean sex) {
        if (tin.length() != 10) return false;

        //find number of days between dobDate and startDate
        LocalDate dobDate = LocalDate.parse(dob);
        if (dobDate.isBefore(startDate)) return false;
        long foundDays = DAYS.between(startDate, dobDate);

        //find sex
        int ninth = Integer.parseInt(String.valueOf(tin.charAt(8)));
        boolean foundSex = ninth % 2 != 0;

        //find last symbol
        int[] nums = new int[10];
        for (int i = 0; i < 10; i++) {
            nums[i] = Integer.parseInt(String.valueOf(tin.charAt(i)));
        }
        int sum = nums[0]*-1 + nums[1]*5 + nums[2]*7 + nums[3]*9 + nums[4]*4 + nums[5]*6 + nums[6]*10 + nums[7]*5 + nums[8]*7;
        int last = (sum % 11) % 10;

        //validate by all checks
        return foundDays == Integer.parseInt(tin.substring(0, 5)) && foundSex == sex && nums[9] == last;
    }
}
