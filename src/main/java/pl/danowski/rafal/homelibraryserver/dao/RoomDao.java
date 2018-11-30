package pl.danowski.rafal.homelibraryserver.dao;

import org.springframework.stereotype.Repository;
import pl.danowski.rafal.homelibraryserver.dao.interfaces.IRoomDao;
import pl.danowski.rafal.homelibraryserver.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings("ALL")
@Repository
@Transactional
public class RoomDao implements IRoomDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Room> findByUserId(Integer id) {
        final String query = "FROM Room AS r WHERE r.id_uzytkownika = :id";
        return (List<Room>) em.createQuery(query)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public Room getById(Integer id) {
        final String query = "FROM Room AS r WHERE r.id = :id";
        return (Room) em.createQuery(query)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Room createRoom(Room room) {
        em.persist(room);
        return room;
    }

    @Override
    public Room updateRoom(Room room) {
        em.merge(room);
        em.flush();
        return room;
    }

    @Override
    public Room deleteRoom(Integer id) {
        Room room = getById(id);
        em.remove(room);
        return room;
    }
}
