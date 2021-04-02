package com.project.love_data.wrapper;

import com.project.love_data.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Integer> {
}
