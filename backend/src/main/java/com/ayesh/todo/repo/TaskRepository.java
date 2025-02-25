package com.ayesh.todo.repo;

import com.ayesh.todo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Page<Task> findByCompletedFalseOrderByCreatedAtDesc(Pageable pageable);
}
