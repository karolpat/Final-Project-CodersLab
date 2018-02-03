package pl.coderslab.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.coderslab.repo.ProductRepository;

@Controller
public class ProductController {

	@Autowired
	private ProductRepository pRepo;
	
	@RequestMapping("/product")
	public String index(Model model) {
				
		model.addAttribute("prd", pRepo.findOne((long)1));
		return "product";
	}
	
	@RequestMapping("/products")
	public String list(Model model) {
		
		model.addAttribute("prds", pRepo.findAll());
		return "products";
	}
}
