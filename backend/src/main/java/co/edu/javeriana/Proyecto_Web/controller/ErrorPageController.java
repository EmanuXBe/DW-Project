package co.edu.javeriana.Proyecto_Web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorPageController {

    @RequestMapping("/error-page")
    public String errorPage(HttpServletRequest request, Model model) {
        Object ex = request.getAttribute("exceptionText");
        model.addAttribute("exceptionText", ex != null ? ex.toString() : "Acceso denegado");
        return "error-page";
    }
}
