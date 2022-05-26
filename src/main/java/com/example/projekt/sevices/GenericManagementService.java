package com.example.projekt.sevices;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.NoSuchElementException;

public class GenericManagementService<T, U extends JpaRepository<T, Long>> implements ManagementInterface<T> {
    protected U repository;

    public GenericManagementService(U repository) {
        this.repository = repository;
    }

    @Override
    public T add(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T show(Long id)  {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No entity found for such id!"));
    }

    @Override
    public Page<T> showAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<T> getAll(){ return repository.findAll();
    }


}
