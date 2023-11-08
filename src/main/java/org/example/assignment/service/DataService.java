package org.example.assignment.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.assignment.model.Data;
import org.example.assignment.model.UploadResult;
import org.example.assignment.repository.DataJpaRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataService {
    private final DataJpaRepository dataJpaRepository;

    public UploadResult upload(InputStream inputStream) {
        try (var reader = new InputStreamReader(inputStream)) {
            var parser = parser(reader);
            var saved = new AtomicInteger(0);
            var failed = new AtomicInteger(0);
            parser.stream().forEach(data -> {
                try {
                    dataJpaRepository.save(data);
                    saved.incrementAndGet();
                } catch (Exception e) {
                    log.error("Could not save record {}", data, e);
                    failed.incrementAndGet();
                }
            });
            return new UploadResult(saved.get(), failed.get());
        } catch (IOException e) {
            log.error("Could not open file", e);
            return new UploadResult(0, 0);
        }
    }

    public Optional<Data> findByCode(String code) {
        return dataJpaRepository.findById(code);
    }

    public Collection<Data> fetch() {
        return dataJpaRepository.findAll();
    }

    public boolean deleteAll() {
        try {
            dataJpaRepository.deleteAll();
            return true;
        } catch (Exception e) {
            log.error("Could not delete all data", e);
            return false;
        }
    }

    private static CsvToBean<Data> parser(InputStreamReader reader) {
        return new CsvToBeanBuilder<Data>(reader)
                .withType(Data.class)
                .withSeparator(',')
                .withQuoteChar('"')
                .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_QUOTES)
                .build();
    }
}
