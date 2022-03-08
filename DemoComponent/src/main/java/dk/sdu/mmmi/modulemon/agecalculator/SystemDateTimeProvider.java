package dk.sdu.mmmi.modulemon.agecalculator;

import dk.sdu.mmmi.modulemon.demointerfaces.IDateTimeProvider;

import java.time.LocalDateTime;

public class SystemDateTimeProvider implements IDateTimeProvider {
    @Override
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
