package pl.danowski.rafal.homelibraryserver.service.interfaces;

import pl.danowski.rafal.homelibraryserver.model.Room;

import java.util.List;

public interface IRoomService {

    List<Room> findByUserLogin(String login);

    Room getById(Integer id);

    Room createRoom(Room room);

    Room updateRoom(Room room);

    Room deleteRoom(Integer id);

}
