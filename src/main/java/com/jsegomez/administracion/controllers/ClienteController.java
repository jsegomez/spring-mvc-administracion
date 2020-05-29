package com.jsegomez.administracion.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsegomez.administracion.models.entity.Cliente;
import com.jsegomez.administracion.paginador.PageRender;
import com.jsegomez.administracion.services.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    // =================================== Métodos para GET ===================================

    @GetMapping(path = { "", "/", "/index" })
    public String index(Model model) {
        model.addAttribute("titulo", "Inicio Spring");
        return "index";
    }

    // Método para mostrar todos los clientes
    @GetMapping(path = "/clientes")
    public String clientes(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

        Pageable pageRequest = PageRequest.of(page, 15);

        Page<Cliente> clientes = clienteService.findAll(pageRequest);

        PageRender<Cliente> pageRender = new PageRender<>("/clientes", clientes);
        model.addAttribute("titulo", "Clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);
        return "clientes/clientes";
    }

    // Método para seleccionar cliente por su ID
    @GetMapping(path = "/cliente/{id}")
    public String cliente(@PathVariable Long id, Model model, RedirectAttributes flash) {

        Cliente cliente = null;

        if (id > 0) {            
            cliente = clienteService.findOne(id);
            if(cliente == null){
                flash.addFlashAttribute("error", "Cliente no existe en la base de datos");
                return "redirect:/clientes/busqueda";
            }
        }

        model.addAttribute("titulo", "Detalle cliente");
        model.addAttribute("cliente", cliente);

        return "clientes/detalle";
    }


    // Método para servir página de busqueda
    @GetMapping(path = "/clientes/busqueda")
    public String busqueda(Model model){
        return "clientes/busqueda";
    }


    // Método para buscar por nombre
    @GetMapping(path = "/clientes/nombre/{nombre}")
    public String findByName(@PathVariable String nombre, Model model){

        List<Cliente> clientes = clienteService.findByName(nombre);
        model.addAttribute("clientes", clientes);
        return "clientes/clientes";
    }

    // ========================== Métodos para editar - crear ==========================

    // Método para servir formulario
    @GetMapping(path = "/registrar")
    public String registrar(final Model model) {
        final Cliente cliente = new Cliente();
        model.addAttribute("titulo", "Registro de cliente");
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    // Método guardar o editar un cliente
    @PostMapping(path = "/registrar")
    public String guardar(  @Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash,
                            @RequestParam(name = "imagen-cliente") MultipartFile  foto, SessionStatus status)
    {
        if(!foto.isEmpty()){

            if(
                cliente.getId() != null && cliente.getId() > 0 &&
                cliente.getFoto() != null && cliente.getFoto().length() > 0
            ){
                Path rootPath = Paths.get("imagenes").resolve(cliente.getFoto()).toAbsolutePath();
                File archivo = rootPath.toFile();

                if(archivo.exists() && archivo.canRead()){
                    archivo.delete();
                }
            }

            String nombreUnico = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
            Path rootDir = Paths.get("imagenes").resolve(nombreUnico);
            Path directorioPath = rootDir.toAbsolutePath();

            try{
                Files.copy(foto.getInputStream(), directorioPath);                
                cliente.setFoto(nombreUnico);
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Registro de cliente");
            return "clientes/formulario";
        }

        if(cliente.getId() == null){
            flash.addFlashAttribute("success","Cliente creado con éxito");
        }else if(cliente.getId() != null){
            flash.addFlashAttribute("success","Cliente modificado con éxito");
        }

        clienteService.save(cliente);        
        
        status.setComplete();
        return "redirect:/cliente/".concat(cliente.getId().toString());
    }

    // Método para cargar formulario para edición de cliente    
    @GetMapping(path = "/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {

        Cliente cliente = new Cliente();

        if (id > 0) {
            cliente = clienteService.findOne(id);
            if(cliente == null){
                flash.addFlashAttribute("error", "error");
                return "/clientes/busqueda";
            }
        } else if(id == 0){
            flash.addFlashAttribute("error", "error");
            return "/clientes/busqueda";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Edición de cliente");

        return "/clientes/formulario";
    }

    // Método para eliminar cliente
    @GetMapping(path = "/cliente/eliminar/{id}")
    public String eliminar(@PathVariable Long id, Model model, RedirectAttributes flash) {

        if(id > 0){
            Cliente cliente = clienteService.findOne(id);

            if(cliente != null){
                Path rootPath = Paths.get("imagenes").resolve(cliente.getFoto()).toAbsolutePath();
                File archivo = rootPath.toFile();
                
                if(archivo.exists() && archivo.canRead() && archivo.length() > 0){
                    if(archivo.delete()){
                        flash.addFlashAttribute("info", "Imagen eliminada con éxito");
                    }
                }                
            }

            clienteService.delete(id);
        }
        flash.addFlashAttribute("success","Cliente eliminado con éxito");
        return "redirect:/clientes";
    }
}
