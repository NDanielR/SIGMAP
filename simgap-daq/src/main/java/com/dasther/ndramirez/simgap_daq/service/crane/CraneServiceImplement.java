package com.dasther.ndramirez.simgap_daq.service.crane;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dasther.ndramirez.simgap_daq.model.dto.cranedto.CraneRequestDto;
import com.dasther.ndramirez.simgap_daq.model.dto.cranedto.CraneResponseDto;
import com.dasther.ndramirez.simgap_daq.model.entity.crane.Crane;
import com.dasther.ndramirez.simgap_daq.repository.crane.CraneRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CraneServiceImplement implements CraneService {

    private final CraneRepository craneRepository;

    public CraneServiceImplement (CraneRepository craneRepository){
        this.craneRepository = craneRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CraneResponseDto> getAll() {
        return craneRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CraneResponseDto getByName(String name) {
        return craneRepository.findByName(name)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe una grúa con el nombre " + name
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CraneResponseDto> searchByName(String name) {
        return craneRepository
                .findByNameContainingIgnoreCaseOrderByNameAsc(name)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CraneResponseDto createCrane(CraneRequestDto craneDto) {
        if (craneRepository.findByName(craneDto.getName()).isPresent()) {
            throw new EntityExistsException(
                    "Ya existe una grúa con el nombre " + craneDto.getName()
            );
        }

        var crane = toEntity(craneDto);
        var savedCrane = craneRepository.save(crane);

        return toDto(savedCrane);
    }

    @Override
    @Transactional
    public CraneResponseDto updateCrane(Long id, CraneRequestDto craneDto) {

        var crane = craneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe una grúa con ID " + id
                ));

        if (craneRepository.existsByNameAndIdCraneNot(
                craneDto.getName(),
                id)) {
            throw new EntityExistsException(
                    "Ya existe otra grúa con el nombre "
                            + craneDto.getName()
            );
        }

        updateEntity(crane, craneDto);
        var updatedCrane = craneRepository.save(crane);

        return toDto(updatedCrane);
    }

    @Override
    @Transactional
    public void deleteCrane(Long id) {
        var crane = craneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe una grúa con ID " + id
                ));

        craneRepository.delete(crane);
    }

    private CraneResponseDto toDto(Crane crane) {
        var dto = new CraneResponseDto();
        dto.setIdCrane(crane.getIdCrane());
        dto.setName(crane.getName());
        dto.setType(crane.getType());
        dto.setIsOperational(crane.getIsOperational());
        return dto;
    }

    private Crane toEntity(CraneRequestDto dto) {
        var crane = new Crane();
        crane.setName(dto.getName());
        crane.setType(dto.getType());
        crane.setIsOperational(dto.getIsOperational());
        return crane;
    }

    private void updateEntity(Crane crane, CraneRequestDto dto) {
        crane.setName(dto.getName());
        crane.setType(dto.getType());
        crane.setIsOperational(dto.getIsOperational());
    }
}
