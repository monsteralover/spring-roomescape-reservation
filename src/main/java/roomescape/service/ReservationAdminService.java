package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.*;
import roomescape.respository.ReservationDAO;
import roomescape.respository.ReservationTimeDAO;
import roomescape.respository.ThemeDAO;

import java.util.List;

@Service
public class ReservationAdminService {
    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;
    private final ThemeDAO themeDAO;

    public ReservationAdminService(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO,
                                   ThemeDAO themeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
        this.themeDAO = themeDAO;
    }

    public ReservationCreateResponseDto createReservation(ReservationCreateDto reservationCreateDto) {
        ReservationTime reservationTime = reservationTimeDAO.findReservationTimeById(reservationCreateDto.getTimeId());
        Theme theme = themeDAO.findThemeById(reservationCreateDto.getThemeId());
        return reservationDAO.insertReservation(reservationCreateDto, reservationTime.getId(), theme.getId());
    }

    public List<Reservation> getReservations() {
        return reservationDAO.readReservations();
    }

    public void deleteReservation(Long id) {
        reservationDAO.deleteReservation(id);
    }
}
