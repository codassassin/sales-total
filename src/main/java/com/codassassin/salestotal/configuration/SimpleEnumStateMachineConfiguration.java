package com.codassassin.salestotal.configuration;

import com.codassassin.salestotal.SalesEvent;
import com.codassassin.salestotal.SalesState;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Log
@Configuration
@EnableStateMachineFactory
public class SimpleEnumStateMachineConfiguration extends StateMachineConfigurerAdapter<SalesState, SalesEvent> {

    @Override
    public void configure(StateMachineTransitionConfigurer<SalesState, SalesEvent> transitions) throws Exception {
        transitions
                .withExternal().source(SalesState.UPLOADED).target(SalesState.VERIFIED).event(SalesEvent.UPLOAD)
                .and()
                .withExternal().source(SalesState.VERIFIED).target(SalesState.AGGREGATED).event(SalesEvent.VERIFY)
                .and()
                .withExternal().source(SalesState.AGGREGATED).target(SalesState.REPORT_GENERATED).event(SalesEvent.AGGREGATE)
                .and()
                .withExternal().source(SalesState.REPORT_GENERATED).event(SalesEvent.GENERATE)
        ;
    }

    @Override
    public void configure(StateMachineStateConfigurer<SalesState, SalesEvent> states) throws Exception {
        states
                .withStates()
                .initial(SalesState.UPLOADED)
                .state(SalesState.VERIFIED)
                .state(SalesState.AGGREGATED)
                .end(SalesState.REPORT_GENERATED)
        ;
    }
}
