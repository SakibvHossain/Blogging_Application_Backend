package com.example.blogging_application_api.services.implementation;

import com.example.blogging_application_api.entity.Category;
import com.example.blogging_application_api.exception.CategoryNotFoundException;
import com.example.blogging_application_api.payload.dtos.CategoryDTO;
import com.example.blogging_application_api.repository.CategoryRepository;
import com.example.blogging_application_api.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * @param categoryDTO
     * @return category
     */
    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = dtoToCategory(categoryDTO);
        Category categorySaved = categoryRepository.save(category);
        return categoryToCategoryDTO(categorySaved);
    }

    /**
     * @param categoryDTO
     * @param categoryId
     * @return
     */
    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category getCategory = findCategoryByIdOrThrow(categoryId);

        getCategory.setCategory_title(categoryDTO.getCategory_title());
        getCategory.setCategory_description(categoryDTO.getCategory_description());
        Category categoryUpdated = categoryRepository.save(getCategory);
        return categoryToCategoryDTO(categoryUpdated);
    }

    /**
     * @param categoryId
     */
    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = findCategoryByIdOrThrow(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    /**
     * @param categoryId
     * @return
     */
    @Override
    public CategoryDTO getCategoryById(Integer categoryId) {
        Category category = findCategoryByIdOrThrow(categoryId);
        return categoryToCategoryDTO(category);
    }

    /**
     * @return
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(this::categoryToCategoryDTO).toList();
    }

    /**
     * @param categoryDTO
     * @param categoryId
     * @return
     */
    @Override
    public CategoryDTO specificFieldUpdate(CategoryDTO categoryDTO, Integer categoryId) {
        Category category = findCategoryByIdOrThrow(categoryId);
        if (categoryDTO.getCategory_title() != null){
            category.setCategory_title(categoryDTO.getCategory_title());
        }
        if (categoryDTO.getCategory_description() != null){
            category.setCategory_description(categoryDTO.getCategory_description());
        }
        Category categoryUpdated = categoryRepository.save(category);
        return categoryToCategoryDTO(categoryUpdated);
    }

    //DTO to Category
    public Category dtoToCategory(CategoryDTO categoryDTO){
        return modelMapper.map(categoryDTO, Category.class);
    }

    //Category to DTO
    public CategoryDTO categoryToCategoryDTO(Category category){
        return modelMapper.map(category, CategoryDTO.class);
    }

    private Category findCategoryByIdOrThrow(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}
