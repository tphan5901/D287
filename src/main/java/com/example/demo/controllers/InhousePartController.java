package com.example.demo.controllers;
import com.example.demo.DataObjects.InhousePart;
import com.example.demo.Service.InhousePartService;
import com.example.demo.Service.InhousePartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InhousePartController {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private InhousePartService inhousePartService;
    InhousePart inhousePart = new InhousePart();

    @GetMapping("/InhousePartForm")
    public String addInhousePart(Model model){
        Long nextId = inhousePartService.getNextId();
        inhousePart.setId(nextId);

        model.addAttribute("inhousepart",inhousePart);
        return "InhousePartForm";
    }

    @PostMapping("/InhousePartForm")
    public String submitForm(@ModelAttribute("inhousepart") InhousePart part, BindingResult dataBinding, Model model){
        if(dataBinding.hasErrors()){
            model.addAttribute("inhousepart",part);
            return "InhousePartForm";
        } else {
            InhousePartService repo=context.getBean(InhousePartServiceImpl.class); //context.getBean returns instance of class and lets us access function properties
            repo.save(part);

            //redirectAttributes.addFlashAttribute("message", "Product deleted successfully!");
            return "redirect:/home";
        }
    }

}
