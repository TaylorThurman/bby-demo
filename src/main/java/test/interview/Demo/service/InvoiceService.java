package test.interview.demo.service;

import test.interview.demo.domain.BillingRecord;
import test.interview.demo.domain.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.interview.demo.exception.ResourceNotFoundException;
import test.interview.demo.repository.InvoiceRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepo invoiceRepo;

    public Invoice getInvoice(UUID id) throws ResourceNotFoundException {
        Invoice invoice = Optional.ofNullable(invoiceRepo.getById(id))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Invoice not found with id: %s", id)));
        return filterOlderDuplicateBillingRecords(invoice);
    }

    /**
     * Filters out any billing records that have duplicate ID's and only returns the record with the most recent date.
     * @param invoice Invoice retrieved from the database.
     * @return Invoice with filtered billing records.
     */
    private Invoice filterOlderDuplicateBillingRecords(Invoice invoice) {
        Map<UUID, BillingRecord> rMap = new HashMap<>();

        for (BillingRecord billingRecord : invoice.getBillingRecords()) {
            if (rMap.containsKey(billingRecord.getId())) {
                BillingRecord existingRecord = rMap.get(billingRecord.getId());
                if (billingRecord.getCreatedTime().after(existingRecord.getCreatedTime())) {
                    rMap.put(billingRecord.getId(), billingRecord);
                }
            } else {
                rMap.put(billingRecord.getId(), billingRecord);
            }
        }

        invoice.setBillingRecords(rMap.values().stream().toList());
        return invoice;
    }
}
