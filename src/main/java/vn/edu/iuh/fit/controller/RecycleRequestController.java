package vn.edu.iuh.fit.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.exceptions.InvalidStatusTransitionException;
import vn.edu.iuh.fit.models.*;
import vn.edu.iuh.fit.models.dto.*;
import vn.edu.iuh.fit.services.IDeviceService;
import vn.edu.iuh.fit.services.IQuoteService;
import vn.edu.iuh.fit.services.IRecycleRequestService;
import vn.edu.iuh.fit.services.IUserService;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor

public class RecycleRequestController {

    private final IRecycleRequestService recycleRequestService;
    private final IDeviceService deviceService;
    private final IQuoteService quoteService;
    private final IUserService userService;
    @Autowired
    private ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(RecycleRequestController.class);

    @PostMapping("/create")
    public ResponseEntity<QuoteResponseDto> createQuoteRequest(@RequestBody DeviceRequestDto quoteRequestDto) {
        double estimatedPrice = calculateEstimatedPrice(quoteRequestDto);
        return createQuoteResponse(estimatedPrice, quoteRequestDto.getModel(), "MTC " + generateProductCode());
    }

    @PostMapping("/confirm")
    public ResponseEntity<QuoteResponseDto> confirmQuoteRequest(@RequestBody DeviceRequestDto deviceRequestDto) {
        try {
            double estimatedPrice = calculateEstimatedPrice(deviceRequestDto);
            Quote quote = convertToQuote(deviceRequestDto, estimatedPrice);

            // Lấy thông tin người dùng đã xác thực
            // User user = userService.getAuthenticatedUser();

            quote.setQuoteStatus(QuoteStatus.CONFIRMED); // Cập nhật trạng thái báo giá
            quoteService.saveQuote(quote); // Lưu quote và recyclingReceipt (cascade)

            QuoteResponseDto quoteResponseDto = modelMapper.map(quote, QuoteResponseDto.class);
            quoteResponseDto.setEstimatedPrice((int) quote.getFirstQuotePrice());
            quoteResponseDto.setCreatedAt(quote.getCreatedAt());
            quoteResponseDto.setQuoteStatus(quote.getQuoteStatus());
            quoteResponseDto.setModel(deviceRequestDto.getModel());
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

    // Tap phieu tai che
    @PostMapping("/createRecycleReceipt")
    public ResponseEntity<?> createRecycleReceipt(@RequestBody RecyclingReceiptDto recyclingReceiptDto) {
        try {
            // Kiểm tra đầu vào
            if (recyclingReceiptDto.getQuoteId() == null) {
                return ResponseEntity.badRequest().body("Missing required fields");
            }

            // Lấy thông tin người dùng (giả định bạn đã có phương thức xác thực)
            User user = userService.getAuthenticatedUser();
            System.out.println("User"+user);

            // Lấy thông tin báo giá
            Quote quote = quoteService.getQuoteById(recyclingReceiptDto.getQuoteId());
            if (quote == null) {
                return ResponseEntity.notFound().build();
            }

            // Kiểm tra trạng thái báo giá  nếu không phải là CONFIRMED thì không thể tạo phiếu tái chế
            if (quote.getQuoteStatus() != QuoteStatus.CONFIRMED) {
                return ResponseEntity.badRequest().body("Quote is not in CONFIRMED state");
            }

            // Tạo phiếu tái chế
            RecyclingReceipt recyclingReceipt = new RecyclingReceipt();
            recyclingReceipt.setPaymentMethod(recyclingReceiptDto.getPaymentMethod());
            recyclingReceipt.setPaymentStatus(recyclingReceiptDto.getPaymentStatus());
            recyclingReceipt.setRecyclingReceiptStatus(RecyclingReceiptStatus.WAITING_FOR_DEVICE); // Trạng thái ban đầu
            recyclingReceipt.setUser(user);
            recyclingReceipt.setQuote(quote);

            // Lưu phiếu tái chế
            recycleRequestService.saveQuoteRequest(recyclingReceipt);

            // Cập nhật trạng thái báo giá thành PENDING
            quote.setQuoteStatus(QuoteStatus.PENDING);
            quoteService.saveQuote(quote);

            return ResponseEntity.status(HttpStatus.CREATED).body("Recycling Receipt created successfully");
        } catch (Exception e) {
            log.error("Error creating recycling receipt", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating recycling receipt");
        }
    }




    // Cập nhật giá cuối cùng cho báo giá
//    @PutMapping("/quotes/{quoteId}")
//    public ResponseEntity<QuoteResponseDto> updateQuotePriceFinal(@PathVariable Long quoteId, @RequestBody QuoteUpdateDto quoteUpdateDto) {
//        try {
//            Quote quote = quoteService.getQuoteById(quoteId);
//            if (quote == null) {
//                return ResponseEntity.notFound().build();
//            }
//            // Cập nhật thông tin báo giá
//            quote.setFinalQuotePrice(quoteUpdateDto.getFinalQuotePrice());
//            quote.setQuoteStatus(quoteUpdateDto.getQuoteStatus());
//
//            Quote updatedQuote = quoteService.saveQuote(quote);
//            return ResponseEntity.ok(modelMapper.map(updatedQuote, QuoteResponseDto.class));
//        } catch (Exception e) {
//            log.error("Error updating quote", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//  lấy báo giá theo id
    @GetMapping("/quote/{id}")
    public ResponseEntity<QuoteDTO> getQuote(@PathVariable Long id) {
        try {
            Quote quote = quoteService.getQuoteById(id);
            if (quote == null) {
                return ResponseEntity.notFound().build();
            }
            QuoteDTO quoteDTO = modelMapper.map(quote, QuoteDTO.class);
            return ResponseEntity.ok(quoteDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    private boolean isValidStatusTransition(RecyclingReceiptStatus currentStatus, RecyclingReceiptStatus newStatus) {
        // Xác định các quy tắc chuyển đổi trạng thái ở đây
        if (currentStatus == RecyclingReceiptStatus.CANCELLED) {
            return false; // Không thể chuyển đổi từ trạng thái Đã hủy
        }

        return switch (currentStatus) {
            case WAITING_FOR_DEVICE -> newStatus == RecyclingReceiptStatus.RECEIVED; //chuyen tu trang thai cho nhan thiet bi sang trang thai nhan thiet bi
            case RECEIVED -> newStatus == RecyclingReceiptStatus.REVIEWING; //chuyen tu trang thai nhan thiet bi sang trang thai xem xet
            case REVIEWING -> newStatus == RecyclingReceiptStatus.ASSESSED; //chuyen tu trang thai xem xet sang trang thai danh gia
            case ASSESSED -> newStatus == RecyclingReceiptStatus.PAID ;//chuyen tu trang thai danh gia sang trang thai thanh toan hoac huy
            case PAID -> newStatus == RecyclingReceiptStatus.RECYCLING;// chuyen tu trang thai thanh toan sang trang thai tai che
            case RECYCLING -> newStatus == RecyclingReceiptStatus.COMPLETED; // chuyen tu trang thai tai che sang trang thai hoan thanh
            default -> false; // Trạng thái hiện tại không hợp lệ
        };
    }
// cap nhat trang thai cua phieu tai che
    @PutMapping("/recyclingReceipts/{receiptId}")
    public ResponseEntity<String> updateRecyclingReceipt(
            @PathVariable Long receiptId,
            @RequestBody RecyclingReceiptUpdateDto updateDto) {
        try {
            RecyclingReceipt receipt = recycleRequestService.getRecycleRequestById(receiptId);
            if (receipt == null) {
                return ResponseEntity.notFound().build();
            }
            Quote quote = receipt.getQuote();

            // Validate transition from the current status
            if (!isValidStatusTransition(receipt.getRecyclingReceiptStatus(), updateDto.getRecyclingReceiptStatus())) {
                return ResponseEntity.badRequest().body("Invalid status transition: Cannot transition from " + receipt.getRecyclingReceiptStatus() + " to " + updateDto.getRecyclingReceiptStatus());
            }

            // Update quote status based on recycling receipt status

            if (updateDto.getRecyclingReceiptStatus() == RecyclingReceiptStatus.ASSESSED) {
                quote.setQuoteStatus(QuoteStatus.ACCEPTED);
                quote.setFinalQuotePrice(updateDto.getFinalQuotePrice()); // Cập nhật giá cuối cùng
            } else if (updateDto.getRecyclingReceiptStatus() == RecyclingReceiptStatus.PAID) {
                quote.setQuoteStatus(QuoteStatus.CONFIRMED);
            }

            // Update recycling receipt status
            receipt.setRecyclingReceiptStatus(updateDto.getRecyclingReceiptStatus());

            // Save the updated quote and recycling receipt
            quoteService.saveQuote(quote);
            recycleRequestService.saveRecyclingReceipt(receipt);
            return ResponseEntity.ok("Recycling receipt updated successfully");
        } catch (Exception e) {
            log.error("Error updating recycling receipt", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private StaffRecyclingReceiptDto convertToStaffDto(RecyclingReceipt receipt) {
        StaffRecyclingReceiptDto dto = modelMapper.map(receipt, StaffRecyclingReceiptDto.class);
        dto.setQuote(modelMapper.map(receipt.getQuote(), QuoteResponseDto.class));
//        dto.setUser(modelMapper.map(receipt.getUser(), UserDto.class));
        dto.setDevice(modelMapper.map(receipt.getQuote().getDevice(), DeviceDTO.class));

        // Ánh xạ estimatedPrice và model
        Quote quote = receipt.getQuote();
        dto.getQuote().setEstimatedPrice((int) quote.getFirstQuotePrice());
        dto.getQuote().setModel(quote.getDevice().getModel());

        return dto;
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
                        dto.setEstimatedPrice((int)quote.getFirstQuotePrice());
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
            // Kiểm tra trạng thái báo giá trước khi hủy (ví dụ: chỉ cho phép hủy khi đang chờ)
            if (quote.getQuoteStatus() != QuoteStatus.CONFIRMED) {
                return ResponseEntity.badRequest().body("Quote cannot be cancelled in its current state");
            }
            quote.setQuoteStatus(QuoteStatus.CANCELLED);
            quoteService.saveQuote(quote);
            return ResponseEntity.ok("Quote cancelled successfully");
        } catch (Exception e) {
            log.error("Error cancelling quote", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error cancelling quote");
        }
    }
    // Xem danh sách đang trong trạng thái cần xử lý
    @GetMapping("/staff/recyclingReceipts/pending")
    public ResponseEntity<List<StaffRecyclingReceiptDto>> getPendingRecyclingReceipts() {
        try {
            List<RecyclingReceipt> receipts = recycleRequestService.getRecyclingReceiptsByStatus(RecyclingReceiptStatus.WAITING_FOR_DEVICE);
            List<StaffRecyclingReceiptDto> receiptDtos = receipts.stream()
                    .map(this::convertToStaffDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(receiptDtos);
        } catch (Exception e) {
            log.error("Error getting pending recycling receipts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // lấy phiếu tái chế theo id
    @GetMapping("/staff/recyclingReceipts/{receiptId}")
    public ResponseEntity<StaffRecyclingReceiptDto> getRecyclingReceiptById(@PathVariable Long receiptId) {
        try {
            RecyclingReceipt receipt = recycleRequestService.getRecycleRequestById(receiptId);
            if (receipt == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(convertToStaffDto(receipt));
        } catch (Exception e) {
            log.error("Error getting recycling receipt by ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}