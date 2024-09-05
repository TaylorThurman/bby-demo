package test.interview.demo.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import test.interview.demo.domain.BillingRecord
import test.interview.demo.domain.Invoice
import test.interview.demo.service.UserService

import static org.mockito.ArgumentMatchers.anyInt
import static org.mockito.ArgumentMatchers.any
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UserController)
class UserControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc
    @MockBean
    UserService userService

    def "GET /users/{id}/records?type=invoices returns list of invoices"() {
        given:
        def invoice = Invoice.builder().id(UUID.randomUUID()).build()
        def invoices = [invoice]
        userService.getUserInvoices(anyInt(), any(), any()) >> invoices

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/records").param("type", "invoices"))
                .andExpect {
                    status().isOk()
                    content().json(invoices as String)
                }
    }

    def "GET /users/{id}/records?type=billing_records returns list of billing records"() {
        given:
        def billingRecord = BillingRecord.builder().id(UUID.randomUUID()).build()
        def billingRecords = [billingRecord]
        userService.getUserBillingRecords(anyInt(), any(), any()) >> billingRecords

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/records").param("type", "billing_records"))
                .andExpect {
                    status().isOk()
                    content().json(billingRecords as String)
                }
    }

    def "GET /users/{id}/records?type=invoices returns 400 when date after is later than date before"() {
        given:
        def dateAfter = "2021-01-02"
        def dateBefore = "2021-01-01"

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/records?type=invoices&dateAfter=$dateAfter&dateBefore=$dateBefore"))
                .andExpect {
                    status().isBadRequest()
                }
    }

    def "GET /users/{id}/records?type=billing_records returns 400 when date after is later than date before"() {
        given:
        def dateAfter = "2021-01-02"
        def dateBefore = "2021-01-01"

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/records?type=billing_records&dateAfter=$dateAfter&dateBefore=$dateBefore"))
                .andExpect {
                    status().isBadRequest()
                }
    }
}