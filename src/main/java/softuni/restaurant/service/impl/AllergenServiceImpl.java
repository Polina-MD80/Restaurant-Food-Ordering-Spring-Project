package softuni.restaurant.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.restaurant.repository.AllergenRepository;
import softuni.restaurant.service.AllergenService;
import softuni.restaurant.model.entity.AllergenEntity;
import softuni.restaurant.model.entity.enums.AllergenEnumName;
import softuni.restaurant.model.view.AllergenViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllergenServiceImpl implements AllergenService {
    private final AllergenRepository allergensRepository;
    private final ModelMapper modelMapper;

    public AllergenServiceImpl(AllergenRepository allergensRepository, ModelMapper modelMapper) {
        this.allergensRepository = allergensRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initAllergens() {
        if (allergensRepository.count()==0){
            Arrays.stream(AllergenEnumName.values()).forEach(allergenEnumName -> {
                AllergenEntity allergenEntity = new AllergenEntity(allergenEnumName, allergenEnumName.getImageUrl());
                allergensRepository.save(allergenEntity);
            });
        }
    }

    @Override
    public List<AllergenViewModel> allAllergensOrderedByName() {
        List<AllergenEntity> allergenEntities = allergensRepository.findAllOrderedByName();
        return mapToView(allergenEntities);
    }

    private List<AllergenViewModel> mapToView(List<AllergenEntity> allergenEntities) {
        return allergenEntities.stream().map(allergenEntity -> modelMapper.map(
                allergenEntity, AllergenViewModel.class
        )).collect(Collectors.toList());
    }

    @Override
    public AllergenEntity findByName(String name) {
        AllergenEnumName allergenEnumName = modelMapper.map(name, AllergenEnumName.class);
        return allergensRepository.findByName(allergenEnumName).orElseThrow();
    }
}
