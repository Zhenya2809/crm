package com.evgeniy.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;

@Data
public class DaySchedule {
    String date;
    HashSet<String> available;

}
