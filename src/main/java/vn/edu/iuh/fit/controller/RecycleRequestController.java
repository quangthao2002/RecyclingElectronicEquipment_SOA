package vn.edu.iuh.fit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.models.RecycleRequest;
import vn.edu.iuh.fit.models.User;
import vn.edu.iuh.fit.models.dto.QuoteRequestDto;
import vn.edu.iuh.fit.models.dto.QuoteResponseDto;
import vn.edu.iuh.fit.repository.RecycleRepository;
import vn.edu.iuh.fit.services.IRecycleRequestService;
import vn.edu.iuh.fit.services.IUserService;

import java.util.Random;

@RestController
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class RecycleRequestController {

    private final IRecycleRequestService recycleRequestService;
    private final RecycleRepository recycleRepository;
    private final IUserService userService;

    @PostMapping("/create")
    public ResponseEntity<QuoteResponseDto> createQuoteRequest(@RequestBody QuoteRequestDto quoteRequestDto) {
        double estimatedPrice = calculateEstimatedPrice(quoteRequestDto);
        return createQuoteResponse(estimatedPrice, quoteRequestDto.getModel(), "MTC " + generateProductCode());
    }

    @PostMapping("/confirm")
    public ResponseEntity<QuoteResponseDto> confirmQuoteRequest(@RequestBody QuoteRequestDto quoteRequestDto) {
        double estimatedPrice = calculateEstimatedPrice(quoteRequestDto);
        RecycleRequest quote = convertToQuoteRequest(quoteRequestDto, estimatedPrice);
        recycleRequestService.saveQuoteRequest(quote);
        return createQuoteResponse(estimatedPrice, quote.getModel(),"MTC " +  quote.getProductCode());
    }

    private double calculateEstimatedPrice(QuoteRequestDto quoteRequestDto) {
        return recycleRequestService.calculateEstimatedPrice(quoteRequestDto);
    }

    private ResponseEntity<QuoteResponseDto> createQuoteResponse(double estimatedPrice, String model, String productCode) {
        QuoteResponseDto quoteResponseDto = new QuoteResponseDto();
        quoteResponseDto.setEstimatedPrice((int) estimatedPrice);
        quoteResponseDto.setModel(model);
        quoteResponseDto.setProductCode(productCode);
        return ResponseEntity.ok(quoteResponseDto);
    }

    private RecycleRequest convertToQuoteRequest(QuoteRequestDto quoteRequestDto, double estimatedPrice) {
        RecycleRequest recycleRequest = new RecycleRequest();
        recycleRequest.setProductCode(generateProductCode());
        recycleRequest.setModel(quoteRequestDto.getModel());
        recycleRequest.setDeviceAge(quoteRequestDto.getDeviceAge());
        recycleRequest.setStatus(quoteRequestDto.getStatus());
        recycleRequest.setDamageLocation(quoteRequestDto.getDamageLocation());
        recycleRequest.setDamageDescription(quoteRequestDto.getDamageDescription());
        recycleRequest.setQuoteValue((int) estimatedPrice);
        return recycleRequest;
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

//    @PostMapping("/createForm")
//    public ResponseEntity<RecycleRequest> createRecycleRequest(@RequestBody RecycleRequest recycleRequest) {
//        User user = userService.getUserById(recycleRequest.getUser().getId());
//        if (user != null) {
//            recycleRequest.setUser(user);
//        }
//        RecycleRequest savedRecycleRequest = recycleRequestService.saveRecycleRequest(recycleRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecycleRequest);
//    }
//
//    // Step 3: Create a new GET method in RecycleRequestController
//    @GetMapping("/{id}")
//    public ResponseEntity<RecycleRequest> getRecycleRequest(@PathVariable Long id) {
//        return recycleRepository.findById(id)
//                .map(recycleRequest -> ResponseEntity.ok(recycleRequest))
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }
//
//    // Step 4: Update the PUT method in RecycleRequestController
//    @PutMapping("/{id}")
//    public ResponseEntity<RecycleRequest> updateRecycleRequest(@PathVariable Long id, @RequestBody RecycleRequest newRecycleRequest) {
//        return recycleRepository.findById(id)
//                .map(recycleRequest -> {
//                    recycleRequest.setStatus(newRecycleRequest.getStatus());
//                    recycleRequest.setQuoteValue(newRecycleRequest.getQuoteValue());
//
//                    RecycleRequest updatedRecycleRequest = recycleRequestService.saveRecycleRequest(recycleRequest);
//                    return ResponseEntity.ok(updatedRecycleRequest);
//                })
//                .orElseGet(() -> {
//                    newRecycleRequest.setId(id);
//                    RecycleRequest updatedRecycleRequest = recycleRequestService.saveRecycleRequest(newRecycleRequest);
//                    return ResponseEntity.status(HttpStatus.CREATED).body(updatedRecycleRequest);
//                });
//    }
}