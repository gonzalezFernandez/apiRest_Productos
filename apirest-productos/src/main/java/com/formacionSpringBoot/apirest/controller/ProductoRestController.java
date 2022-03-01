package com.formacionSpringBoot.apirest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formacionSpringBoot.apirest.entity.Producto;
import com.formacionSpringBoot.apirest.service.ProductoService;

@RestController
@RequestMapping("/api")
public class ProductoRestController {
	
	@Autowired
	private ProductoService servicio;
	
	@GetMapping({"/productos", "/todos"})
	public List<Producto> index(){
		return servicio.findAll();
	}
	
	@GetMapping("productos/{id}")
	public ResponseEntity<?>  findProductoById(@PathVariable Long codigo) {
		Producto producto = null;
		Map<String, Object> response=new HashMap<>();
		
		try {
			producto = servicio.findByCodigo(codigo);
		}catch (DataAccessException e) {//MUY ESPECÍFICO, EXCEPCIONES SOBRE EL DAO
			response.put("mensaje", "Error al rellenar la consulta a base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(producto ==null) {
		response.put("mensaje", "El Código de producto: ".concat(codigo.toString().concat(" no existe en la base de datos")));
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		
		}
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
	
	
	@PostMapping("/producto")
	public ResponseEntity<?> savePorducto(@RequestBody Producto producto){
		Producto productoNew = null;
		
		Map<String, Object>response = new HashMap<>();
		
		try {
			productoNew = servicio.save(producto);
			
			
		}catch (DataAccessException e) {//MUY ESPECÍFICO, EXCEPCIONES SOBRE EL DAO
			response.put("mensaje", "Error al rellenar la consulta a base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El producto ha sido creado con éxito!");
		response.put("producto", productoNew);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/producto/{id}")
	public ResponseEntity<?> updateCliente(@RequestBody Producto producto, @PathVariable Long codigo) {
		
		Producto productoActual = servicio.findByCodigo(codigo);
		
		Map<String, Object> response = new HashMap<>();
		
		if(productoActual == null) {
			response.put("mensaje", "Error: no se pudo editar. El producto con Codigo: "+codigo.toString()+" no existe en la base de datos");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
		productoActual.setTipo(producto.getTipo());
		productoActual.setCantidad(producto.getCantidad());
		productoActual.setPrecio(producto.getPrecio());
		productoActual.setMarca(producto.getMarca());
		productoActual.setFecha_ingreso(producto.getFecha_ingreso());
		productoActual.setDescripcion(producto.getDescripcion());
		
		servicio.save(productoActual);
		
		}catch (DataAccessException e) {//MUY ESPECÍFICO, EXCEPCIONES SOBRE EL DAO
			response.put("mensaje", "Error al rellenar la consulta a base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El producto ha sido actualizado con éxito!");
		response.put("cliente", productoActual);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
		
	}
	
	@DeleteMapping("/producto/{id}")
	public void deleteProducto(@PathVariable Long codigo) {
		servicio.delete(codigo);
	}
	
	
}
