package dk.sdu.mmmi.modulemon.DemoComponent.agecalculator;

import dk.sdu.mmmi.modulemon.DemoComponent.demointerfaces.IDateTimeProvider;

import java.time.LocalDateTime;

public class SystemDateTimeProvider implements IDateTimeProvider {
    @Override
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
