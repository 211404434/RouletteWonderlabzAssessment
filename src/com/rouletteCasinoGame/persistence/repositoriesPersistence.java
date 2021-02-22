package com.rouletteCasinoGame.persistence;


import com.rouletteCasinoGame.files.csv.CSVFileFilesRepository;

import java.io.File;

/**
 * This class is a factory where different types of repositories
 * should be created.
 */
public class repositoriesPersistence {

    public static FilesRepository loadFileFrom(final File file) {
        return new CSVFileFilesRepository(file);
    }
}