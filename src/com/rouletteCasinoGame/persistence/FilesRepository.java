package com.rouletteCasinoGame.persistence;




import com.rouletteCasinoGame.Player;
import com.rouletteCasinoGame.exceptions.GameException;

import java.util.Collection;

/**
 * Interface that should be implemented by all
 * the types of repositories available.
 */
public interface FilesRepository {

    Collection<Player> getPlayers() throws GameException;

    void save(Collection<Player> players);
}
