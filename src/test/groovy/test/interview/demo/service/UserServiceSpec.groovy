package test.interview.demo.service

import spock.lang.Specification
import test.interview.demo.domain.BillingRecord
import test.interview.demo.domain.Invoice
import test.interview.demo.repository.BillingRecordRepo
import test.interview.demo.repository.InvoiceRepo

import java.time.LocalDateTime
import java.time.ZoneId

class UserServiceSpec extends Specification {

    InvoiceRepo invoiceRepo = Mock()
    BillingRecordRepo billingRecordRepo = Mock()
    def userService = new UserService(invoiceRepo, billingRecordRepo)

    int customerNumber
    Date dateAfter
    Date dateBefore

    def setup() {
        customerNumber = 1
        def twoDaysAgo = LocalDateTime.now().minusDays(2)
        def twoDaysFromNow = LocalDateTime.now().plusDays(2)
        dateAfter = Date.from(twoDaysAgo.atZone(ZoneId.systemDefault()).toInstant())
        dateBefore = Date.from(twoDaysFromNow.atZone(ZoneId.systemDefault()).toInstant())
    }

    def 'getUserInvoices() returns invoices within date range'() {
        given:
        def invoices = [Invoice.builder().createdTime(new Date()).build(), Invoice.builder().createdTime(new Date()).build()]
        invoiceRepo.getAllByUserId(customerNumber) >> invoices

        when:
        def result = userService.getUserInvoices(customerNumber, dateAfter, dateBefore)

        then:
        result == invoices
    }

    def 'getUserInvoices() returns invoices only after a certain date'() {
        given:
        def invoices = [Invoice.builder().createdTime(dateAfter).build(), Invoice.builder().createdTime(new Date()).build()]
        invoiceRepo.getAllByUserId(customerNumber) >> invoices

        when:
        def result = userService.getUserInvoices(customerNumber, dateAfter, null)

        then:
        result.size() == 1
        result.contains(invoices[1])
    }

    def 'getUserInvoices() returns invoices only before a certain date'() {
        given:
        def invoices = [Invoice.builder().createdTime(new Date()).build(), Invoice.builder().createdTime(dateBefore).build()]
        invoiceRepo.getAllByUserId(customerNumber) >> invoices

        when:
        def result = userService.getUserInvoices(customerNumber, null, dateBefore)

        then:
        result.size() == 1
        result.contains(invoices[0])
    }

    def 'getUserInvoices() returns all invoices when no date range specified'() {
        given:
        def invoices = [Invoice.builder().createdTime(new Date()).build(), Invoice.builder().createdTime(new Date()).build()]
        invoiceRepo.getAllByUserId(customerNumber) >> invoices

        when:
        def result = userService.getUserInvoices(customerNumber, null, null)

        then:
        result == invoices
    }

    def 'getUserInvoices() returns empty list when no invoices found'() {
        given:
        invoiceRepo.getAllByUserId(customerNumber) >> []

        when:
        def result = userService.getUserInvoices(customerNumber, dateAfter, dateBefore)

        then:
        result.isEmpty()
    }

    def 'getUserBillingRecords() returns billing records within date range'() {
        given:
        def billingRecords = [BillingRecord.builder().createdTime(new Date()).build(), BillingRecord.builder().createdTime(new Date()).build()]
        billingRecordRepo.getUserBillingRecords(customerNumber) >> billingRecords

        when:
        def result = userService.getUserBillingRecords(customerNumber, dateAfter, dateBefore)

        then:
        result == billingRecords
    }

    def 'getUserBillingRecords() returns billing records only after a certain date'() {
        given:
        def billingRecords = [BillingRecord.builder().createdTime(dateAfter).build(), BillingRecord.builder().createdTime(new Date()).build()]
        billingRecordRepo.getUserBillingRecords(customerNumber) >> billingRecords

        when:
        def result = userService.getUserBillingRecords(customerNumber, dateAfter, null)

        then:
        result.size() == 1
        result.contains(billingRecords[1])
    }

    def 'getUserBillingRecords() returns billing records only before a certain date'() {
        given:
        def billingRecords = [BillingRecord.builder().createdTime(new Date()).build(), BillingRecord.builder().createdTime(dateBefore).build()]
        billingRecordRepo.getUserBillingRecords(customerNumber) >> billingRecords

        when:
        def result = userService.getUserBillingRecords(customerNumber, null, dateBefore)

        then:
        result.size() == 1
        result.contains(billingRecords[0])
    }

    def 'getUserBillingRecords() returns all billing records when no date range specified'() {
        given:
        def billingRecords = [BillingRecord.builder().createdTime(new Date()).build(), BillingRecord.builder().createdTime(new Date()).build()]
        billingRecordRepo.getUserBillingRecords(customerNumber) >> billingRecords

        when:
        def result = userService.getUserBillingRecords(customerNumber, null, null)

        then:
        result == billingRecords
    }

    def 'getUserBillingRecords() returns empty list when no billing records found'() {
        given:
        billingRecordRepo.getUserBillingRecords(customerNumber) >> []

        when:
        def result = userService.getUserBillingRecords(customerNumber, dateAfter, dateBefore)

        then:
        result.isEmpty()
    }
}
