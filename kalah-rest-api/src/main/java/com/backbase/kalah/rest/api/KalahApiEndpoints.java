package com.backbase.kalah.rest.api;

import com.backbase.kalah.domain.exception.GameNotFoundException;
import com.backbase.kalah.domain.exception.GameOverException;
import com.backbase.kalah.domain.exception.IllegalMoveException;
import com.backbase.kalah.domain.exception.InvalidPitIdException;
import com.backbase.kalah.domain.exception.KalahException;
import com.backbase.kalah.domain.model.Game;
import com.backbase.kalah.domain.service.GameService;
import com.backbase.kalah.rest.constants.Constants;
import com.backbase.kalah.rest.mapper.CreateGameResponseMapper;
import com.backbase.kalah.rest.mapper.MakeMoveResponseMapper;
import com.backbase.kalah.rest.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * This class contains REST api endpoints for Kalah Game.
 *
 * @author Aashish
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
public class KalahApiEndpoints {

    private final GameService gameService;
    private final CreateGameResponseMapper createGameResponseMapper;
    private final MakeMoveResponseMapper makeMoveResponseMapper;

    /**
     * This method is used to create/initialize new game.
     *
     * @param request
     * @param numberOfStonesInPit
     * @return
     */
    @PostMapping
    public ResponseEntity createGame(HttpServletRequest request,
            @RequestParam(value = "numberOfPits", defaultValue = "6", required = false) Integer numberOfStonesInPit) {
        Game game = gameService.createGame(numberOfStonesInPit);
        String url = getUrl(request, game.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createGameResponseMapper.mapToResponse(game.getId(), url));
    }

    /**
     * This method is used to move stone from the pit for the given game.
     *
     * @param request
     * @param gameId
     * @param pitId
     * @return
     */
    @PutMapping("/{gameId}/pits/{pitId}")
    public ResponseEntity makeAMove(HttpServletRequest request, @PathVariable Integer gameId, @PathVariable Integer pitId) {
        log.debug("Move stones from pit {} for the game {}",gameId, pitId);
        Game game = gameService.makeAMove(gameId, pitId);
        String url = getUrl(request, game.getId());
        return ResponseEntity.ok().body(makeMoveResponseMapper.mapToResponse(game, url));
    }

    private String getUrl(HttpServletRequest request, Integer gameId) {
        return MessageFormat.format(Constants.GAME_URL,
                request.getScheme(), request.getServerName(), String.valueOf(request.getServerPort()), gameId);
    }

    @ExceptionHandler({GameNotFoundException.class, GameOverException.class, InvalidPitIdException.class, IllegalMoveException.class, KalahException.class})
    public ResponseEntity handleApiExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().message(ex.getMessage()).build());
    }

}
