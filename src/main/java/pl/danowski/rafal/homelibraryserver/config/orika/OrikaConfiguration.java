package pl.danowski.rafal.homelibraryserver.config.orika;

import com.google.common.collect.ImmutableList;
import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.FromStringConverter;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class OrikaConfiguration extends ConfigurableMapper implements ApplicationContextAware {

    private MapperFactory factory;

    protected abstract ImmutableList<Pair<Class<?>, Class<?>>> getDefaultMappings();

    @Override
    protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
        factoryBuilder.useAutoMapping(false).build();
    }

    protected void configure(MapperFactory factory) {
        this.factory = factory;
        registerDefaultConverters(factory);
        registerDefaultMappings();
    }

    private void registerDefaultMappings() {
        getDefaultMappings()
                .forEach(mappingPair -> registerDefaultMapping(mappingPair.getLeft(), mappingPair.getRight()));
    }

    private void registerDefaultConverters(MapperFactory factory) {
        ConverterFactory converterFactory = factory.getConverterFactory();
        converterFactory.registerConverter(new PassThroughConverter(LocalDateTime.class));
        converterFactory.registerConverter(new PassThroughConverter(LocalDate.class));
        converterFactory.registerConverter(new PassThroughConverter(LocalTime.class));
        converterFactory.registerConverter(new FromStringConverter());
    }

    @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        addAllSpringBeans(applicationContext);
    }

    private void addAllSpringBeans(ApplicationContext applicationContext) {
        applicationContext.getBeansOfType(Converter.class).values().forEach(this::addConverter);
        applicationContext.getBeansOfType(Mapper.class).values().forEach(this::addMapper);
    }

    public void addConverter(Converter<?, ?> converter) {
        factory.getConverterFactory().registerConverter(converter);
    }

    public void addMapper(Mapper<?, ?> mapper) {
        registerDefaultMapping(mapper.getAType().getClass(), mapper.getBType().getClass());
    }

    private void registerDefaultMapping(Class<?> firstClass, Class<?> secondClass) {
        factory.classMap(firstClass, secondClass).byDefault().register();
    }
}
