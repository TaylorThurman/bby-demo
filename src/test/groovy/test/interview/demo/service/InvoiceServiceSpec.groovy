package test.interview.demo.service

import spock.lang.Specification
import test.interview.demo.domain.Invoice
import test.interview.demo.repository.InvoiceRepo
import test.interview.demo.service.InvoiceService

class InvoiceServiceSpec extends Specification {

    InvoiceRepo invoiceRepo = Mock()
    def invoiceService = new InvoiceService(invoiceRepo)

    def 'getInvoice() happy path'() {
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
}
