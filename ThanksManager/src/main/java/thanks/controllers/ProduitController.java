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
	
/**********************************************************************************************************/
	/***************************************produits**********************************************/
	/****************************************************************************************************/
	@RequestMapping(value="index")
	public String index(Model  model , @RequestParam(name="page",defaultValue="0") int page, @RequestParam(name="keyword",defaultValue="")String keyword) {
		
		System.out.println("mot clé: "+keyword);
		Page<Produit> etds =  produitRepository.searchProduit("%"+keyword+"%", new PageRequest(page, 5));
		
		//association du produit avec le nom de son type à travers une map
		HashMap<Long,String> tp = new HashMap<Long, String>();
		for(Produit p:etds) {
			
			String typeName = typeproduitRepository.getNameById(p.getTypeProduit());
			tp.put(p.getId(),typeName);
		}
		
		
		
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
	public String formProduit(Model model) {
		
		List<Typeproduit> tp= typeproduitRepository.findAll();
		
		model.addAttribute("tp",tp);
		model.addAttribute("produit", new Produit());
		
		return "formProduit";
	}
	@RequestMapping(value="saveProduit", method=RequestMethod.POST)
	public String saveProduit(Model model,@Valid Produit et,BindingResult bindingResult,@RequestParam(name="picture")MultipartFile photo) throws IllegalStateException, IOException {
		
		
		if(bindingResult.hasErrors()) {
			
			return "formProduit";
		}
		
		produitRepository.save(et);
		if(!photo.isEmpty() ) {
			
			et.setImage(et.getId()+".jpg");
			
			photo.transferTo(new File(imageDir+et.getImage()));
			
		}
		
		return "redirect:index";
	}
	
	@RequestMapping(value="updateProduit", method=RequestMethod.POST)
	public String updateProduit(Model model,@Valid Produit et,BindingResult bindingResult,@RequestParam(name="picture")MultipartFile photo) throws IllegalStateException, IOException {
		
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
	
	/****************************************************************************************************/
	/***************************types produits***************************************/
	/*****************************************************************************************************/
	
	
	@RequestMapping(value="indexType")
	public String indexType(Model  model , @RequestParam(name="page",defaultValue="0") int page, @RequestParam(name="add",defaultValue="0")int add,@RequestParam(name="editId",defaultValue="0")Long editId, @RequestParam(name="keyword",defaultValue="")String keyword) {
		
		Page<Typeproduit> etds =  typeproduitRepository.searchTypeproduit("%"+keyword+"%", new PageRequest(page, 5));
				
		int pageCount = etds.getTotalPages();
		model.addAttribute("typesProduits", etds);
	
		int[] pages= new int[pageCount];
		for(int i=0;i<pageCount;i++) {
			pages[i]=i;
		}
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", page);
		model.addAttribute("keyword",keyword);
		
		//si on souhaite ajouter un type de produit
		if(add==1) {
			model.addAttribute("Typeproduit", new Typeproduit());
			model.addAttribute("add", 1);}
		//si on souhaite modifier un produit
		else if(add==2){
			
			Typeproduit tp= typeproduitRepository.getOne(editId);
			//System.out.println("edit"+ editId);
			model.addAttribute("edittp",tp);
			model.addAttribute("add", 2);
		}
		return "typesProduits";
		
	}

	@RequestMapping(value="saveTypeproduit", method=RequestMethod.POST)
	public String saveTypeproduit(Model model,@Valid Typeproduit tp,BindingResult bindingResult) throws IllegalStateException, IOException {
		
		System.out.println(tp.getId());
		if(bindingResult.hasErrors()) {
			
			return "formTypeproduit";
		}
		
		typeproduitRepository.save(tp);
		
		
		return "redirect:indexType";
	}
	
	@RequestMapping(value="updateTypeproduit", method=RequestMethod.POST)
	public String updateTypeproduit(Model model,@Valid Typeproduit tp,BindingResult bindingResult) throws IllegalStateException, IOException {
		
		System.out.println(tp.getName()+" : "+tp.getId());
		if(bindingResult.hasErrors()) {
			
			return "redirect:editType";
		}
		
		typeproduitRepository.save(tp);
		
		return "redirect:indexType";
	}
	
	@RequestMapping(value="/deleteType", method=RequestMethod.GET)
	public String supprimerType(Long id) {
		
		typeproduitRepository.deleteById(id);
		return "redirect:indexType";
		
	}
	@RequestMapping(value="/editType", method=RequestMethod.GET)
	public String editType(Long id,Model model) {
		
		
		Typeproduit tp = typeproduitRepository.getOne(id);
		model.addAttribute("typeproduit",tp);
		
		return "EditTypeproduit";
		
	}
	
	
}
