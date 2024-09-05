package test.interview.demo.repository;

import org.springframework.stereotype.Service;
import test.interview.demo.domain.BillingRecord;
import test.interview.demo.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class BillingRecordRepo {

    public List<BillingRecord> getUserBillingRecords(int customerNumber) throws ResourceNotFoundException {
        List<BillingRecord> userBillingRecords = FakeDB.allBillingRecords.stream().filter(record -> record.getUser().getCustomerNumber() == customerNumber).toList();
        if (userBillingRecords.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No billing records found for user: %s", customerNumber));
        }
        return userBillingRecords;
    }

}