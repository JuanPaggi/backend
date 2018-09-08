package ar.com.tandilweb.byo.backend.Transport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.TablaTest;
import ar.com.tandilweb.byo.backend.Model.repository.TablaTestRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.Hola;

public class TablaTestAdapter {
	
	@Autowired
	public TablaTestRepository repositorioTablaTest;
	
	public boolean nuevoRegistro() {
		// llamar al modelo o al gateway
		TablaTest item = new TablaTest();
		item.setNamejaja("Hola Mundo");
		repositorioTablaTest.save(item);
		return true;
	}
	
	public List<Hola> listaRegistros() {
		List<Hola> out = new ArrayList<Hola>();
		
		Iterable<TablaTest> items = repositorioTablaTest.findAll();
		
		for(TablaTest item: items) {
			Hola itemOut = new Hola();
			itemOut.setHola(item.getNamejaja());
			itemOut.setMundo(item.getId_tabla().toString());
			out.add(itemOut);
		}
		
		return out;
	}

}
