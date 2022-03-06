package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.colorDtos.ColorByIdDto;
import com.turkcell.rentacar.business.dtos.colorDtos.ColorListDto;
import com.turkcell.rentacar.business.requests.colorRequests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.colorRequests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface ColorService {

    DataResult<List<ColorListDto>> getAll();

    Result add(CreateColorRequest createColorRequest) throws BusinessException;

    DataResult<ColorByIdDto> getByColorId(int id) throws BusinessException;

    Result update(UpdateColorRequest updateColorRequest) throws BusinessException;

    Result deleteByColorId(int colorId) throws BusinessException;
}
