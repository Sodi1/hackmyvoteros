package com.rosatom.myvote.repository;

import com.rosatom.myvote.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
