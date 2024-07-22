package dev.zilvis.Bendras.projektas.su.Sergejum.Controller;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.CarAdPostEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Repository.CarAdPostRepository;
import dev.zilvis.Bendras.projektas.su.Sergejum.Service.CarAdPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sort")
public class PaginationController {

    @Autowired
    private CarAdPostService carAdPostService;
    @Autowired
    private CarAdPostRepository carAdPostRepository;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) LocalDate yearFrom,
            @RequestParam(required = false) LocalDate yearTo,
            @RequestParam(required = false) Integer maxMillage,
            @RequestParam(required = false) Float priceFrom,
            @RequestParam(required = false) Float priceTo,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) Long userEntityEmail

    ) {
        // https://www.bezkoder.com/spring-boot-pagination-filter-jpa-pageable/
        try {
            Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

            Specification<CarAdPostEntity> spec = Specification.where(null);

            if (make != null && !make.isEmpty()) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("make"), make));
            }
            if (userEntityEmail != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.greaterThanOrEqualTo(root.get("userEntityEmail"), userEntityEmail));
            }
            if (model != null && !model.isEmpty()) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("model"), model));
            }
            if (yearFrom != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.greaterThanOrEqualTo(root.get("year"), yearFrom));
            }
            if (yearTo != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.lessThanOrEqualTo(root.get("year"), yearTo));
            }
            if (maxMillage != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.lessThanOrEqualTo(root.get("millage"), maxMillage));
            }
            if (priceFrom != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceFrom));
            }
            if (priceTo != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceTo));
            }
            if (fuelType != null && !fuelType.isEmpty()) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("fuelType"), fuelType));
            }

            Page<CarAdPostEntity> carPage = carAdPostRepository.findAll(spec, paging);
            List<CarAdPostEntity> carList = carPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("carPost", carList);
            response.put("currentPage", carPage.getNumber());
            response.put("totalItems", carPage.getTotalElements());
            response.put("totalPages", carPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
