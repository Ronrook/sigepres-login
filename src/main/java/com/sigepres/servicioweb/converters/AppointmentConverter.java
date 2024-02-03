package com.sigepres.servicioweb.converters;

import com.sigepres.servicioweb.dto.AppointmentRequestDTO;
import com.sigepres.servicioweb.dto.AppointmentResponseDTO;
import com.sigepres.servicioweb.entities.Appointment;
import com.sigepres.servicioweb.service.interfaces.ICustomerService;
import com.sigepres.servicioweb.service.interfaces.IEmployeeService;
import com.sigepres.servicioweb.service.interfaces.IServicesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentConverter implements AbstractConverter<Appointment, AppointmentRequestDTO, AppointmentResponseDTO>{

    private final ICustomerService customerService;

    private final IEmployeeService employeeService;

    private final IServicesService servicesService;
    @Autowired
    private final ModelMapper modelMapper;

    public AppointmentConverter(ICustomerService customerService, IEmployeeService employeeService, IServicesService servicesService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.servicesService = servicesService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Appointment fromDto(AppointmentRequestDTO dto) {
        Appointment appointment = modelMapper.map(dto, Appointment.class);
        appointment.setCustomer(customerService.getCustomerById(dto.getCustomerId()));
        appointment.setService(servicesService.getServicesById(dto.getServiceId()));
        appointment.setEmployee(employeeService.getEmployeeEntityById(dto.getEmployeeId()));
        return appointment;
    }

    @Override
    public AppointmentResponseDTO fromEntity(Appointment appointment) {
        return  modelMapper.map(appointment, AppointmentResponseDTO.class);
    }
}
