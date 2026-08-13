package com.dasther.ndramirez.simgap_daq.api.service.device;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dasther.ndramirez.simgap_daq.api.exception.DuplicateResourceException;
import com.dasther.ndramirez.simgap_daq.api.exception.ResourceNotFoundException;
import com.dasther.ndramirez.simgap_daq.api.model.dto.devicedto.DeviceRequestDto;
import com.dasther.ndramirez.simgap_daq.api.model.dto.devicedto.DeviceResponseDto;
import com.dasther.ndramirez.simgap_daq.api.model.entity.crane.Crane;
import com.dasther.ndramirez.simgap_daq.api.model.entity.device.Device;
import com.dasther.ndramirez.simgap_daq.api.repository.crane.CraneRepository;
import com.dasther.ndramirez.simgap_daq.api.repository.device.DeviceRepository;


@Service
public class DeviceServiceImplement implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final CraneRepository craneRepository;

    public DeviceServiceImplement(
            DeviceRepository deviceRepository,
            CraneRepository craneRepository) {
        this.deviceRepository = deviceRepository;
        this.craneRepository = craneRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceResponseDto> getAll() {
        return deviceRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceResponseDto getByName(String name) {
        return deviceRepository.findByName(name)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un dispositivo con el nombre " + name
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceResponseDto> searchByName(String text) {
        return deviceRepository
                .findByNameContainingIgnoreCaseOrderByNameAsc(text)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional
    public DeviceResponseDto createDevice(DeviceRequestDto deviceDto) {
        validateUniqueFields(deviceDto, null);
        var crane = getCraneById(deviceDto.getCraneId());
        var device = toEntity(deviceDto, crane);
        var savedDevice = deviceRepository.save(device);
        return toDto(savedDevice);
    }

    @Override
    @Transactional
    public DeviceResponseDto updateDevice(
            Long id,
            DeviceRequestDto deviceDto) {

        var device = getDeviceById(id);
        validateUniqueFields(deviceDto, id);
        var crane = getCraneById(deviceDto.getCraneId());

        updateEntity(device, deviceDto, crane);
        var updatedDevice = deviceRepository.save(device);
        return toDto(updatedDevice);
    }

    @Override
    @Transactional
    public void deleteDevice(Long id) {
        var device = getDeviceById(id);
        deviceRepository.delete(device);
    }

    private DeviceResponseDto toDto(Device device) {
        var dto = new DeviceResponseDto();
        dto.setIdDevice(device.getIdDevice());
        dto.setName(device.getName());
        dto.setAddressIp(device.getAddressIp());
        dto.setMac(device.getMac());
        dto.setRack(device.getRack());
        dto.setSlot(device.getSlot());
        dto.setCraneId(device.getCrane().getIdCrane());
        dto.setCraneName(device.getCrane().getName());
        dto.setIsOperational(device.getIsOperational());
        return dto;
    }

    private Device toEntity(DeviceRequestDto dto, Crane crane) {
        var device = new Device();
        device.setName(dto.getName());
        device.setAddressIp(dto.getAddressIp());
        device.setMac(dto.getMac().toUpperCase());
        device.setRack(dto.getRack());
        device.setSlot(dto.getSlot());
        device.setCrane(crane);
        device.setIsOperational(dto.getIsOperational());
        return device;
    }

    private void updateEntity(
            Device device,
            DeviceRequestDto dto,
            Crane crane) {

        device.setName(dto.getName());
        device.setAddressIp(dto.getAddressIp());
        device.setMac(dto.getMac().toUpperCase());
        device.setRack(dto.getRack());
        device.setSlot(dto.getSlot());
        device.setCrane(crane);
        device.setIsOperational(dto.getIsOperational());
    }

    private Device getDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un dispositivo con ID " + deviceId
                ));
    }

    private Crane getCraneById(Long craneId) {
        return craneRepository.findById(craneId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe una grúa con ID " + craneId
                ));
    }

    private void validateUniqueFields(
            DeviceRequestDto dto,
            Long currentDeviceId) {

        boolean duplicateName = currentDeviceId == null
                ? deviceRepository.existsByName(dto.getName())
                : deviceRepository.existsByNameAndIdDeviceNot(
                        dto.getName(),
                        currentDeviceId
                );

        if (duplicateName) {
            throw new DuplicateResourceException(
                    "Ya existe un dispositivo con el nombre " + dto.getName()
            );
        }

        boolean duplicateIp = currentDeviceId == null
                ? deviceRepository.existsByAddressIp(dto.getAddressIp())
                : deviceRepository.existsByAddressIpAndIdDeviceNot(
                        dto.getAddressIp(),
                        currentDeviceId
                );

        if (duplicateIp) {
            throw new DuplicateResourceException(
                    "Ya existe un dispositivo con la dirección IP "
                            + dto.getAddressIp()
            );
        }

        boolean duplicateMac = currentDeviceId == null
                ? deviceRepository.existsByMacIgnoreCase(dto.getMac())
                : deviceRepository.existsByMacIgnoreCaseAndIdDeviceNot(
                        dto.getMac(),
                        currentDeviceId
                );

        if (duplicateMac) {
            throw new DuplicateResourceException(
                    "Ya existe un dispositivo con la dirección MAC "
                            + dto.getMac()
            );
        }
    }
}
