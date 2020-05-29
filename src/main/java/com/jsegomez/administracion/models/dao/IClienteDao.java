package com.jsegomez.administracion.models.dao;

import java.util.List;

import com.jsegomez.administracion.models.entity.Cliente;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{

    @Query("select c from Cliente c where c.nombre like %?1%")
    public List<Cliente> findByName(String nombre);

    @Query("select c from Cliente c where c.apellido like %?1%")
    public List<Cliente> findByLastName(String apellido);
    
    @Query("select c from Cliente c where c.email like %?1%")
    public List<Cliente> findByEmail(String email);
            
}





















