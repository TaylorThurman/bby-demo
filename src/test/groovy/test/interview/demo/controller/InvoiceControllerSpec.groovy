package test.interview.demo.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import test.interview.demo.domain.Invoice
import test.interview.demo.service.InvoiceService

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(InvoiceController)
class InvoiceControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc
    @MockBean
    InvoiceService invoiceService

    def "GET /invoices/{id} returns an invoice when UUID is valid"() {
        given:
        def validId = UUID.randomUUID()
        def invoice = Invoice.builder().id(UUID.randomUUID()).build()
        invoiceService.getInvoice(validId) >> invoice

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/invoices/1"))
                .andExpect {
                    status().isOk()
                    content().json(invoice as String)
                }
    }

    def "GET /invoices/{id} returns 400 when UUID is invalid"() {
        given:
        def invalidId = "invalid"

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/invoices/$invalidId"))
                .andExpect {
                    status().isBadRequest()
                }
    }
}
