package animeta.module;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import animeta.serializer.WorkSerializer;

public class SerializerModule extends AbstractBinder {
    @Override protected void configure() {
        bindAsContract(WorkSerializer.class).in(Singleton.class);
    }
}
