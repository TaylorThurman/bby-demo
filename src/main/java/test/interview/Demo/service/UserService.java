package test.interview.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.interview.demo.domain.BillingRecord;
import test.interview.demo.domain.Core;
import test.interview.demo.domain.Invoice;
import test.interview.demo.repository.BillingRecordRepo;
import test.interview.demo.repository.InvoiceRepo;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final InvoiceRepo invoiceRepo;
    private final BillingRecordRepo billingRecordRepo;

    /**
     * Retrieves a list of invoices for a specific user, optionally filtered by a date range.
     *
     * @param customerNumber The unique identifier of the customer whose invoices are to be retrieved.
     * @param dateAfter      The start date of the filter range (inclusive). If null, no lower bound is applied.
     * @param dateBefore     The end date of the filter range (inclusive). If null, no upper bound is applied.
     * @return A list of {@link Invoice} objects associated with the specified customer,
     * filtered by the provided date range. If both {@code dateAfter} and {@code dateBefore}
     * are null, all invoices for the customer are returned.
     */
    public List<Invoice> getUserInvoices(int customerNumber, Date dateAfter, Date dateBefore) {
        List<Invoice> userInvoices = invoiceRepo.getAllByUserId(customerNumber);
        return filterByDateRange(userInvoices, dateAfter, dateBefore);
    }

    /**
     * Retrieves a list of billing records for a specific user, optionally filtered by a date range.
     *
     * @param customerNumber The unique identifier of the customer whose billing records are to be retrieved.
     * @param dateAfter      The start date of the filter range (inclusive). If null, no lower bound is applied.
     * @param dateBefore     The end date of the filter range (inclusive). If null, no upper bound is applied.
     * @return A list of {@link BillingRecord} objects associated with the specified customer,
     * filtered by the provided date range. If both {@code dateAfter} and {@code dateBefore}
     * are null, all billing records for the customer are returned.
     */
    public List<BillingRecord> getUserBillingRecords(int customerNumber, Date dateAfter, Date dateBefore) {
        List<BillingRecord> userBillingRecords = billingRecordRepo.getUserBillingRecords(customerNumber);
        return filterByDateRange(userBillingRecords, dateAfter, dateBefore);
    }

    /**
     * Filters a list of records by a specified date range. The records must extend the {@link Core} class,
     * which provides a `getCreatedTime` method to retrieve the creation time of each record.
     *
     * @param <T>        The type of the records in the list, which must extend {@link Core}.
     * @param records    The list of records to filter. Each record must have a creation time.
     * @param dateAfter  The start date of the filter range (inclusive). If null, no lower bound is applied.
     * @param dateBefore The end date of the filter range (inclusive). If null, no upper bound is applied.
     * @return A list of records that were created within the specified date range.
     * If both {@code dateAfter} and {@code dateBefore} are null, all records are returned.
     */
    private <T extends Core> List<T> filterByDateRange(List<T> records, Date dateAfter, Date dateBefore) {
        Stream<T> recordStream = records.stream();
        if (dateAfter != null) {
            recordStream = recordStream.filter(record -> record.getCreatedTime().after(dateAfter));
        }
        if (dateBefore != null) {
            recordStream = recordStream.filter(record -> record.getCreatedTime().before(dateBefore));
        }
        return recordStream.toList();
    }
}
