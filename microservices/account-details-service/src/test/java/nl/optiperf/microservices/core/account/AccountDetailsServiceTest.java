package nl.optiperf.microservices.core.account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import nl.optiperf.microservices.core.account.model.AccountDetails;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("AccountDetailsService model unit tests.")
public class AccountDetailsServiceTest {
    private final Integer accountNumber = 123456;
    private final String accountName = "Test Account";
    private final AccountDetails.AccountType accountType = AccountDetails.AccountType.SAVINGS;
    private final String currency = "USD";
    private final String status = "ACTIVE";
    private final OffsetDateTime createdDate = OffsetDateTime.now(ZoneOffset.UTC);
    
    @Test
    @DisplayName("Should correctly initialize with all-args constructor and getters should return correct values")
    void testAllArgsConstructorAndGetters() {
        AccountDetails accountDetails = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);

        assertEquals(accountNumber, accountDetails.getAccountNumber());
        assertEquals(accountName, accountDetails.getAccountName());
        assertEquals(accountType, accountDetails.getAccountType());
        assertEquals(currency, accountDetails.getCurrency());
        assertEquals(status, accountDetails.getStatus());
        assertEquals(createdDate, accountDetails.getCreatedDate());
        assertNotNull(accountDetails.toString(), "toString() should not return null");
        assertNotNull(accountDetails.hashCode(), "hashCode() should not return null");
        assertNotNull(accountDetails.equals(new AccountDetails()), "equals() should not return null");
    }

    @Test
    @DisplayName("Should correctly set and get values using setters and getters")
    void testSettersAndGetters() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountNumber(accountNumber);
        accountDetails.setAccountName(accountName);
        accountDetails.setAccountType(accountType);
        accountDetails.setCurrency(currency);
        accountDetails.setStatus(status);
        accountDetails.setCreatedDate(createdDate);

        assertEquals(accountNumber, accountDetails.getAccountNumber());
        assertEquals(accountName, accountDetails.getAccountName());
        assertEquals(accountType, accountDetails.getAccountType());
        assertEquals(currency, accountDetails.getCurrency());
        assertEquals(status, accountDetails.getStatus());
        assertEquals(createdDate, accountDetails.getCreatedDate());
    }
    @Test
    @DisplayName("Equals should return true for identical objects")
    void testEquals() {
        AccountDetails accountDetails1 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        AccountDetails accountDetails2 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);

        assertTrue(accountDetails1.equals(accountDetails2));
        assertTrue(accountDetails2.equals(accountDetails1));
    }
    @Test
    @DisplayName("Equals should return false for objects with different values")
    void testEquals_DifferentValues() {
        AccountDetails account1 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        AccountDetails account2 = new AccountDetails(99999, "Different Account", AccountDetails.AccountType.CHECKING, "USD", "INACTIVE", OffsetDateTime.now().minusDays(1));

        assertFalse(account1.equals(account2));
    }
    @Test
    @DisplayName("HashCode should be same for identical objects")
    void testHashCode_ConsistentForEqualObjects() {
        AccountDetails account1 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        AccountDetails account2 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);

        assertEquals(account1.hashCode(), account2.hashCode());
    }
    @Test
    @DisplayName("Equals should return false when comparing with different accountNumber")
    void testEquals_DifferentAccountNumber() {
        AccountDetails account1 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        AccountDetails account2 = new AccountDetails(99999, accountName, accountType, currency, status, createdDate);
        assertFalse(account1.equals(account2));
    }
    @Test
    @DisplayName("Equals should return false when comparing with different accountName")
    void testEquals_DifferentAccountName() {
        AccountDetails account1 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        AccountDetails account2 = new AccountDetails(accountNumber, "Another Name", accountType, currency, status, createdDate);
        assertFalse(account1.equals(account2));
    }
// Add similar specific difference tests for accountType, currency, status, createdDate
    @Test
    @DisplayName("Equals should return false when comparing with different accountType")
    void testEquals_DifferentAccountType() {
        AccountDetails account1 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        AccountDetails account2 = new AccountDetails(accountNumber, accountName, AccountDetails.AccountType.CHECKING, currency, status, createdDate);
        assertFalse(account1.equals(account2));
    }
    @Test
    @DisplayName("Equals should return false when comparing with different currency")
    void testEquals_DifferentCurrency() {
        AccountDetails account1 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        AccountDetails account2 = new AccountDetails(accountNumber, accountName, accountType, "EUR", status, createdDate);
        assertFalse(account1.equals(account2));
    }
    @Test
    @DisplayName("Equals should return false when comparing with different status")
    void testEquals_DifferentStatus() {
        AccountDetails account1 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        AccountDetails account2 = new AccountDetails(accountNumber, accountName, accountType, currency, "INACTIVE", createdDate);
        assertFalse(account1.equals(account2));
    }
    @Test
    @DisplayName("Equals should return false when comparing with different createdDate")
    void testEquals_DifferentCreatedDate() {
        AccountDetails account1 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        AccountDetails account2 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate.minusDays(1));
        assertFalse(account1.equals(account2));
    }
    @Test
    @DisplayName("HashCode should be consistent for the same object")
    void testHashCode_Consistent() {
        AccountDetails account = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        int initialHashCode = account.hashCode();
        
        // Call hashCode multiple times to ensure consistency
        for (int i = 0; i < 10; i++) {
            assertEquals(initialHashCode, account.hashCode());
        }
    }   

    @Test
    @DisplayName("Equals should return false when comparing with null")
    void testEquals_Null() {
        AccountDetails account1 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        assertFalse(account1.equals(null));
    }
    @Test
    @DisplayName("Equals should return false when comparing with object of different type")
    void testEquals_DifferentType() {
        AccountDetails account1 = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        Object otherObject = new Object();
        assertFalse(account1.equals(otherObject));
    }
    @Test
    @DisplayName("toString should return a non-null string and contain key fields")
    void testToString() {
        AccountDetails account = new AccountDetails(accountNumber, accountName, accountType, currency, status, createdDate);
        String accountString = account.toString();

        assertNotNull(accountString);
        assertTrue(accountString.contains(accountNumber.toString()));
        assertTrue(accountString.contains(accountName));
        assertTrue(accountString.contains(accountType.toString()));
        assertTrue(accountString.contains(currency));
        assertTrue(accountString.contains(status));
        assertTrue(accountString.contains(createdDate.toString()), "toString() should contain createdDate");
        // You can add more assertions to check for specific formatting or content in the toString output
        assertTrue(accountString.startsWith("AccountDetails{"), "toString() should start with 'AccountDetails{'");
        assertTrue(accountString.endsWith("}"), "toString() should end with '}'");
        // Check if the string contains all fields
        assertTrue(accountString.contains("accountNumber=" + accountNumber), "toString() should contain accountNumber");
        assertTrue(accountString.contains("accountName='" + accountName + "'"), "toString() should contain accountName");
        assertTrue(accountString.contains("accountType=" + accountType), "toString() should contain accountType");
        assertTrue(accountString.contains("currency='" + currency + "'"), "toString() should contain currency");
        assertTrue(accountString.contains("status='" + status + "'"), "toString() should contain status");
        assertTrue(accountString.contains("createdDate=" + createdDate), "toString() should contain createdDate");
    }
}
