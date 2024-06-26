package vn.edu.iuh.fit.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.models.*;
import vn.edu.iuh.fit.models.dto.DeviceRequestDto;
import vn.edu.iuh.fit.models.dto.QuoteDto;
import vn.edu.iuh.fit.models.dto.QuoteResponseDto;
import vn.edu.iuh.fit.services.IDeviceService;
import vn.edu.iuh.fit.services.IQuoteService;
import vn.edu.iuh.fit.services.IRecycleRequestService;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {
    private final IDeviceService deviceService;
    private final IQuoteService quoteService;
    private final IRecycleRequestService recycleRequestService;
    private static final Logger log = LoggerFactory.getLogger(RecyclingReceiptController.class);
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<QuoteResponseDto> createQuoteRequest(@RequestBody DeviceRequestDto deviceRequestDto) {
        try {
            double estimatedPrice = calculateEstimatedPrice(deviceRequestDto);
            Quote quote = convertToQuote(deviceRequestDto, estimatedPrice);
            quote.setQuoteStatus(QuoteStatus.PENDING); // Cập nhật trạng thái báo giá

            QuoteResponseDto quoteResponseDto = modelMapper.map(quote, QuoteResponseDto.class);
            quoteResponseDto.setEstimatedPrice((int) quote.getFirstQuotePrice());
            quoteResponseDto.setCreatedAt(quote.getCreatedAt());
            quoteResponseDto.setQuoteStatus(quote.getQuoteStatus());
            quoteResponseDto.setModel(deviceRequestDto.getModel());
            quoteResponseDto.setProductCode(quote.getProductCode());
            quoteResponseDto.setDeviceType(deviceRequestDto.getDeviceType());
            quoteResponseDto.setDeviceId(quote.getDevice().getDeviceId());
            return ResponseEntity.ok(quoteResponseDto);
        } catch (Exception e) {
            log.error("Error confirming quote request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // xác nhận với mức giá ước lượng
    @PostMapping("/confirm")
    public ResponseEntity<QuoteResponseDto> confirmQuoteRequest(@RequestBody DeviceRequestDto deviceRequestDto) {
        try {
            double estimatedPrice = calculateEstimatedPrice(deviceRequestDto);
            Quote quote = convertToQuote(deviceRequestDto, estimatedPrice);
            quote.setQuoteStatus(QuoteStatus.PENDING); // Cập nhật trạng thái báo giá
            quoteService.saveQuote(quote); // Lưu quote và recyclingReceipt (cascade)

            QuoteResponseDto quoteResponseDto = modelMapper.map(quote, QuoteResponseDto.class);
            quoteResponseDto.setEstimatedPrice((int) quote.getFirstQuotePrice());
            quoteResponseDto.setCreatedAt(quote.getCreatedAt());
            quoteResponseDto.setQuoteStatus(quote.getQuoteStatus());
            quoteResponseDto.setModel(deviceRequestDto.getModel());
            quoteResponseDto.setProductCode(quote.getProductCode());
            quoteResponseDto.setDeviceType(deviceRequestDto.getDeviceType());
            quoteResponseDto.setDeviceId(quote.getDevice().getDeviceId());
            return ResponseEntity.ok(quoteResponseDto);
        } catch (Exception e) {
            log.error("Error confirming quote request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private double calculateEstimatedPrice(DeviceRequestDto deviceRequestDto) {
        return recycleRequestService.calculateEstimatedPrice(deviceRequestDto);
    }

    private ResponseEntity<QuoteResponseDto> createQuoteResponse(double estimatedPrice, String model, String productCode) {
        QuoteResponseDto quoteResponseDto = new QuoteResponseDto();
        quoteResponseDto.setEstimatedPrice((int) estimatedPrice);
        quoteResponseDto.setModel(model);
        quoteResponseDto.setProductCode(productCode);
        return ResponseEntity.ok(quoteResponseDto);
    }

    private Quote convertToQuote(DeviceRequestDto deviceRequestDto, double estimatedPrice) {
        Quote quote = new Quote();
        quote.setProductCode(generateProductCode());
        quote.setFirstQuotePrice(estimatedPrice);
        quote.setQuoteStatus(QuoteStatus.PENDING);
        Device device = new Device();
        device.setModel(deviceRequestDto.getModel());
        device.setDeviceAge(deviceRequestDto.getDeviceAge());
        device.setDeviceStatus(deviceRequestDto.getDeviceStatus());
        device.setDamageLocation(deviceRequestDto.getDamageLocation());
        device.setDamageDescription(deviceRequestDto.getDamageDescription());
        device.setDeviceType(deviceRequestDto.getDeviceType());
        deviceService.saveDevice(device);
        quote.setDevice(device);

        return quote;
    }

    private String generateProductCode() {
        String characters = "0123456789";
        StringBuilder productCode = new StringBuilder();
        Random rnd = new Random();
        while (productCode.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * characters.length());
            productCode.append(characters.charAt(index));
        }
        return productCode.toString();
    }

    //  lấy báo giá theo id
    @GetMapping("/quote/{id}")
    public ResponseEntity<QuoteDto> getQuote(@PathVariable Long id) {
        try {
            Quote quote = quoteService.getQuoteById(id);
            if (quote == null) {
                return ResponseEntity.notFound().build();
            }
            QuoteDto quoteDTO = modelMapper.map(quote, QuoteDto.class);
            if (quote.getRecyclingReceipt() != null) {
                quoteDTO.setRecyclingReceiptStatus(quote.getRecyclingReceipt().getRecyclingReceiptStatus());
            }
            return ResponseEntity.ok(quoteDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // lấy danh sách các báo giá  của khách hàng
    @GetMapping("/users/{userId}/quotes")
    public ResponseEntity<List<QuoteResponseDto>> getQuotesByUserId(@PathVariable Long userId) {
        try {
            List<Quote> quotes = quoteService.findByRecyclingReceiptUserId(userId);
            List<QuoteResponseDto> quoteResponseDtos = quotes.stream()
                    .map(quote -> {
                        QuoteResponseDto dto = modelMapper.map(quote, QuoteResponseDto.class);
                        // Ánh xạ giá ước tính ban đầu
                        dto.setEstimatedPrice((int) quote.getFirstQuotePrice());

                        if (quote.getRecyclingReceipt() != null) {
                            dto.setRecyclingReceiptStatus(quote.getRecyclingReceipt().getRecyclingReceiptStatus());
                        }
                        // Ánh xạ tên model
                        if (quote.getDevice() != null) {
                            dto.setModel(quote.getDevice().getModel());
                        }
                        // Ánh xạ giá cuối cùng
                        dto.setFinalQuotePrice(quote.getFinalQuotePrice());
                        return dto;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(quoteResponseDtos);
        } catch (Exception e) {
            log.error("Error getting quotes by user ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // xem chi tiết báo giá
    @GetMapping("/{quoteId}")
    public ResponseEntity<QuoteResponseDto> getQuoteById(@PathVariable Long quoteId) {
        try {
            Quote quote = quoteService.getQuoteById(quoteId);
            if (quote == null) {
                return ResponseEntity.notFound().build();
            }

            QuoteResponseDto quoteResponseDto = modelMapper.map(quote, QuoteResponseDto.class);
            quoteResponseDto.setEstimatedPrice((int) quote.getFirstQuotePrice());
            quoteResponseDto.setModel(quote.getDevice().getModel());
            if (quote.getRecyclingReceipt() != null) {
                quoteResponseDto.setRecyclingReceiptStatus(quote.getRecyclingReceipt().getRecyclingReceiptStatus());
            }

            return ResponseEntity.ok(quoteResponseDto);
        } catch (Exception e) {
            log.error("Error getting quote by ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Hủy báo giá
    @PutMapping("/{quoteId}/cancel")
    public ResponseEntity<String> cancelQuote(@PathVariable Long quoteId) {
        try {
            Quote quote = quoteService.getQuoteById(quoteId);
            if (quote == null) {
                return ResponseEntity.notFound().build();
            }

            // Kiểm tra trạng thái báo giá chi tiết hơn
            QuoteStatus currentStatus = quote.getQuoteStatus();
            if (!Set.of(QuoteStatus.PENDING,QuoteStatus.ACCEPTED,QuoteStatus.CONFIRMED).contains(currentStatus)) {
                return ResponseEntity.badRequest().body("Khong the huy bao gia voi trang thai hien tai: " + currentStatus);
            }

            // Kiểm tra và hủy phiếu thu hồi (nếu có)
            RecyclingReceipt receipt = quote.getRecyclingReceipt();
            if (receipt != null) {
                RecyclingReceiptStatus receiptStatus = receipt.getRecyclingReceiptStatus();
                if (!Set.of(RecyclingReceiptStatus.WAITING_FOR_DEVICE, RecyclingReceiptStatus.RECEIVED,
                        RecyclingReceiptStatus.ASSESSED).contains(receiptStatus)) {
                    return ResponseEntity.badRequest().body("Khong the huy phieu tai che o trang thai hien tai: " + receiptStatus);
                }

                receipt.setRecyclingReceiptStatus(RecyclingReceiptStatus.CANCELLED);
                receipt.setPaymentStatus(PaymentStatus.CANCELLED);
                recycleRequestService.saveRecyclingReceipt(receipt);
            }

            // Đánh dấu báo giá là đã hủy
            quote.setQuoteStatus(QuoteStatus.CANCELLED);
            quoteService.saveQuote(quote);

            return ResponseEntity.ok("Đã hủy báo giá thành công");
        } catch (Exception e) {
            log.error("Lỗi khi hủy báo giá", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi hủy báo giá");
        }
    }
    }
