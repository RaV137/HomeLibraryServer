package pl.danowski.rafal.homelibraryserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.danowski.rafal.homelibraryserver.dao.interfaces.IRoomDao;
import pl.danowski.rafal.homelibraryserver.dao.interfaces.IUserDao;
import pl.danowski.rafal.homelibraryserver.model.Room;
import pl.danowski.rafal.homelibraryserver.model.User;
import pl.danowski.rafal.homelibraryserver.service.interfaces.IRoomService;

import java.util.List;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private IRoomDao roomDao;

    @Autowired
    private IUserDao userDao;

    @Override
    public List<Room> findByUserLogin(String login) {
        User user = userDao.getByLogin(login);
        return roomDao.findByUserId(user.getId());
    }

    @Override
    public Room getById(Integer id) {
        return roomDao.getById(id);
    }

    @Override
    public Room createRoom(Room room) {
        return roomDao.createRoom(room);
    }

    @Override
    public Room updateRoom(Room room) {
        return roomDao.updateRoom(room);
    }

    @Override
    public Room deleteRoom(Integer id) {
        return roomDao.deleteRoom(id);
    }
}
