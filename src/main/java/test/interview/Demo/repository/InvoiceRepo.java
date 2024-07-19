package test.interview.demo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.interview.demo.domain.Invoice;

import java.util.UUID;

@Service  // not using @Repository because it isn't a real repository
@RequiredArgsConstructor
public class InvoiceRepo {

    public Invoice getById(UUID id) {
        return FakeDB.idToInvoiceMap.get(id);
    }


}