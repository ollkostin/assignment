package org.example.assignment.model;

import com.opencsv.bean.CsvDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@lombok.Data
@Entity
@NoArgsConstructor
public class Data {
    @Id
    private String code;
    private String source;
    private String codeListCode;
    private String displayValue;
    private String longDescription;
    @CsvDate(value="dd-MM-yyyy")
    private LocalDate fromDate;
    @CsvDate(value="dd-MM-yyyy")
    private LocalDate toDate;
    private Integer sortingPriority;


}
