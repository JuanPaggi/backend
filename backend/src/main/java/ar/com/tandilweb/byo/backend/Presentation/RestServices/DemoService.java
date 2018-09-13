package ar.com.tandilweb.byo.backend.Presentation.RestServices;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demoService")
public class DemoService {
	
//	@Autowired
//	public TablaTestAdapter tablaTestAdapter;
//	
//	@Autowired
//	public DemoAdapter demoAdapter;
//
//	// http://localhost:38674/demoService/helloWorld
//	@RequestMapping(path = "/helloWorld", method = RequestMethod.GET)
//	public Hola helloWorld() { // @RequestParam(value="qd", defaultValue="1") int qdata, @RequestBody PaymentRequest request
//		Hola out = demoAdapter.getApiDemo();
////		out.setMundo("GG");
//		return out;
//	}
//	
//	@RequestMapping(path="/nuevoRegistro", method = RequestMethod.GET)
//	public boolean nuevoRegistro() {
//		return tablaTestAdapter.nuevoRegistro();
//	}
//	
//	@RequestMapping(path="/listaRegistros", method = RequestMethod.GET)
//	public List<Hola> listaRegistros() {
//		return tablaTestAdapter.listaRegistros();
//	}
}
