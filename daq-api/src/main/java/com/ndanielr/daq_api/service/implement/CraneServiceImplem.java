package com.ndanielr.daq_api.service.implement;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ndanielr.daq_api.model.dto.CraneResponseDto;
import com.ndanielr.daq_api.model.dto.CreateCraneDto;
import com.ndanielr.daq_api.model.dto.UpdateCraneDto;
import com.ndanielr.daq_api.model.mapper.CraneMapper;
import com.ndanielr.daq_api.repository.CraneRepository;
import com.ndanielr.daq_api.service.CraneService;

import lombok.var;

@Service
public class CraneServiceImplem implements CraneService {

    private final CraneRepository craneRepository;
    private final CraneMapper craneMapper;

    public CraneServiceImplem(CraneRepository craneRepository,
            CraneMapper craneMapper) {
        this.craneRepository = craneRepository;
        this.craneMapper = craneMapper;
    }

    @Override
    public List<CraneResponseDto> getAll() {
        var list = craneRepository.findAll();
        return list.stream()
                .map(craneMapper::toDto)
                .toList();
    }

    @Override
    public CraneResponseDto getById(Long id) {
        var crane = craneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontro una grua con este id: " + id));

        return craneMapper.toDto(crane);
    }

    @Override
    public CraneResponseDto getByName(String name) {
        var crane = craneRepository.findByNameIgnoreCaseContains(name)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontro una grua con el nombre: " + name));

        return craneMapper.toDto(crane);
    }

    @Override
    public CraneResponseDto create(CreateCraneDto dto) {
        Boolean existingName = craneRepository.existsByName(dto.name());
        Boolean existingIp = craneRepository.existsByIp(dto.ip());

        if (existingName) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una grua con el nombre registrado:" + dto.name());
        }

        if (existingIp) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La IP ya esta siendo usada" + dto.ip());
        }

        var crane = craneMapper.toEntity(dto);
        var savedCrane = craneRepository.save(crane);
        return craneMapper.toDto(savedCrane);
    }

    @Override
    @Transactional
    public CraneResponseDto update(Long id, UpdateCraneDto dto) {
        var crane = craneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró una grúa con el id: " + id));

        if (craneRepository.existsByNameAndIdNot(dto.name(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una grúa con el nombre: " + dto.name());
        }

        crane.setName(dto.name());
        crane.setIp(dto.ip());
        crane.setRack(dto.rack());
        crane.setSlot(dto.slot());

        return craneMapper.toDto(craneRepository.save(crane));
    }

    @Override
    public void delete(Long id) {
        craneRepository.deleteById(id);
    }

}
