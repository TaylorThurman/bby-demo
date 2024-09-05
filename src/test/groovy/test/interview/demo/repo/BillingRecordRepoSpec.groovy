package test.interview.demo.repo

import spock.lang.Specification
import test.interview.demo.exception.ResourceNotFoundException
import test.interview.demo.repository.BillingRecordRepo

class BillingRecordRepoSpec extends Specification {

    BillingRecordRepo billingRecordRepo = new BillingRecordRepo()

    def "getUserBillingRecords() returns billing record when customer number is valid"() {
        given:
        def validCustomerNumber = 1

        when:
        def billingRecords = billingRecordRepo.getUserBillingRecords(validCustomerNumber)

        then:
        billingRecords != null
        billingRecords.forEach { it.user.customerNumber == validCustomerNumber }
    }

    def "getUserBillingRecords() returns ResourceNotFoundException when customer number is invalid"() {
        given:
        def invalidCustomerNumber = 999

        when:
        billingRecordRepo.getUserBillingRecords(invalidCustomerNumber)

        then:
        thrown(ResourceNotFoundException)
    }
}
