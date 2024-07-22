package dev.zilvis.Bendras.projektas.su.Sergejum.Controller;

import dev.zilvis.Bendras.projektas.su.Sergejum.DTO.Filter;
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
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestBody(required = false) Filter filter

    ) {
        try {
            Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

            Specification<CarAdPostEntity> spec = Specification.where(null);

            if (filter != null) {
                if (filter.getMake() != null && !filter.getMake().isEmpty()) {
                    spec = spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("make"), filter.getMake()));
                }
                if (filter.getModel() != null && !filter.getModel().isEmpty()) {
                    spec = spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("model"), filter.getModel()));
                }
                if (filter.getYear() != null) {
                    spec = spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.greaterThanOrEqualTo(root.get("year"), filter.getYear()));
                }
                if (filter.getYear() != null) {
                    spec = spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.lessThanOrEqualTo(root.get("year"), filter.getYear()));
                }
                if (filter.getMillage() != 0) {
                    spec = spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.lessThanOrEqualTo(root.get("millage"), filter.getMillage()));
                }
                if (filter.getMinPrice() != 0) {
                    spec = spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
                }
                if (filter.getMaxPrice() != 0) {
                    spec = spec.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
                }
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
