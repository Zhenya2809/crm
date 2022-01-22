package com.evgeniy.dev;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class AppointmentDTO {
    private String date;
    private String time;
    private String email;
}
