package test.interview.demo.repository;

import org.springframework.stereotype.Service;
import test.interview.demo.domain.Invoice;
import test.interview.demo.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service  // not using @Repository because it isn't a real repository
public class InvoiceRepo {

    public Invoice getById(UUID id) {
        return FakeDB.idToInvoiceMap.get(id);
    }

    public List<Invoice> getAllByUserId(int userId) {
        List<Invoice> userInvoices = FakeDB.idToInvoiceMap.values().stream()
                .filter(i -> i.getUser().getCustomerNumber() == userId)
                .toList();

        if (userInvoices.isEmpty())
            throw new ResourceNotFoundException(String.format("User not found with id %s", userId));

        return userInvoices;
    }
}