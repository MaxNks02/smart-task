package com.example.smartbank_task_webflux.service;

import com.example.smartbank_task_webflux.dao.dto.CityDto;
import com.example.smartbank_task_webflux.dao.mapper.CityMapper;
import com.example.smartbank_task_webflux.dao.model.City;
import com.example.smartbank_task_webflux.dao.repo.CityRepo;
import com.example.smartbank_task_webflux.exception.CustomInternalServerException;
import com.example.smartbank_task_webflux.exception.handler.ApiErrorMessages;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest()
class CityServiceTest {


    @Autowired
    private CityService cityService;

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private CityMapper cityMapper;

    private final String pathToFile = "C:\\abbos\\myFile0.CSV";

    private final String baseUrl = "https://localhost:8081";


    //positive tests


    @Test
    public void saveAll() throws IOException {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.CSV",
                MediaType.TEXT_PLAIN_VALUE,
                fileBytes()
        );
        List<CityDto> cityDtoList = CvsReader(file.getInputStream());
        Flux<City> fluxCity = cityService.saveAll(Mono.just(file.getInputStream()));
        List<City> cityList = fluxCity.collectList().block();
        assertNotNull(fluxCity);
        assertEquals(cityDtoList.size(), cityDtoList.size());
        assertEquals(cityDtoList.get(0).getName(), cityList.get(0).getName());
        assertEquals(cityDtoList.get(1).getName(), cityList.get(1).getName());
        assertEquals(cityDtoList.get(2).getName(), cityList.get(2).getName());
        assertEquals(cityDtoList.get(3).getName(), cityList.get(3).getName());

    }

    @Test
    public void shouldSaveSingleCity() {

        CityDto cityDto = CityDto.builder()
                .name("Nukus")
                .number(1234L)
                .build();
        StepVerifier.create(cityService.create(cityDto)).consumeNextWith(newCityDto -> {
            assertEquals(newCityDto.getName(), "Nukus");
            assertEquals(newCityDto.getNumber(), 1234L);
        }).verifyComplete();
    }

    @Test
    public void getByIdCity() {

        long id = 3493L;
        CityDto cityDtoDb = cityRepo.findById(id).map(cityMapper::toDto).block();
        StepVerifier.create(cityService.getById(id)).consumeNextWith(newCityDto -> {
            assertNotNull(newCityDto);
            assertEquals(newCityDto.getNumber(), cityDtoDb.getNumber());
            assertEquals(newCityDto.getName(), cityDtoDb.getName());
        }).verifyComplete();
    }

    @Test
    public void getAll() {

        List<City> cityList = cityRepo.findAll().collectList().block();

        Flux<CityDto> fluxCity = cityService.getAll();
        List<CityDto> cityDtoList = fluxCity.collectList().block();
        assertNotNull(fluxCity);
        assertEquals(cityDtoList.size(), cityList.size());
        assertEquals(cityList.get(0).getName(), cityDtoList.get(0).getName());
        assertEquals(cityList.get(1).getName(), cityDtoList.get(1).getName());
        assertEquals(cityList.get(2).getName(), cityDtoList.get(2).getName());
        assertEquals(cityList.get(3).getName(), cityDtoList.get(3).getName());

    }


    @Test
    public void sortByNumber() throws IOException {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.CSV",
                MediaType.TEXT_PLAIN_VALUE,
                fileBytes()
        );
        Flux<CityDto> fluxCityDto = cityService.sortByCode(Mono.just(file.getInputStream()));
        List<CityDto> cityDtoList = CvsReader(file.getInputStream());
        List<CityDto> sortedDtoList = fluxCityDto.collectList().block();
        assertNotNull(fluxCityDto);
        assertEquals(cityDtoList.size(), sortedDtoList.size());
        assertEquals(sortedDtoList.get(0).getNumber(), 102);
        assertEquals(sortedDtoList.get(1).getNumber(), 119);
        assertEquals(sortedDtoList.get(2).getNumber(), 133);
        assertEquals(sortedDtoList.get(3).getNumber(), 141);
    }

    @Test
    public void sortByName() throws IOException {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.CSV",
                MediaType.TEXT_PLAIN_VALUE,
                fileBytes()
        );
        Flux<CityDto> fluxCityDto = cityService.sortByName(Mono.just(file.getInputStream()));
        List<CityDto> cityDtoList = CvsReader(file.getInputStream());
        List<CityDto> sortedDtoList = fluxCityDto.collectList().block();
        assertNotNull(fluxCityDto);
        assertEquals(cityDtoList.size(), sortedDtoList.size());
        assertEquals(sortedDtoList.get(0).getNumber(), 355679);
        assertEquals(sortedDtoList.get(1).getNumber(), 60507);
        assertEquals(sortedDtoList.get(2).getNumber(), 427972);

    }


    //negative test

    // THERE WAS BUG THAT assertThatExceptionOfType DOES NOT CATCH ERROR OR EX, SO I DECIDED TO DO TEST WITH WEB CLIENT

//    @Test
//    public void shouldSaveSingleCityThrowError() {
//
//        StepVerifier.create(cityService.create(CityDto.builder().number(1245L).build())).
//                expectErrorMatches(throwable -> throwable instanceof DatabaseException &&
//                        throwable.getMessage().equals("Error occurs while exchange data with database. Please try again later, after checking given error details. ")).verify();
//
//    }
//
//
//    @Test
//    public void getByIdCityThrowNotFound() {
//
//        long id = 21321;
//        assertThatExceptionOfType(DatabaseException.class).isThrownBy(() -> StepVerifier.create(cityService.getById(id)))
//                .withMessageContaining("Could not find requesting data. ");
//
//    }


    private byte[] fileBytes() {
        File file = new File(pathToFile);
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private List<CityDto> CvsReader(InputStream is) {
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
            return cityDtoList;
        } catch (IOException e) {
            throw new CustomInternalServerException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s" + e.getMessage() + " %s", "File cannot be empty!"));
        }
    }


}


