package thanks.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import thanks.dao.ProduitRepository;
import thanks.dao.TypeproduitRepository;
import thanks.entities.Produit;
import thanks.entities.Typeproduit;


@Controller
@RequestMapping(value="produits")
public class ProduitController {

	@Autowired  
	private ProduitRepository produitRepository;
	@Autowired
	private TypeproduitRepository typeproduitRepository;
	@Value(value = "${dir.images}")
	private String imageDir;
	
	
	@RequestMapping(value="index")
	public String index(Model  model , @RequestParam(name="page",defaultValue="0") int page, @RequestParam(name="keyword",defaultValue="")String keyword) {
		
		Page<Produit> etds =  produitRepository.searchProduit("%"+keyword+"%", new PageRequest(page, 5));
		
		//association du produit avec le nom de son type à travers une map
		HashMap<Long,String> tp = new HashMap<Long, String>();
		for(Produit p:etds) {
			String typeName = typeproduitRepository.getNameById(p.getTypeProduit());
			System.out.println(p.getTypeProduit()+" : "+typeName);
			tp.put(p.getId(),typeName);
		}
		System.out.println("Map:"+tp.toString());
		
		
		int pageCount = etds.getTotalPages();
		model.addAttribute("produits", etds);
		model.addAttribute("tp",tp);
		int[] pages= new int[pageCount];
		for(int i=0;i<pageCount;i++) {
			pages[i]=i;
		}
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", page);
		model.addAttribute("keyword",keyword);
		return "produits";
		
	}
	@RequestMapping(value="formProduit", method=RequestMethod.GET)
	public String formEtudiant(Model model) {
		model.addAttribute("produit", new Produit());
		model.addAttribute("success",0);
		return "formProduit";
	}
	@RequestMapping(value="saveProduit", method=RequestMethod.POST)
	public String saveProduit(Model model,@Valid Produit et,BindingResult bindingResult,@RequestParam(name="picture")MultipartFile photo) throws IllegalStateException, IOException {
		
		
		if(bindingResult.hasErrors()) {
			System.out.println(et.getId());
			return "formProduit";
		}
		System.out.println(et.getId());
		produitRepository.save(et);
		if(!photo.isEmpty() ) {
			System.out.println("photo");
			et.setImage(et.getId()+".jpg");
			System.out.println(et.getImage());
			photo.transferTo(new File(imageDir+et.getImage()));
			
		}
		
		return "redirect:index";
	}
	
	@RequestMapping(value="updateProduit", method=RequestMethod.POST)
	public String updateEtudiant(Model model,@Valid Produit et,BindingResult bindingResult,@RequestParam(name="picture")MultipartFile photo) throws IllegalStateException, IOException {
		
		if(bindingResult.hasErrors()) {
			
			return "redirect:edit";
		}
		
		produitRepository.save(et);
		if(!photo.isEmpty() ) {
			System.out.println("photo");
			et.setImage(et.getId()+".jpg");
			System.out.println(et.getImage());
			photo.transferTo(new File(imageDir+et.getImage()));
			
		}
		return "redirect:index";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String supprimer(Long id) {
		
		produitRepository.deleteById(id);
		return "redirect:index";
		
	}
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String edit(Long id,Model model) {
		
		
		Produit et = produitRepository.getOne(id);
		List<Typeproduit> tp= typeproduitRepository.findAll();
		for(Typeproduit t:tp)System.out.println(t.getId()+" : "+t.getName());
		model.addAttribute("produit",et);
		model.addAttribute("tp",tp);
		return "EditProduit";
		
	}
	@RequestMapping(value="/getImage",produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(Long id) {
		File f = new File(imageDir+id+".jpg");
		try {
			return IOUtils.toByteArray(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;}
}
