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

    /**
     * Retrieves an invoice by its unique identifier and filters out any older duplicate billing records.
     *
     * @param id The unique identifier of the invoice to be retrieved.
     * @return The {@link Invoice} object associated with the specified ID, with older duplicate billing records filtered out.
     * @throws ResourceNotFoundException if no invoice is found with the specified ID.
     */
    public Invoice getInvoice(UUID id) throws ResourceNotFoundException {
        Invoice invoice = Optional.ofNullable(invoiceRepo.getById(id))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Invoice not found with id: %s", id)));
        return filterOlderDuplicateBillingRecords(invoice);
    }

    /**
     * Filters out older duplicate billing records from an invoice, keeping only the most recent record for each unique billing record ID.
     *
     * @param invoice The {@link Invoice} object to be processed.
     * @return The processed {@link Invoice} object with older duplicate billing records removed.
     *         If the invoice has no billing records or only one billing record, the original invoice is returned unchanged.
     */
    private Invoice filterOlderDuplicateBillingRecords(Invoice invoice) {
        if (invoice.getBillingRecords() == null || invoice.getBillingRecords().size() <= 1) return invoice;

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
