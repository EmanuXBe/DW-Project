package co.edu.javeriana.Proyecto_Web.exception_handler;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AppControlerAdvice {
    
    @ExceptionHandler(NoSuchElementException.class)
    public ModelAndView handlerNotFoundException(NoSuchElementException e) {
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("exceptionText", e.toString());
        return modelAndView;
    }
}
