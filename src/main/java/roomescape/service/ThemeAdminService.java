package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.Theme;
import roomescape.model.ThemeCreateRequestDto;
import roomescape.model.ThemeCreateResponseDto;
import roomescape.respository.ThemeDAO;

@Service
public class ThemeAdminService {
    private final ThemeDAO themeDAO;

    public ThemeAdminService(ThemeDAO themeDAO) {
        this.themeDAO = themeDAO;
    }

    public ThemeCreateResponseDto createTheme(ThemeCreateRequestDto themeCreateDto) {
        Theme theme = new Theme(themeCreateDto.getName(), themeCreateDto.getDescription(), themeCreateDto.getThumbnail());
        Theme insertedTheme = themeDAO.insertTheme(theme);
        return new ThemeCreateResponseDto(insertedTheme.getId(), insertedTheme.getName(), insertedTheme.getDescription(),
                insertedTheme.getThumbnail());
    }


}
