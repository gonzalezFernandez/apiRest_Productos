package com.formacionSpringBoot.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionSpringBoot.apirest.dao.ProductoDao;
import com.formacionSpringBoot.apirest.entity.Producto;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
	private ProductoDao productoDao;

	@Override
	@Transactional(readOnly=true)
	public List<Producto> findAll() {
		
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Producto findByCodigo(Long codigo) {
	
		return productoDao.findById(codigo).orElse(null);
	}

	@Override
	@Transactional
	public Producto save(Producto producto) {
	
		return productoDao.save(producto);
	}

	@Override
	@Transactional
	public void delete(Long codigo) {
		productoDao.deleteById(codigo);
		
	}

}
