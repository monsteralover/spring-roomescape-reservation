package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.model.ThemeCreateRequestDto;
import roomescape.model.ThemeCreateResponseDto;
import roomescape.service.ThemeAdminService;

@RestController
@RequestMapping("/themes")
public class ThemeAdminApiController {
    private final ThemeAdminService themeAdminService;

    public ThemeAdminApiController(ThemeAdminService themeAdminService) {
        this.themeAdminService = themeAdminService;
    }

    @PostMapping
    public ResponseEntity<ThemeCreateResponseDto> createTheme(@RequestBody ThemeCreateRequestDto themeCreateRequestDto) {
        ThemeCreateResponseDto themeCreateResponseDto = themeAdminService.createTheme(themeCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(themeCreateResponseDto);
    }

}
