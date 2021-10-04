package com.example.onlinephoneshop.generators;

import com.example.onlinephoneshop.utils.Helper;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserIdGenerator implements IdentifierGenerator, Configurable {
    private String prefix;

    @Override
    public Serializable generate(
            SharedSessionContractImplementor session, Object obj)
            throws HibernateException {

        String query = String.format("select %s from %s",
                session.getEntityPersister(obj.getClass().getName(), obj)
                        .getIdentifierPropertyName(),
                obj.getClass().getSimpleName());

        Stream idsToCheckExist = session.createQuery(query).stream();
        Stream ids = session.createQuery(query).stream();

        String currentDateStr = Helper.extractDateForPrefixId();
        String newPrefix = prefix + currentDateStr;

        List<String> existDatePrefixList = (List<String>) idsToCheckExist.filter(o -> o.toString().contains(newPrefix))
                                            .collect(Collectors.toList());
        Long max=0L;
        if(existDatePrefixList.size()>0){
            max = ids.map(o -> o.toString().substring(10, 14))
                    .mapToLong(num -> Long.parseLong(num.toString()))
                    .max()
                    .orElse(0L);
        }
        Long newIDNumber = max + 1;

        if (newIDNumber >= 1 && newIDNumber < 10)
            return newPrefix + "000" + newIDNumber;
        else if (newIDNumber >= 10 && newIDNumber < 100)
            return newPrefix + "00" + newIDNumber;
        else if (newIDNumber >= 100 && newIDNumber < 1000)
            return newPrefix + "0" + newIDNumber;
        else
            return newPrefix + newIDNumber;
    }

    @Override
    public void configure(Type type, Properties properties,
                          ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty("prefix");
    }
}
