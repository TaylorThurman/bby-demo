package test.interview.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.interview.demo.domain.Invoice;
import test.interview.demo.exception.ResourceNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service  // not using @Repository because it isn't a real repository
@RequiredArgsConstructor
public class InvoiceRepo {

    public Invoice getById(UUID id) {
        return FakeDB.idToInvoiceMap.get(id);
    }

    public List<Invoice> getByCustomerNumberAndDate(int userId, Date dateAfter, Date dateBefore) {
        List<Invoice> userInvoices = getAllByUserId(userId);

        Stream<Invoice> userInvoicesStream = userInvoices.stream();
        if (dateAfter != null) {
            userInvoicesStream = userInvoicesStream.filter(invoice -> invoice.getCreatedTime().after(dateAfter));
        }
        if (dateBefore != null) {
            userInvoicesStream = userInvoicesStream.filter(invoice -> invoice.getCreatedTime().before(dateBefore));
        }

        return userInvoicesStream.toList();
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