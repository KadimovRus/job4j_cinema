package ru.job4j.cinemajob.service;

import ru.job4j.cinemajob.dto.FileDto;

import java.util.Optional;

public interface FileService {
    Optional<FileDto> getFileById(int id);

}
