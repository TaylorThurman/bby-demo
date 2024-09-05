package test.interview.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.interview.demo.domain.BillingRecord;
import test.interview.demo.domain.Invoice;
import test.interview.demo.exception.BadUserRequestException;
import test.interview.demo.service.UserService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}/invoices")
    public ResponseEntity<List<Invoice>> getUserInvoices(@PathVariable("id") int id,
                                                         @RequestParam(required = false, value = "date_after") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateAfter,
                                                         @RequestParam(required = false, value = "date_before") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateBefore) throws BadUserRequestException {
        if (dateAfter != null && dateBefore != null && dateAfter.after(dateBefore)) {
            throw new BadUserRequestException("date_after cannot be later than date_before");
        }

        return ResponseEntity.ok(userService.getUserInvoices(id, dateAfter, dateBefore));
    }

    @GetMapping("/{id}/billing_records")
    public ResponseEntity<List<BillingRecord>> getUserBillingRecords(@PathVariable("id") int id,
                                                                     @RequestParam(required = false, value = "date_after") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateAfter,
                                                                     @RequestParam(required = false, value = "date_before") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateBefore) throws BadUserRequestException {
        if (dateAfter != null && dateBefore != null && dateAfter.after(dateBefore)) {
            throw new BadUserRequestException("date_after cannot be later than date_before");
        }

        return ResponseEntity.ok(userService.getUserBillingRecords(id, dateAfter, dateBefore));
    }
}
