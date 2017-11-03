package gt.edu.umg.io.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import gt.edu.umg.io.dto.TransporteRequest;
import gt.edu.umg.io.dto.TransporteResponse;
import gt.edu.umg.io.methods.CostoMinimos;
import gt.edu.umg.io.methods.EsquinaNoroeste;
import gt.edu.umg.io.methods.Voguel;
import gt.edu.umg.io.utils.TransporteUtils;

@Controller
@RequestMapping("/")
public class TransporteController {
	
	private static final Logger log = Logger.getLogger(TransporteController.class.getName());
	
	private final String METODO_NOROESTE = "1";
	private final String METODO_COSTOS_MINIMOS = "2";
	private final String METODO_VOGEL = "3";
	
	private final String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
			"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	
	@RequestMapping(method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        return "home";
    }
	
	@RequestMapping(value="/resolverproblema", params={"oferta", "demanda", "metodo", "precios"}, method=RequestMethod.POST)
	public ModelAndView detallePage(@RequestParam(value="oferta")String ofertaStr, 
			@RequestParam(value="demanda")String demandaStr, 
			@RequestParam(value="metodo")String metodo, 
			@RequestParam(value="precios")String preciosStr) {
		ModelAndView model = new ModelAndView();
		
		try {
			TransporteRequest transporteReq = TransporteUtils.parserSolicitud(ofertaStr, demandaStr, metodo, preciosStr);
			
			TransporteResponse resultado;
			if(METODO_NOROESTE.equals(metodo)) {
				resultado = EsquinaNoroeste.resolver(transporteReq);
			} else if(METODO_COSTOS_MINIMOS.equals(metodo)) {
				resultado = CostoMinimos.resolver(transporteReq);
			} else if(METODO_VOGEL.equals(metodo)) {
				resultado = Voguel.resolver(transporteReq);
			} else {
				resultado = new TransporteResponse();
			}
			resultado.setTitulos(letters);
			
			model.addObject("resultado", resultado);
		} catch (Exception e) {
			log.log(Level.INFO, "Ocurrio un error resolviendo el metodo: ", e);
		}
		
		model.setViewName("fragmentos/resultado");
		
		return model;
	}
}
