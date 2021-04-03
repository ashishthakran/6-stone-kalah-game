package com.backbase.kalah.rest.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class contains general application constants.
 *
 * @author Aashish
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Constants {

    public static final String GAME_URL = "{0}://{1}:{2}/games/{3}";
}
