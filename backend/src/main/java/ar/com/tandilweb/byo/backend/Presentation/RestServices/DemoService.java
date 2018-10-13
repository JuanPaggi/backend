package ar.com.tandilweb.byo.backend.Presentation.RestServices;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demoService")
public class DemoService {
	
	@RequestMapping(path="/info", method=RequestMethod.GET) 
	public String informationService() {
		return "hola mundo";
	}
}
