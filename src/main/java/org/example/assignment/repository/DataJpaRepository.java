package org.example.assignment.repository;

import org.example.assignment.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataJpaRepository extends JpaRepository<Data, String> {
}
