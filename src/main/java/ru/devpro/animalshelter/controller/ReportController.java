package ru.devpro.animalshelter.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.devpro.animalshelter.core.entity.ReportEntity;
import ru.devpro.animalshelter.service.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(
            summary = "Поиск отчета по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportEntity.class)
                            )
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<ReportEntity> findReport(
            @Parameter(description = "Идентификатор отчета", example = "1")
            @PathVariable long id) {

        ReportEntity report = reportService.findById(id);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }
}
