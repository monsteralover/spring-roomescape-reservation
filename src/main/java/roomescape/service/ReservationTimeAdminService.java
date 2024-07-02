package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exception.ReservationTimeInvalidException;
import roomescape.model.ReservationTime;
import roomescape.model.ReservationTimeCreateRequestDto;
import roomescape.respository.ReservationTimeDAO;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReservationTimeAdminService {
    private final ReservationTimeDAO reservationTimeDao;

    public ReservationTimeAdminService(ReservationTimeDAO reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime createReservationTime(ReservationTimeCreateRequestDto time) {
        validateReservationTimeCreation(time);
        return reservationTimeDao.insertReservationTime(time.getStartAt());
    }

    private void validateReservationTimeCreation(ReservationTimeCreateRequestDto time) {
        String startAt = time.getStartAt();
        if (isInValidTime(startAt)) {
            throw new ReservationTimeInvalidException("24시간제 형식에 맞는 시간을 입력해주세요.");
        }
    }

    private boolean isInValidTime(String time) {
        final String TIME_PATTERN = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

        Pattern pattern = Pattern.compile(TIME_PATTERN);
        Matcher matcher = pattern.matcher(time);
        return !matcher.matches();
    }

    public List<ReservationTime> getReservationTime() {
        return reservationTimeDao.readReservationTime();
    }

    public void deleteReservationTime(Long id) {
        reservationTimeDao.deleteReservationTime(id);
    }
}
