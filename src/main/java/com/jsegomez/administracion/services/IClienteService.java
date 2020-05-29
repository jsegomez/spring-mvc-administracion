package com.jsegomez.administracion.services;

import java.util.List;

import com.jsegomez.administracion.models.entity.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteService {

    public List<Cliente> findAll();

    public Page<Cliente> findAll(Pageable pageable);
    
    public Cliente save(Cliente cliente);

    public Cliente findOne(Long id);

    public void delete(Long id);

    public List<Cliente> findByName(String nombre);

    public List<Cliente> findByLastName(String apellido);
        
    public List<Cliente> findByEmail(String email);
    
}




