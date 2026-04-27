package com.fashion.repository;

import com.fashion.model.FashionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "items")
public interface ItemRepository extends JpaRepository<FashionItem, Long> {
}