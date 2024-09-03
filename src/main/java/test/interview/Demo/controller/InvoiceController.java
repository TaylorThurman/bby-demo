package test.interview.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.interview.demo.domain.Invoice;
import test.interview.demo.exception.BadUserRequestException;
import test.interview.demo.service.InvoiceService;

import java.util.UUID;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable String id) {
        UUID invoiceId;
        try {
            invoiceId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new BadUserRequestException("ID is not a valid UUID");
        }
        return ResponseEntity.ok(invoiceService.getInvoice(invoiceId));
    }
}
