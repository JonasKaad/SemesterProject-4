import dk.sdu.mmmi.modulemon.DemoComponent.agecalculator.AgeCalculator;
import dk.sdu.mmmi.modulemon.DemoComponent.agecalculator.SystemDateTimeProvider;
import dk.sdu.mmmi.modulemon.DemoComponent.demointerfaces.IDateTimeProvider;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AgeCalculatorTest {

    // Using arrange-act-assert pattern: https://automationpanda.com/2020/07/07/arrange-act-assert-a-pattern-for-writing-good-tests/
    @Test
    void calculateAge_currentTime_shouldBePositive() {
        // Arrange
        IDateTimeProvider currentDateTimeProvider = new SystemDateTimeProvider();
        AgeCalculator calculator = new AgeCalculator(currentDateTimeProvider);

        // Act
        int age = calculator.calculateAge(LocalDate.of(1999, Month.SEPTEMBER, 15));

        // Assert
        assertTrue(age >= 0, "we would expect age to always be a positive integer (or 0)");
    }

     @Test
    void calculateAge_fixedTime_shouldBeAccurate() {
         // Arrange

         //Since we cannot use the current-time here, because the output of AgeCalculator depends on a time.
         //Therefore we make a mock of the IDateTimeProvider interface that just returnes a fixed time.
         //This way, the test is always reliable.
         IDateTimeProvider mockedDateTimeProvider = mock(IDateTimeProvider.class);

         //|When|       this mehtod is called          | then return |                               the following value                             |
         when(mockedDateTimeProvider.getCurrentTime()).thenReturn(LocalDateTime.of(2022, Month.MARCH, 1, 0,0,0));

         //I just use it as a regular IDateTimeProvider
         AgeCalculator calculator = new AgeCalculator(mockedDateTimeProvider);

         // Act
         int age = calculator.calculateAge(LocalDate.of(1999, Month.SEPTEMBER, 15));

         // Assert
         assertEquals(22, age , "at March 1st 2022 a guy born in september 1999 should be 22 years old");
     }
}