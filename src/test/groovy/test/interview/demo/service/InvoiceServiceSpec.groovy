package test.interview.demo.service

import spock.lang.Specification
import test.interview.demo.domain.BillingRecord
import test.interview.demo.domain.Invoice
import test.interview.demo.exception.ResourceNotFoundException
import test.interview.demo.repository.InvoiceRepo

import java.time.LocalDateTime
import java.time.ZoneId

class InvoiceServiceSpec extends Specification {

    InvoiceRepo invoiceRepo = Mock()
    def invoiceService = new InvoiceService(invoiceRepo)

    def 'getInvoice() returns invoice when id is valid'() {
        given:
        def id = UUID.fromString("11111111-1111-1111-1111-111111111111")
        def invoice = Invoice.builder().id(id).build()

        when:
        def result = invoiceService.getInvoice(id)

        then:
        result == invoice
        1 * invoiceRepo.getById(id) >> invoice
        0 * _
    }

    def 'getInvoice() throws ResourceNotFoundException when id is invalid'() {
        given:
        def id = UUID.fromString("11111111-1111-1111-1111-111111111111")

        when:
        def result = invoiceService.getInvoice(id)

        then:
        result == null
        thrown(ResourceNotFoundException)
    }

    def 'getInvoice() filters older duplicate billing records'() {
        given:
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2)
        Date createdDate = Date.from(twoDaysAgo.atZone(ZoneId.systemDefault()).toInstant())

        def id = UUID.fromString("33333333-3333-3333-3333-333333333333")
        def billingRecord1 = BillingRecord.builder().id(id).createdTime(createdDate).build()
        def billingRecord2 = BillingRecord.builder().id(id).createdTime(new Date()).build()
        def invoice = Invoice.builder().id(id).billingRecords([billingRecord1, billingRecord2]).build()
        invoiceRepo.getById(id) >> invoice

        when:
        def result = invoiceService.getInvoice(id)

        then:
        result.billingRecords.size() == 1
        result.billingRecords.contains(billingRecord2)
    }
}
