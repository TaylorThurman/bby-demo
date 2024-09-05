package test.interview.demo.repository;

import org.springframework.stereotype.Service;
import test.interview.demo.domain.BillingRecord;
import test.interview.demo.exception.ResourceNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BillingRecordRepo {

    public List<BillingRecord> getByCustomerNumberAndDate(int customerNumber, Date dateAfter, Date dateBefore) {
        List<BillingRecord> usersBillingRecords = getUserBillingRecords(customerNumber);
       Stream<BillingRecord> userBillingRecordStream = usersBillingRecords.stream();
        if (dateAfter != null) {
            userBillingRecordStream = userBillingRecordStream.filter(record -> record.getCreatedTime().after(dateAfter));
        }
        if (dateBefore != null) {
            userBillingRecordStream = userBillingRecordStream.filter(record -> record.getCreatedTime().before(dateBefore));
        }

        return userBillingRecordStream.toList();
    }

    public List<BillingRecord> getUserBillingRecords(int customerNumber) throws ResourceNotFoundException {
        List<BillingRecord> userBillingRecords = FakeDB.allBillingRecords.stream().filter(record -> record.getUser().getCustomerNumber() == customerNumber).toList();
        if (userBillingRecords.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No billing records found for user: %s", customerNumber));
        }
        return userBillingRecords;
    }

}