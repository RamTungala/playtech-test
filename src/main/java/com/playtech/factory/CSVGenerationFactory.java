package com.playtech.factory;

import com.playtech.service.CSVGeneration;
import com.playtech.service.GameWageredWonService;
import com.playtech.service.ReadPlayerInfoFromService;
import com.playtech.service.SessionGameWageredWonService;
import com.playtech.service.SessionWageredWonService;
import com.playtech.service.Top5ProfitablePlayersForCompanyService;
import com.playtech.service.WriteDataToCSV;

public class CSVGenerationFactory {

    public CSVGeneration createCSVFile(Integer input,
                                       ReadPlayerInfoFromService readPlayerInfoFromService,
                                       WriteDataToCSV writeDataToCSV) {
        if (1 == input) {
            return new SessionWageredWonService(readPlayerInfoFromService, writeDataToCSV);
        } else if (2 == input) {
            return new GameWageredWonService(readPlayerInfoFromService, writeDataToCSV);
        } else if (3 == input) {
            return new SessionGameWageredWonService(readPlayerInfoFromService, writeDataToCSV);
        } else if (4 == input) {
            return new Top5ProfitablePlayersForCompanyService(readPlayerInfoFromService, writeDataToCSV);
        } else {
            return null;
        }
    }
}
