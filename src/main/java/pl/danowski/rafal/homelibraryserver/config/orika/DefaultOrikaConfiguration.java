package pl.danowski.rafal.homelibraryserver.config.orika;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import pl.danowski.rafal.homelibraryserver.dto.book.BookDto;
import pl.danowski.rafal.homelibraryserver.dto.book.CreateBookDto;
import pl.danowski.rafal.homelibraryserver.dto.room.CreateRoomDto;
import pl.danowski.rafal.homelibraryserver.dto.room.RoomDto;
import pl.danowski.rafal.homelibraryserver.dto.user.RegisterEditUserDto;
import pl.danowski.rafal.homelibraryserver.dto.user.UserDto;
import pl.danowski.rafal.homelibraryserver.model.Book;
import pl.danowski.rafal.homelibraryserver.model.Room;
import pl.danowski.rafal.homelibraryserver.model.User;

@Component
public class DefaultOrikaConfiguration extends OrikaConfiguration {

    private static final ImmutableList<Pair<Class<?>, Class<?>>> DEFAULT_MAPPINGS =
            ImmutableList.of(
                    new ImmutablePair<>(User.class, UserDto.class),
                    new ImmutablePair<>(User.class, RegisterEditUserDto.class),
                    new ImmutablePair<>(Room.class, RoomDto.class),
                    new ImmutablePair<>(Room.class, CreateRoomDto.class),
                    new ImmutablePair<>(Book.class, BookDto.class),
                    new ImmutablePair<>(Book.class, CreateBookDto.class)
            );

    @Override
    protected ImmutableList<Pair<Class<?>, Class<?>>> getDefaultMappings() {
        return DEFAULT_MAPPINGS;
    }
}
