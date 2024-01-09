package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.dto.AppointmentRequestDTO;
import com.sigepres.servicioweb.dto.AppointmentResponseDTO;
import com.sigepres.servicioweb.entities.Appointment;
import com.sigepres.servicioweb.entities.Customer;
import com.sigepres.servicioweb.entities.Employee;
import com.sigepres.servicioweb.entities.Services;
import com.sigepres.servicioweb.exceptions.DuplicateValueException;
import com.sigepres.servicioweb.exceptions.ResourceNotFoundException;
import com.sigepres.servicioweb.repository.IAppointmentRepository;
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

    private final IServicesService servicesService;

    @Autowired
    private final ModelMapper modelMapper;


    public AppointmentServiceImpl(IAppointmentRepository appointmentRepository, ICustomerService customerService,
                                  IEmployeeService employeeService,  IServicesService servicesService, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.servicesService = servicesService;
        this.modelMapper = modelMapper;
    }


    @Override
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentRequestDTO) {

        // Validación que exista de Cliente, Empleado y Servicio
        Customer customer =  customerService.getCustomerById(appointmentRequestDTO.getCustomerId());
        Services service = servicesService.getServicesById(appointmentRequestDTO.getServiceId());
        Employee employee = employeeService.getEmployeeById(appointmentRequestDTO.getEmployeeId());

        // Verificación de disponibilidad, verificar si el empleado esta disponible en fecha y hora
        validateAvailability(appointmentRequestDTO);

        // Mapeo de los datos de entrada a entidad
        Appointment appointment = mapToAppointment(appointmentRequestDTO, customer, service,employee);

        // Guardar la cita en la base de datos
        appointmentRepository.save(appointment);

        // Mapeo de entidad a dto response
        return modelMapper.map(appointment, AppointmentResponseDTO.class);
    }

    private Appointment mapToAppointment(AppointmentRequestDTO appointmentRequestDTO, Customer customer, Services service, Employee employee) {
        Appointment appointment = modelMapper.map(appointmentRequestDTO, Appointment.class);
        appointment.setCustomer(customer);
        appointment.setService(service);
        appointment.setEmployee(employee);
        return appointment;
    }

    private void validateAvailability(AppointmentRequestDTO appointmentRequestDTO) {
        // Verificar si ya existe una cita para el empleado en la misma fecha y hora
        Integer employeeId = appointmentRequestDTO.getEmployeeId();
        LocalDate appointmentDatetime = appointmentRequestDTO.getAppointmentDate();
        Time appointmentStartTime = appointmentRequestDTO.getAppointmentStartTime();
        Time appointmentEndTime = appointmentRequestDTO.getAppointmentEndTime();

        boolean isAppointmentExists = appointmentRepository
                .existsByEmployeeIdAndAppointmentDateAndTimeRange
                        (employeeId, appointmentDatetime, appointmentStartTime, appointmentEndTime);
        if (isAppointmentExists) {
            throw new DuplicateValueException("Ya hay una cita asignada para el empleado en la misma fecha y hora.");
        }
    }


    // Obtener una cita por id
    @Override
    public AppointmentResponseDTO getAppointmentById(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada."));
        return modelMapper.map(appointment, AppointmentResponseDTO.class);
    }

    // Obtener todas las citas de un empleado
    @Override
    public List<AppointmentResponseDTO>  getAppointmentsByEmployeeId(Integer employeeId) {
        // Validar si existe empleado
        employeeService.getEmployeeById(employeeId);

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
        employeeService.getEmployeeById(employeeId);

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
        employeeService.getEmployeeById(employeeId);

        List<Appointment> appointments = appointmentRepository.findAppointmentsByEmployeeAndDate(employeeId, date);

        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .toList();
    }

    @Override
    public AppointmentResponseDTO updateAppointment(Integer appointmentId, AppointmentRequestDTO updatedAppointment) {



        // validar si existe cita a actualizar
        Appointment existingAppointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));

        // Verificación de disponibilidad, verificar si el empleado esta disponible en fecha y hora
        this.validateAvailability(updatedAppointment);


        // Mapeo de los datos de entrada a la entidad existente
        mapToExistingAppointment(updatedAppointment, existingAppointment);

        // Guardar la cita en la base de datos
        appointmentRepository.save(existingAppointment);

        // Mapeo de entidad a dto response
        return modelMapper.map(existingAppointment, AppointmentResponseDTO.class);
    }

    private void mapToExistingAppointment(AppointmentRequestDTO source, Appointment destination) {

        destination.setAppointmentDate(source.getAppointmentDate());
        destination.setAppointmentStartTime(source.getAppointmentStartTime());
        destination.setAppointmentEndTime(source.getAppointmentEndTime());
        destination.setAttended(source.isAttended());
    }


/*
    @Override
    public void deleteAppointment(int appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new EntityNotFoundException("Cita no encontrada con ID: " + appointmentId);
        }

        appointmentRepository.deleteById(appointmentId);
    }
*/


}
