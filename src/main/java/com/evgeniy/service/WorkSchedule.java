//package com.evgeniy.service;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class WorkSchedule {
//    final String date = "* 37 16 19 1 *";
//    //*-second  37-minutes 16-hour 19-day 1-month *-year
//    // ("* * * * * *")minute hour dayOfMonth month dayOfWeek years
//    // * - all
//    // ? -any
//    // / -increments
//    // - -range
//
//    @Scheduled(cron = date)
//    public void runFirst() throws InterruptedException {
//        System.out.println("first");
//        Thread.sleep(1000);
//    }
//
//}
