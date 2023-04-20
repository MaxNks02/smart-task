package com.example.smartbank_task_webflux.service;

import com.example.smartbank_task_webflux.dao.dto.CityDto;
import com.example.smartbank_task_webflux.dao.mapper.CityMapper;
import com.example.smartbank_task_webflux.dao.model.City;
import com.example.smartbank_task_webflux.dao.repo.CityRepo;
import com.example.smartbank_task_webflux.exception.BadRequestException;
import com.example.smartbank_task_webflux.exception.CustomInternalServerException;
import com.example.smartbank_task_webflux.exception.DatabaseException;
import com.example.smartbank_task_webflux.exception.handler.ApiErrorMessages;
import com.google.gson.Gson;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CityService extends BaseService<CityRepo, City, CityDto, CityMapper> {

    private final Logger LOG = LoggerFactory.getLogger(CityService.class);
    private final Gson gson;

    public CityService(CityRepo repository, @Qualifier("cityMapperImpl") CityMapper mapper, Gson gson) {
        super(repository, mapper);
        this.gson = gson;
    }


    public Flux<CityDto> sortByName(Mono<InputStream> is) {

        return getCityDtoFlux(is).sort(Comparator.comparing(CityDto::getName));
    }

    public Flux<CityDto> sortByCode(Mono<InputStream> is) {

        return getCityDtoFlux(is).sort(Comparator.comparing(CityDto::getNumber));
    }

    @Transactional
    public Flux<City> saveAll(Mono<InputStream> file) {
        return getRepository().saveAll(getCityDtoFlux(file).map(getMapper()::toEntity))
                .onErrorMap(throwable -> new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", throwable.getMessage())));

    }


    private Flux<CityDto> getCityDtoFlux(Mono<InputStream> is) {

        // DataBuffer reading only 1024 bytes

//        Flux<List<CityDto>> fileBytes = file.content().map(dataBuffer -> {
//            System.out.println(dataBuffer.capacity());
//            byte[] bytes = new byte[dataBuffer.readableByteCount()];
//            dataBuffer.read(bytes);
//            DataBufferUtils.release(dataBuffer);
//            return CvsReader(new ByteArrayInputStream(bytes));
//        });
//            List<CityDto> cityDtoList = CvsReader(new FileInputStream(file2));
//

        return is.map(this::CvsReader).
                flatMapMany(Flux::fromIterable);
    }


    private List<CityDto> CvsReader(InputStream is) {
        try {
            if (is.available() == 0) {
                LOG.error("RESPONSE FROM INDIVIDUAL DETAILS: {} \t\t  ", gson.toJson("File is Empty || CityService.class"));
                throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + " %s", "File cannot be empty!"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<CityDto> cityDtoList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {

                CityDto cityDto = new CityDto(
                        Long.parseLong(csvRecord.get("city_code")),
                        csvRecord.get("city")
                );
                cityDtoList.add(cityDto);

            }
            LOG.info("RESPONSE FROM INDIVIDUAL DETAILS: {} \t\t  ", gson.toJson("Converted to Dto list from File || CityService.class"));
            return cityDtoList;
        } catch (IOException e) {
            LOG.error("RESPONSE FROM INDIVIDUAL DETAILS: {} \t\t  ", gson.toJson("Error" + e.getMessage() + "  || CityService.class"));
            throw new CustomInternalServerException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s" + e.getMessage() + " %s"));
        }
    }


    @Override
    public Mono<CityDto> update(CityDto input, Long id) {
        return null;
    }
}
