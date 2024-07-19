package test.interview.demo.repository;

import test.interview.demo.constants.InvoiceStatus;
import test.interview.demo.domain.BillingRecord;
import test.interview.demo.domain.Invoice;
import test.interview.demo.domain.User;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class FakeDB {

   /*
        SETUP DATA
   */

    static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    private static Date getOldYearDate() {
        Date date = null;
        try {
            String dateInString = "7-Jun-2023";
            date = formatter.parse(dateInString);
        } catch(ParseException e) {
            // do nothing
        }
        return date;
    }

    private static Date getThisYearDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    private static final User user1 = User.builder()
            .email("user1@email.com")
            .customerNumber(1)
            .build();

    private static final User user2 = User.builder()
            .email("user2@email.com")
            .customerNumber(2)
            .build();

    private static final BillingRecord br1 = BillingRecord.builder()
            .createdTime(getOldYearDate())
            .id(UUID.fromString("22222222-1111-1111-1111-111111111111"))
            .price(BigDecimal.valueOf(9.99))
            .user(user1)
            .build();

    private static final BillingRecord br2 = BillingRecord.builder()
            .price(BigDecimal.valueOf(10.99))
            .id(UUID.fromString("22222222-1111-1111-1111-111111111112"))
            .createdTime(getThisYearDate())
            .user(user1)
            .build();

    private static final BillingRecord br3 = BillingRecord.builder()
            .price(BigDecimal.valueOf(9.99))
            .id(UUID.fromString("22222222-1111-1111-1111-111111111113"))
            .createdTime(getOldYearDate())
            .user(user1)
            .build();

    private static final BillingRecord br4 = BillingRecord.builder()
            .price(BigDecimal.valueOf(3.99))
            .id(UUID.fromString("22222222-1111-1111-1111-111111111114"))
            .createdTime(getOldYearDate())
            .user(user2)
            .build();

    private static final BillingRecord br5 = BillingRecord.builder()
            .price(BigDecimal.valueOf(13.99))
            .id(UUID.fromString("22222222-1111-1111-1111-111111111115"))
            .createdTime(getThisYearDate())
            .user(user2)
            .build();

    private static final BillingRecord br6 = BillingRecord.builder()
            .price(BigDecimal.valueOf(3.99))
            .id(UUID.fromString("22222222-1111-1111-1111-111111111114"))
            .createdTime(getThisYearDate())
            .user(user2)
            .build();

    private static final BillingRecord br7 = BillingRecord.builder()
            .price(BigDecimal.valueOf(17.99))
            .id(UUID.fromString("22222222-1111-1111-1111-111111111117"))
            .createdTime(getThisYearDate())
            .user(user2)
            .build();

    private static final BillingRecord br8 = BillingRecord.builder()
            .price(BigDecimal.valueOf(18.99))
            .id(UUID.fromString("22222222-1111-1111-1111-111111111118"))
            .createdTime(getThisYearDate())
            .user(user2)
            .build();

    private static final Invoice inv1 = Invoice.builder()
            .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
            .status(InvoiceStatus.PAID)
            .billingRecords(List.of(br1, br2, br3))
            .createdTime(getThisYearDate())
            .user(user1)
            .build();

    private static final Invoice inv2 = Invoice.builder()
            .id(UUID.fromString("11111111-1111-1111-1111-111111111112"))
            .status(InvoiceStatus.PAID)
            .billingRecords(List.of(br4, br5, br6))
            .createdTime(getThisYearDate())
            .user(user2)
            .build();

    private static final Invoice inv3 = Invoice.builder()
            .id(UUID.fromString("11111111-1111-1111-1111-111111111113"))
            .status(InvoiceStatus.FAILED_AUTH)
            .billingRecords(List.of(br7, br8))
            .createdTime(getThisYearDate())
            .user(user2)
            .build();

    public static final Map<UUID, Invoice> idToInvoiceMap = Map.of(
            inv1.getId(), inv1,
            inv2.getId(), inv2,
            inv3.getId(), inv3
    );

    public static final List<BillingRecord> allBillingRecords = List.of(
            br1, br2, br3, br4, br5, br6, br7, br8
    );

}
