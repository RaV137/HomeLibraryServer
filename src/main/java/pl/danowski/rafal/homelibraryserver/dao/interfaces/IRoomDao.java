package pl.danowski.rafal.homelibraryserver.dao.interfaces;

import pl.danowski.rafal.homelibraryserver.model.Room;

import java.util.List;

public interface IRoomDao {

    List<Room> findByUserId(Integer id);

    Room getById(Integer id);

    Room createRoom(Room room);

    Room updateRoom(Room room);

    Room deleteRoom(Integer id);

}
