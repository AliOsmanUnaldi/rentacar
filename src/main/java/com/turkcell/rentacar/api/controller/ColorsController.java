package com.turkcell.rentacar.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.ColorDto;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;

@RestController
@RequestMapping("/api/colors")

public class ColorsController {

    private ColorService colorService;

    public ColorsController(ColorService colorService) {

        this.colorService = colorService;
    }

    @GetMapping("/getall")
    public List<ColorListDto> getAll(){
        return this.colorService.getAll();
    }

    @GetMapping("/getbyid")
    public ColorDto getById(@RequestParam(required = true) int colorId){
        return this.colorService.getById(colorId);
    }

    @PostMapping("/add")
    public void add(@RequestBody CreateColorRequest createColorRequest) throws BusinessException {

        this.colorService.add(createColorRequest);
    }

    @PostMapping("/update")
    public void update(@RequestBody UpdateColorRequest updateColorRequest) throws BusinessException {
        this.colorService.update(updateColorRequest);
    }

    @DeleteMapping("/deletebyid")
    public void deleteById(int colorId) {
        this.colorService.deleteById(colorId);
    }



}
