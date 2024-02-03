package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.converters.AppointmentConverter;
import com.sigepres.servicioweb.dto.AppointmentRequestDTO;
import com.sigepres.servicioweb.dto.AppointmentResponseDTO;
import com.sigepres.servicioweb.entities.Appointment;
import com.sigepres.servicioweb.exceptions.DuplicateValueException;
import com.sigepres.servicioweb.exceptions.ResourceNotFoundException;
import com.sigepres.servicioweb.repository.IAppointmentRepository;
import com.sigepres.servicioweb.service.interfaces.IAppointmentService;
import com.sigepres.servicioweb.service.interfaces.ICustomerService;
import com.sigepres.servicioweb.service.interfaces.IEmployeeService;
import com.sigepres.servicioweb.service.interfaces.IServicesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;


@Service
public class AppointmentServiceImpl implements IAppointmentService {

    private final IAppointmentRepository appointmentRepository;

    private final ICustomerService customerService;

    private final IEmployeeService employeeService;

    private final AppointmentConverter appointmentConverter;

    @Autowired
    private final ModelMapper modelMapper;


    public AppointmentServiceImpl(IAppointmentRepository appointmentRepository, ICustomerService customerService,
                                  IEmployeeService employeeService, AppointmentConverter appointmentConverter, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.appointmentConverter = appointmentConverter;
        this.modelMapper = modelMapper;
    }


    @Override
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentDTO) {
        validateAvailability(appointmentDTO);
        Appointment appointment =  appointmentConverter.fromDto(appointmentDTO);
        appointmentRepository.save(appointment);
        return appointmentConverter.fromEntity(appointment);
    }

    private void validateAvailability(AppointmentRequestDTO appointmentDTO) {
        // Verificar si ya existe una cita para el empleado en la misma fecha y hora
        Integer employeeId = appointmentDTO.getEmployeeId();
        LocalDate appointmentDatetime = appointmentDTO.getAppointmentDate();
        Time appointmentStartTime = appointmentDTO.getAppointmentStartTime();
        Time appointmentEndTime = appointmentDTO.getAppointmentEndTime();

        boolean isAppointmentExists = appointmentRepository
                .existsByEmployeeIdAndAppointmentDateAndTimeRange
                        (employeeId, appointmentDatetime, appointmentStartTime, appointmentEndTime);
        if (isAppointmentExists) {
            throw new DuplicateValueException("Ya existe una cita programada para la misma fecha, hora y empleado. Por favor, elija otra hora o fecha.");
        }
    }


    // Obtener una cita por id
    @Override
    public AppointmentResponseDTO getAppointmentById(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada."));
        return appointmentConverter.fromEntity(appointment);
    }

    // Obtener todas las citas de un empleado
    @Override
    public List<AppointmentResponseDTO>  getAppointmentsByEmployeeId(Integer employeeId) {
        // Validar si existe empleado
        employeeService.getEmployeeEntityById(employeeId);
        List<Appointment> appointments = appointmentRepository.findByEmployeeId(employeeId);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .toList();
    }

    // Obtener todas las citas de un cliente
    @Override
    public List<AppointmentResponseDTO> getAppointmentsByCustomerId(Integer customerId) {
        // validar si existe cliente
        customerService.getCustomerById(customerId);
        List<Appointment> appointments = appointmentRepository.findByCustomerId(customerId);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointments(){
        return appointmentRepository.findAll().stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<Appointment> appointments = appointmentRepository.findByAppointmentDatetimeBetween(startDate, endDate);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByCustomerAndDates(Integer customerId, LocalDate startDate, LocalDate endDate) {
        // validar si existe cliente
        customerService.getCustomerById(customerId);
        List<Appointment> appointments = appointmentRepository.findAppointmentsByCustomerAndDateBetween(customerId, startDate, endDate);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByEmployeeAndDates(Integer employeeId, LocalDate startDate, LocalDate endDate) {
        // validar si existe empleado
        employeeService.getEmployeeEntityById(employeeId);
        List<Appointment> appointments = appointmentRepository.findAppointmentsByEmployeeAndDateBetween(employeeId, startDate, endDate);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .toList();
    }

    public List<AppointmentResponseDTO> getAppointmentsByCustomerAndDate(Integer customerId, LocalDate date){
        // validar si existe cliente
        customerService.getCustomerById(customerId);
        List<Appointment> appointments = appointmentRepository.findAppointmentsByCustomerAndDate(customerId, date);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .toList();
    }

    public List<AppointmentResponseDTO> getAppointmentsByEmployeeAndDate(Integer employeeId, LocalDate date){
        // validar si existe empleado
        employeeService.getEmployeeEntityById(employeeId);
        List<Appointment> appointments = appointmentRepository.findAppointmentsByEmployeeAndDate(employeeId, date);
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .toList();
    }

    @Override
    public AppointmentResponseDTO updateAppointment(Integer appointmentId, AppointmentRequestDTO appointmentDTO) {
        // validar si existe cita a actualizar
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new ResourceNotFoundException("Cita con id " + appointmentId + "no existe");
        }
        this.validateAvailability(appointmentDTO);
        Appointment updatedAppointment = appointmentConverter.fromDto(appointmentDTO);
        updatedAppointment.setAppointmentId(appointmentId);
        appointmentRepository.save(updatedAppointment);
        return appointmentConverter.fromEntity(updatedAppointment);
    }

    @Override
    public void deleteAppointment(int appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new ResourceNotFoundException("Cita con id " + appointmentId + "no existe");
        }
        appointmentRepository.deleteById(appointmentId);
    }



}
