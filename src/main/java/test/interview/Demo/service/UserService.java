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

    public List<Invoice> getUserInvoices(int customerNumber, Date dateAfter, Date dateBefore) {
        List<Invoice> userInvoices = invoiceRepo.getAllByUserId(customerNumber);
        return filterByDateRange(userInvoices, dateAfter, dateBefore);
    }

    public List<BillingRecord> getUserBillingRecords(int customerNumber, Date dateAfter, Date dateBefore) {
        List<BillingRecord> userBillingRecords = billingRecordRepo.getUserBillingRecords(customerNumber);
        return filterByDateRange(userBillingRecords, dateAfter, dateBefore);
    }

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
