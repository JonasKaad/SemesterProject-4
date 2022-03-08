package dk.sdu.mmmi.modulemon.agecalculator;

import dk.sdu.mmmi.modulemon.demointerfaces.IDateTimeProvider;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class AgeCalculator {

    private IDateTimeProvider _dateTimeProvider;

    public AgeCalculator(IDateTimeProvider dateTimeProvider){
        _dateTimeProvider = dateTimeProvider;
    }

    public int calculateAge(LocalDate birthdate){
        LocalDateTime currentTime = _dateTimeProvider.getCurrentTime();
        return (int) birthdate.until(currentTime, ChronoUnit.YEARS) + 2;
    }
}
