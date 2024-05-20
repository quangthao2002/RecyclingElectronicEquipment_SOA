package vn.edu.iuh.fit.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.models.Device;
import vn.edu.iuh.fit.models.dto.DeviceDto;
import vn.edu.iuh.fit.services.IDeviceService;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final IDeviceService deviceService;
    private static final Logger log = LoggerFactory.getLogger(RecyclingReceiptController.class);
    @Autowired
    private ModelMapper modelMapper;
    // danh gia  lai thiet bi
    @PutMapping("/devices/{deviceId}")
    public ResponseEntity<DeviceDto> updateDevice(
            @PathVariable Long deviceId,
            @RequestBody DeviceDto deviceDto
    ) {
        try {
            Device existingDevice = deviceService.getDeviceById(deviceId);
            if (existingDevice == null) {
                return ResponseEntity.notFound().build();
            }
            existingDevice.setDamageLocation(deviceDto.getDamageLocation());
            existingDevice.setDamageDescription(deviceDto.getDamageDescription());

            Device updatedDevice = deviceService.saveDevice(existingDevice);
            return ResponseEntity.ok(modelMapper.map(updatedDevice, DeviceDto.class));
        } catch (Exception e) {
            log.error("Error updating device", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
