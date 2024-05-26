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
import vn.edu.iuh.fit.models.dto.*;
import vn.edu.iuh.fit.services.IQuoteService;
import vn.edu.iuh.fit.services.IRecycleRequestService;
import vn.edu.iuh.fit.services.IUserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recycling")
@RequiredArgsConstructor

public class RecyclingReceiptController {

    private final IRecycleRequestService recycleRequestService;
    private final IQuoteService quoteService;
    private final IUserService userService;
    @Autowired
    private ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(RecyclingReceiptController.class);

    // Tap phieu tai che
    @PostMapping("/createRecycleReceipt")
    public ResponseEntity<?> createRecycleReceipt(@RequestBody RecyclingReceiptDto recyclingReceiptDto) {
        try {
            if (recyclingReceiptDto.getQuoteId() == null) {
                return ResponseEntity.badRequest().body("Missing required fields");
            }
            // Lấy thông tin người dùng (giả định bạn đã có phương thức xác thực)
            User user = userService.getAuthenticatedUser();
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Quote quote = quoteService.getQuoteById(recyclingReceiptDto.getQuoteId());
            if (quote == null) {
                return ResponseEntity.notFound().build();
            }
            // Kiểm tra trạng thái báo giá  nếu không phải là PENDING thì không thể tạo phiếu tái chế
            if (quote.getQuoteStatus() != QuoteStatus.PENDING) {
                return ResponseEntity.badRequest().body("Quote is not in PENDING state");
            }
            // Tạo phiếu tái chế
            RecyclingReceipt recyclingReceipt = new RecyclingReceipt();
            recyclingReceipt.setPaymentMethod(recyclingReceiptDto.getPaymentMethod());
            recyclingReceipt.setPaymentStatus(PaymentStatus.UNPAID);
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

    private boolean isValidStatusTransition(RecyclingReceiptStatus currentStatus, RecyclingReceiptStatus newStatus) {
        if (currentStatus == RecyclingReceiptStatus.CANCELLED) {
            return false; // Không thể chuyển đổi từ trạng thái Đã hủy
        }
        return switch (currentStatus) {
            case WAITING_FOR_DEVICE ->
                    newStatus == RecyclingReceiptStatus.RECEIVED; //chuyen tu trang thai cho nhan thiet bi sang trang thai nhan thiet bi
            case RECEIVED ->
                    newStatus == RecyclingReceiptStatus.ASSESSED; //chuyen tu trang thai xem xet sang trang thai danh gia
            case ASSESSED ->
                    newStatus == RecyclingReceiptStatus.PAID;//chuyen tu trang thai danh gia sang trang thai thanh toan
            case PAID ->
                    newStatus == RecyclingReceiptStatus.RECYCLING;// chuyen tu trang thai thanh toan sang trang thai tai che
            case RECYCLING ->
                    newStatus == RecyclingReceiptStatus.COMPLETED; // chuyen tu trang thai tai che sang trang thai hoan thanh
            default -> false; // Trạng thái hiện tại không hợp lệ
        };
    }
    // cap nhat trang thai va gia cuoi cung cho phiếu tái chế
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
            if (updateDto.getRecyclingReceiptStatus() == RecyclingReceiptStatus.ASSESSED) {// chuyển sang trạng thái dánh giá thì cập nhật giá cuối cùng
                quote.setQuoteStatus(QuoteStatus.ACCEPTED);
                quote.setFinalQuotePrice(updateDto.getFinalQuotePrice()); // Cập nhật giá cuối cùng
            } else if (updateDto.getRecyclingReceiptStatus() == RecyclingReceiptStatus.PAID) {
                quote.setQuoteStatus(QuoteStatus.PAID);
                receipt.setPaymentStatus(PaymentStatus.PAID);
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
        dto.setDevice(modelMapper.map(receipt.getQuote().getDevice(), DeviceDto.class));

        // Ánh xạ estimatedPrice và model
        Quote quote = receipt.getQuote();
        dto.getQuote().setEstimatedPrice((int) quote.getFirstQuotePrice());
        dto.getQuote().setModel(quote.getDevice().getModel());

        return dto;
    }

//    Nếu quoteStatus là CONFIRMED và recyclingReceiptStatus là WAITING_FOR_DEVICE ( bên công ty tái chế đã xác nhận báo giá và đang chờ thiết bị được gửi đến),
//    khách hàng sẽ biết rằng báo giá của họ đã được xác nhận và đang chờ họ gửi thiết bị đến công ty tái chế.

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

    @GetMapping("/recyclingReceipts")
    public ResponseEntity<List<StaffRecyclingReceiptDto>> getAllRecyclingReceipts() {
        try {
            List<RecyclingReceipt> receipts = recycleRequestService.getAllRecyclingReceipts();
            List<StaffRecyclingReceiptDto> receiptDtos = receipts.stream()
                    .map(this::convertToStaffDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(receiptDtos);
        } catch (Exception e) {
            log.error("Error getting all recycling receipts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//get all đang xử lí
    @GetMapping("/processing")
    public ResponseEntity<List<RecyclingReceiptDto>> getProcessingRecyclingReceipts() {
        try {
            List<RecyclingReceipt> receipts = recycleRequestService.findByRecyclingReceiptStatusIn(List.of(RecyclingReceiptStatus.RECEIVED, RecyclingReceiptStatus.ASSESSED));
            List<RecyclingReceiptDto> receiptDtos = receipts.stream()
                    .map(receipt -> modelMapper.map(receipt, RecyclingReceiptDto.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(receiptDtos);
        } catch (Exception e) {
            log.error("Error getting processing recycling receipts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/report")
    public ResponseEntity<List<RecyclingReceiptDto>> getReportByStatus() {
        try {
            List<RecyclingReceipt> receipts = recycleRequestService.findByRecyclingReceiptStatusIn(List.of(RecyclingReceiptStatus.RECYCLING, RecyclingReceiptStatus.COMPLETED, RecyclingReceiptStatus.CANCELLED));
            List<RecyclingReceiptDto> receiptDtos = receipts.stream()
                    .map(receipt -> modelMapper.map(receipt, RecyclingReceiptDto.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(receiptDtos);
        } catch (Exception e) {
            log.error("Error getting processing recycling receipts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    	get all đang thanh toán
    @GetMapping("/paid")
    public  ResponseEntity<List<RecyclingReceiptDto>> getRecyclingByStatusPaid(){
        try {
            List<RecyclingReceipt> receipts = recycleRequestService.findByRecyclingReceiptsByStatus(RecyclingReceiptStatus.PAID);
            List<RecyclingReceiptDto> receiptDtos = receipts.stream()
                    .map(receipt -> modelMapper.map(receipt, RecyclingReceiptDto.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(receiptDtos);
        } catch (Exception e) {
            log.error("Error getting processing recycling receipts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}