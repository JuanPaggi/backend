package ar.com.tandilweb.byo.backend.Presentation.RestServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.tandilweb.byo.backend.Presentation.dto.out.Hola;
import ar.com.tandilweb.byo.backend.Transport.UserAdapter;

@RestController
@RequestMapping("/demoService")
public class DemoService {
	
	@Autowired
	public UserAdapter userAdapter;

	// http://localhost:38674/demoService/helloWorld
	@RequestMapping(path = "/helloWorld", method = RequestMethod.GET)
	public Hola helloWorld() { // @RequestParam(value="qd", defaultValue="1") int qdata, @RequestBody PaymentRequest request
		Hola out = new Hola();
		out.setMundo("Ok");
		return out;
	}

}
