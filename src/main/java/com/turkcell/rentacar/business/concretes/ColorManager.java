package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.colorDtos.ColorByIdDto;
import com.turkcell.rentacar.business.dtos.colorDtos.ColorListDto;
import com.turkcell.rentacar.business.requests.colorRequests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.colorRequests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentacar.entities.concretes.Color;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorManager implements ColorService {

    private ColorDao colorDao;
    private ModelMapperService modelMapperService;

    public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
        this.colorDao = colorDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<ColorListDto>> getAll() {

        List<Color> result = this.colorDao.findAll();
        List<ColorListDto> response = result.stream()
                .map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<ColorListDto>>(response, BusinessMessages.ColorMessages.COLORS_LISTED);
    }

    @Override
    public Result add(CreateColorRequest createColorRequest) throws BusinessException {

        Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

        checkIfColorNameIsUnique(color.getColorName());

        this.colorDao.save(color);

        return new SuccessResult(BusinessMessages.ColorMessages.COLOR_ADDED);
    }

    @Override
    public DataResult<ColorByIdDto> getByColorId(int colorId) throws BusinessException {

        checkIfColorExists(colorId);

        Color color = this.colorDao.getById(colorId);
        ColorByIdDto response = this.modelMapperService.forDto().map(color, ColorByIdDto.class);

        return new SuccessDataResult<ColorByIdDto>(response,BusinessMessages.ColorMessages.COLOR_FOUND);
    }


    @Override
    public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {

        checkIfColorExists(updateColorRequest.getColorId());

        Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
        checkIfColorNameIsUnique(color.getColorName());

        this.colorDao.save(color);

        return new SuccessResult(BusinessMessages.ColorMessages.COLOR_UPDATED);
    }

    @Override
    public Result deleteByColorId(int colorId) throws BusinessException {

        checkIfColorExists(colorId);

        this.colorDao.deleteById(colorId);

        return new SuccessResult();
    }

    private boolean checkIfColorNameIsUnique(String colorName) throws BusinessException {

        for (ColorListDto colorElement : this.getAll().getData()) {

            if (colorElement.getColorName().equals(colorName)) {

                throw new BusinessException(BusinessMessages.ColorMessages.SAME_NAMED_COLORS_ARE_NOT_DESIRED);

            }
        }

        return true;

    }

    private boolean checkIfColorExists(int colorId) throws BusinessException {

        if (!colorDao.existsById(colorId)){

            throw new BusinessException(BusinessMessages.ColorMessages.COLOR_DOES_NOT_EXIST);

        }

        return true;

    }

}