package test.interview.demo.repository;

import test.interview.demo.domain.BillingRecord;

import java.util.List;

public class BillingRecordRepo {

    public List<BillingRecord> getAllBillingRecords() {
        return FakeDB.allBillingRecords;
    }

}