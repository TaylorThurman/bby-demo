package test.interview.demo.repo

import spock.lang.Specification
import test.interview.demo.domain.Invoice
import test.interview.demo.exception.ResourceNotFoundException
import test.interview.demo.repository.InvoiceRepo

class InvoiceRepoSpec extends Specification {

    InvoiceRepo invoiceRepo = new InvoiceRepo()

    def "getById() returns invoice when id is valid"() {
        given:
        UUID validId = UUID.fromString("11111111-1111-1111-1111-111111111111")

        when:
        Invoice invoice = invoiceRepo.getById(validId)

        then:
        invoice != null
        invoice.id == validId
    }

    def "getAllByUserId() returns invoices when userId is valid"() {
        given:
        int validUserId = 1

        when:
        List<Invoice> invoices = invoiceRepo.getAllByUserId(validUserId)

        then:
        invoices.size() == 1
        invoices.every { it.user.customerNumber == validUserId }
    }

    def "getAllByUserId() throws ResourceNotFoundException when userId is invalid"() {
        given:
        int invalidUserId = 999

        when:
        invoiceRepo.getAllByUserId(invalidUserId)

        then:
        thrown(ResourceNotFoundException)
    }
}
