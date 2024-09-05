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

    @GetMapping("/{id}/records")
    public ResponseEntity<List<?>> getUserRecords(@PathVariable("id") int id,
                                                  @RequestParam("type") String type,
                                                  @RequestParam(required = false, value = "date_after") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateAfter,
                                                  @RequestParam(required = false, value = "date_before") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateBefore) throws BadUserRequestException {
        if (dateAfter != null && dateBefore != null && dateAfter.after(dateBefore)) {
            throw new BadUserRequestException("date_after cannot be later than date_before");
        }

        List<?> records = switch (type.toLowerCase()) {
            case "invoices" -> userService.getUserInvoices(id, dateAfter, dateBefore);
            case "billing_records" -> userService.getUserBillingRecords(id, dateAfter, dateBefore);
            default ->
                    throw new BadUserRequestException("Invalid type specified. Use 'invoices' or 'billing_records'.");
        };

        return ResponseEntity.ok(records);
    }
}
