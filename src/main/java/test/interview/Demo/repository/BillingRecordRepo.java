package test.interview.demo.repository;

import org.springframework.stereotype.Service;
import test.interview.demo.domain.BillingRecord;
import test.interview.demo.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class BillingRecordRepo {

    /**
     * Retrieves a list of billing records associated with a specific user.
     *
     * @param customerNumber The unique identifier of the customer whose billing records are to be retrieved.
     * @return A list of {@link BillingRecord} objects associated with the specified customer number.
     * @throws ResourceNotFoundException if no billing records are found for the specified customer number.
     */
    public List<BillingRecord> getUserBillingRecords(int customerNumber) throws ResourceNotFoundException {
        List<BillingRecord> userBillingRecords = FakeDB.allBillingRecords.stream().filter(record -> record.getUser().getCustomerNumber() == customerNumber).toList();
        if (userBillingRecords.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No billing records found for user: %s", customerNumber));
        }
        return userBillingRecords;
    }

}