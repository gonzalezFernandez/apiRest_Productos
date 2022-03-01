package com.formacionSpringBoot.apirest.service;

import java.util.List;

import com.formacionSpringBoot.apirest.entity.Producto;

public interface ProductoService {
	
	public List<Producto> findAll();
	
	public Producto findByCodigo(Long codigo);
	
	public Producto save(Producto producto);
	
	public void delete(Long codigo);

}
