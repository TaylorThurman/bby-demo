package test.interview.demo.repository;

import org.springframework.stereotype.Service;
import test.interview.demo.domain.Invoice;
import test.interview.demo.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service  // not using @Repository because it isn't a real repository
public class InvoiceRepo {

    /**
     * Retrieves an invoice by its unique identifier.
     *
     * @param id The unique identifier (UUID) of the invoice to be retrieved.
     * @return The {@link Invoice} object associated with the specified ID, or {@code null} if no invoice is found.
     */
    public Invoice getById(UUID id) {
        return FakeDB.idToInvoiceMap.get(id);
    }

    /**
     * Retrieves a list of invoices associated with a specific user.
     *
     * @param userId The unique identifier of the user whose invoices are to be retrieved.
     * @return A list of {@link Invoice} objects associated with the specified user ID.
     * @throws ResourceNotFoundException if no invoices are found for the specified user ID.
     */
    public List<Invoice> getAllByUserId(int userId) {
        List<Invoice> userInvoices = FakeDB.idToInvoiceMap.values().stream()
                .filter(i -> i.getUser().getCustomerNumber() == userId)
                .toList();

        if (userInvoices.isEmpty())
            throw new ResourceNotFoundException(String.format("User not found with id %s", userId));

        return userInvoices;
    }
}