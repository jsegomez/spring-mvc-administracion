package com.jsegomez.administracion.services;

import java.util.List;

import com.jsegomez.administracion.models.dao.IClienteDao;
import com.jsegomez.administracion.models.entity.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteDao clienteDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }


    @Override
    public Cliente save(Cliente cliente) {
        return clienteDao.save(cliente);
    }

    @Override
    @Transactional
    public Cliente findOne(Long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteDao.deleteById(id);
    }

    @Override
    public List<Cliente> findByName(String nombre) {
        return clienteDao.findByName(nombre);
    }

    @Override
    public List<Cliente> findByLastName(String apellido) {
        return findByLastName(apellido);
    }

    @Override
    public List<Cliente> findByEmail(String email) {
        return findByEmail(email);
    }



}

