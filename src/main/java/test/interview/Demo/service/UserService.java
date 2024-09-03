package test.interview.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.interview.demo.domain.Invoice;
import test.interview.demo.repository.BillingRecordRepo;
import test.interview.demo.repository.InvoiceRepo;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final InvoiceRepo invoiceRepo;
    private final BillingRecordRepo billingRecordRepo;

    public List<Invoice> getUserInvoices(int customerNumber, Date dateAfter, Date dateBefore) {
        return invoiceRepo.getByCustomerNumberAndDate(customerNumber, dateAfter, dateBefore);
    }
}
