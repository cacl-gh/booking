package com.cacl.booking.app.config;

import com.cacl.booking.app.TicketModel;
import com.cacl.booking.domain.Ticket;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppOrikaConfig {

    @Bean("appOrika")
    public MapperFacade mapperFacade() {

        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(TicketModel.class, Ticket.class).byDefault().register();

        return mapperFactory.getMapperFacade();
    }
}