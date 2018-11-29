package pl.danowski.rafal.homelibraryserver.config.orika;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import pl.danowski.rafal.homelibraryserver.dto.user.RegisterUserDto;
import pl.danowski.rafal.homelibraryserver.dto.user.EditUserDto;
import pl.danowski.rafal.homelibraryserver.dto.user.UserDto;
import pl.danowski.rafal.homelibraryserver.model.User;

@Component
public class UsersOrikaConfiguration extends OrikaConfiguration {

    private static final ImmutableList<Pair<Class<?>, Class<?>>> DEFAULT_MAPPINGS =
            ImmutableList.of(
                    new ImmutablePair<>(User.class, UserDto.class),
                    new ImmutablePair<>(User.class, RegisterUserDto.class),
                    new ImmutablePair<>(User.class, EditUserDto.class)
            );

    @Override
    protected ImmutableList<Pair<Class<?>, Class<?>>> getDefaultMappings() {
        return DEFAULT_MAPPINGS;
    }
}
