package com.turkcell.rentacar.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;


@RestController
@RequestMapping("/api/brands")

public class BrandsController {

    private BrandService brandService;

    public BrandsController(BrandService brandService) {
        super();
        this.brandService = brandService;
    }

    @GetMapping("/getall")
    public List<BrandListDto> getAll(){
        return this.brandService.getAll();
    }

    @PostMapping("/add")
    public void add(@RequestBody CreateBrandRequest createBrandRequest) throws BusinessException {

        this.brandService.add(createBrandRequest);
    }

    @GetMapping("/getbyid")
    public BrandDto getById(@RequestParam(required = true) int brandId){
        return this.brandService.getById(brandId);
    }

    @PostMapping("/update")
    public void update(@RequestBody UpdateBrandRequest updateBrandRequest) throws BusinessException {
        this.brandService.update(updateBrandRequest);
    }

    @DeleteMapping("/deletebyid")
    public void deleteById(int brandId) {
        this.brandService.deleteById(brandId);
    }



}
