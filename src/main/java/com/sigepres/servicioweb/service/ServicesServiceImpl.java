package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.entities.Services;
import com.sigepres.servicioweb.exceptions.ResourceNotFoundException;
import com.sigepres.servicioweb.repository.IServicesRepository;
import com.sigepres.servicioweb.service.interfaces.IServicesService;
import org.springframework.stereotype.Service;

@Service
public class ServicesServiceImpl implements IServicesService {

    private final IServicesRepository serviceRepository;

    public ServicesServiceImpl(IServicesRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Services getServicesById(Integer serviceId) {
        return serviceRepository
                .findById(serviceId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("El servicio ingresado no existe."));
    }
}
