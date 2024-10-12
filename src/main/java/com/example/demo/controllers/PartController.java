package com.example.demo.controllers;
import com.example.demo.DataObjects.InhousePart;
import com.example.demo.DataObjects.OutsourcedPart;
import com.example.demo.DataObjects.Part;
import com.example.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
public class PartController {
    @Autowired
    private ApplicationContext context;

    @GetMapping("/UpdatePartForm")
    public String updatePart(@RequestParam("partID") int Id, Model model){
        PartService repo = context.getBean(PartServiceImpl.class);
        OutsourcedPartService outsourcedRepo = context.getBean(OutsourcedPartServiceImpl.class);
        InhousePartService inhouseRepo = context.getBean(InhousePartServiceImpl.class);

        OutsourcedPart outsourcedPart = outsourcedRepo.findById(Id);
        if (outsourcedPart != null) {
            model.addAttribute("outsourcedpart", outsourcedPart);
            return "OutsourcedPartForm";
        }

        InhousePart inhousePart = inhouseRepo.findById(Id);
        model.addAttribute("inhousepart", inhousePart);
        return "InhousePartForm";
    }

    @GetMapping("/deletepart")
    public String deletePart(@Valid @RequestParam("partID") int Id, RedirectAttributes redirectAttributes) {
        PartService repo = context.getBean(PartServiceImpl.class);
        Part part = repo.searchById(Id);

        if (part.getProducts().isEmpty()) {
            repo.deleteById(Id);
            return "redirect:/home";
        } else {
    //        redirectAttributes.addFlashAttribute("messageTitle", "Error");
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete a part that is associated with a product.");
            return "redirect:/partError";
        }
    }

    @GetMapping("/partError")
    public String partError(Model model) {
        return "partError";
    }

}
