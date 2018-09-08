package ar.com.tandilweb.byo.backend.Presentation.RestServices;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.tandilweb.byo.backend.Presentation.dto.out.Hola;

@RestController
@RequestMapping("/demoService")
public class DemoService {

	// http://localhost:38674/demoService/helloWorld
	@RequestMapping(path = "/helloWorld", method = RequestMethod.GET)
	public Hola helloWorld() { // @RequestParam(value="qd", defaultValue="1") int qdata, @RequestBody PaymentRequest request
		Hola out = new Hola();
		out.mundo = "Ok";
		return out;
	}

}
