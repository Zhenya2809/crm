package com.evgeniy.dev;

import com.evgeniy.entity.AppointmentToDoctors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    List<AppointmentDTO> infoToInfoDTO(List<AppointmentToDoctors> appointmentToDoctorsList);
}
