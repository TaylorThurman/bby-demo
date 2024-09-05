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

    /**
     * Retrieves a list of user records (either invoices or billing records) based on the specified type and optional date range.
     *
     * @param id         The unique identifier of the user whose records are to be retrieved.
     * @param type       The type of records to retrieve. Must be either "invoices" or "billing_records".
     * @param dateAfter  The start date of the filter range (inclusive). If null, no lower bound is applied.
     * @param dateBefore The end date of the filter range (inclusive). If null, no upper bound is applied.
     * @return A {@link ResponseEntity} containing a list of user records of the specified type, filtered by the provided date range.
     * @throws BadUserRequestException if the specified type is invalid, or if the date range is invalid (i.e., date_after is after date_before).
     */
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
