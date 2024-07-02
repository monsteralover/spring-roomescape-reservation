package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exception.ReservationDateInvalidException;
import roomescape.exception.ReservationNameInvalidException;
import roomescape.model.*;
import roomescape.respository.ReservationDAO;
import roomescape.respository.ReservationTimeDAO;
import roomescape.respository.ThemeDAO;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        validateReservationCreation(reservationCreateDto);
        ReservationTime reservationTime = reservationTimeDAO.findReservationTimeById(reservationCreateDto.getTimeId());
        Theme theme = themeDAO.findThemeById(reservationCreateDto.getThemeId());
        return reservationDAO.insertReservation(reservationCreateDto, reservationTime.getId(), theme.getId());
    }

    private void validateReservationCreation(ReservationCreateDto reservationCreateDto) {
        if (isInValidDateFormat(reservationCreateDto.getDate())) {
            throw new ReservationDateInvalidException("yyyy-mm-dd 형식에 맞는 날짜를 입력하세요");
        }
        if (containsSpecialCharacters(reservationCreateDto.getName()) || containsWhitespace(reservationCreateDto.getName())) {
            throw new ReservationNameInvalidException("이름에 특수문자나 공백이 들어갈 수 없습니다.");
        }
    }

    public static boolean isInValidDateFormat(String date) {
        String DATE_PATTERN = "^(\\d{4})-(\\d{2})-(\\d{2})$";
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);
        return !matcher.matches();
    }

    public boolean containsSpecialCharacters(String input) {
        String SPECIAL_CHAR_PATTERN = "[^a-zA-Z0-9 ]";
        Pattern pattern = Pattern.compile(SPECIAL_CHAR_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public boolean containsWhitespace(String input) {
        String WHITESPACE_PATTERN = "\\s";
        Pattern pattern = Pattern.compile(WHITESPACE_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public List<Reservation> getReservations() {
        return reservationDAO.readReservations();
    }

    public void deleteReservation(Long id) {
        reservationDAO.deleteReservation(id);
    }
}
