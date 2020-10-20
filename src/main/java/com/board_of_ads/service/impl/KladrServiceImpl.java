package com.board_of_ads.service.impl;

import com.board_of_ads.models.City;
import com.board_of_ads.models.Region;
import com.board_of_ads.repository.CityRepository;
import com.board_of_ads.repository.RegionRepository;
import com.board_of_ads.service.interfaces.KladrService;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class KladrServiceImpl implements KladrService {

    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;

    @Override
    public Region getRegionByRegionNumber(String regionNumber) {
        return regionRepository.findRegionByRegionNumber(regionNumber);
    }

    @Override
    public void saveRegion(Region region) {
        regionRepository.save(region);
    }

    @Override
    public void saveCity(City city) {
        cityRepository.save(city);
    }

    @Override
    public Set<City> getAllCityOfRegion(Region region) {
        return cityRepository.findCitiesByRegion(region);
    }

    @Override
    public boolean existsCityByCityNameAndRegion(String cityName, Region region) {
        return cityRepository.existsCityByNameAndRegion(cityName, region);
    }

    @Override
    public boolean existsRegionByName(String regionName) {
        return regionRepository.existsRegionByName(regionName);
    }

    @Override
    public void streamKladr() throws IOException {
        Set<FileInputStream> streamKLADR = new HashSet<>();
        FileInputStream fileInputStream_1 = new FileInputStream("src/main/resources/kladr/KLADR_1.xls");
        streamKLADR.add(fileInputStream_1);
        FileInputStream fileInputStream_2 = new FileInputStream("src/main/resources/kladr/KLADR_2.xls");
        streamKLADR.add(fileInputStream_2);
        FileInputStream fileInputStream_3 = new FileInputStream("src/main/resources/kladr/KLADR_3.xls");
        streamKLADR.add(fileInputStream_3);
        FileInputStream fileInputStream_4 = new FileInputStream("src/main/resources/kladr/KLADR_4.xls");
        streamKLADR.add(fileInputStream_4);
        for (FileInputStream fileInputStream : streamKLADR) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            Workbook workbook = new HSSFWorkbook(bufferedInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getCell(1).getStringCellValue().equals("обл")
                        || row.getCell(1).getStringCellValue().equals("Респ")
                        || row.getCell(1).getStringCellValue().equals("край")
                        || row.getCell(1).getStringCellValue().equals("АО")
                        || row.getCell(1).getStringCellValue().equals("Аобл")
                        || (row.getCell(1).getStringCellValue().equals("г")
                        & (row.getCell(0).getStringCellValue().equals("Москва")
                        || row.getCell(0).getStringCellValue().equals("Санкт-Петербург")
                        || row.getCell(0).getStringCellValue().equals("Байконур")
                        || row.getCell(0).getStringCellValue().equals("Севастополь")))) {
                    String regionFormSubject = null;
                    switch (row.getCell(1).getStringCellValue()) {
                        case "обл":
                            regionFormSubject = "Область";
                            break;
                        case "край":
                            regionFormSubject = "Край";
                            break;
                        case "Респ":
                            regionFormSubject = "Республика";
                            break;
                        case "АО":
                            regionFormSubject = "Автономный округ";
                            break;
                        case "Аобл":
                            regionFormSubject = "Автономная область";
                            break;
                        case "г":
                            regionFormSubject = "Город";
                            break;
                    }
                    if (!regionRepository.existsRegionByName(row.getCell(0).getStringCellValue())) {
                        regionRepository.save(new Region(row.getCell(0).getStringCellValue(), row.getCell(2).getStringCellValue().substring(0, 2), regionFormSubject));
                    }
                }
                if (row.getCell(1).getStringCellValue().equals("г")
                        & !(row.getCell(0).getStringCellValue().equals("Москва")
                        || row.getCell(0).getStringCellValue().equals("Санкт-Петербург")
                        || row.getCell(0).getStringCellValue().equals("Байконур")
                        || row.getCell(0).getStringCellValue().equals("Севастополь"))) {

                    if (!cityRepository.existsCityByNameAndRegion(row.getCell(0).getStringCellValue(), regionRepository.findRegionByRegionNumber(row.getCell(2).getStringCellValue().substring(0, 2)))) {
                        cityRepository.save(new City(row.getCell(0).getStringCellValue(), regionRepository.findRegionByRegionNumber(row.getCell(2).getStringCellValue().substring(0, 2)), "Город"));
                    }
                }
            }
            fileInputStream.close();
        }
    }
}
