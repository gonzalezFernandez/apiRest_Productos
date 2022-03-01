package com.formacionSpringBoot.apirest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionSpringBoot.apirest.entity.Producto;


@Repository
public interface ProductoDao extends CrudRepository<Producto, Long>{

}
