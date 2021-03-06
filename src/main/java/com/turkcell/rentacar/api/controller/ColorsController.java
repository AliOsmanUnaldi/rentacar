package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.colorDtos.ColorByIdDto;
import com.turkcell.rentacar.business.dtos.colorDtos.ColorListDto;
import com.turkcell.rentacar.business.requests.colorRequests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.colorRequests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colors")

public class ColorsController {

    private ColorService colorService;

    public ColorsController(ColorService colorService) {

        this.colorService = colorService;
    }

    @GetMapping("/getall")
    public DataResult<List<ColorListDto>> getAll() {
        return this.colorService.getAll();
    }

    @GetMapping("/getbyid")
    public DataResult<ColorByIdDto> getById(@RequestParam(required = true) int colorId) throws BusinessException {

        return this.colorService.getByColorId(colorId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateColorRequest createColorRequest) throws BusinessException {

        return this.colorService.add(createColorRequest);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateColorRequest updateColorRequest) throws BusinessException {

        return this.colorService.update(updateColorRequest);
    }

    @DeleteMapping("/deletebyid")
    public Result deleteById(@RequestParam int colorId) throws BusinessException {

        return this.colorService.deleteByColorId(colorId);
    }


}
