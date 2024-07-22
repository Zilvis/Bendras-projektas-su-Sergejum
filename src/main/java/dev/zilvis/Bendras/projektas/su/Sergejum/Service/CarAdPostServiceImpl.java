package dev.zilvis.Bendras.projektas.su.Sergejum.Service;

import dev.zilvis.Bendras.projektas.su.Sergejum.Model.CarAdPostEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Model.UserEntity;
import dev.zilvis.Bendras.projektas.su.Sergejum.Model.UserEntityRepository;
import dev.zilvis.Bendras.projektas.su.Sergejum.Repository.CarAdPostRepository;
import dev.zilvis.Bendras.projektas.su.Sergejum.Repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarAdPostServiceImpl implements CarAdPostService{

    @Autowired
    private CarAdPostRepository carAdPostRepository;
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public CarAdPostEntity createNewAd (CarAdPostEntity carAdPostEntity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        carAdPostEntity.setUserEntityEmail(myUserDetailService.getUserId(authentication));
        return carAdPostRepository.save(carAdPostEntity);
    }

    @Override
    public List<CarAdPostEntity> getAll(){
        return (List<CarAdPostEntity>) carAdPostRepository.findAll();
    }

    @Override
    public ResponseEntity<?> deleteById(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (carAdPostRepository.findById(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No car post with this id!");
        }

        if (myUserDetailService.isAdmin(authentication.getName())){
            carAdPostRepository.deleteById(id);
            return ResponseEntity.ok("Deleted!");
        }

        Optional<CarAdPostEntity> optionalCarAdPostEntity = carAdPostRepository.findById(id);
        Long userId = myUserDetailService.getUserId(authentication);
        CarAdPostEntity existingCarAdPostEntity = optionalCarAdPostEntity.get();
        Long carUserId = existingCarAdPostEntity.getUserEntityEmail();

        if (userId.equals(carUserId)){
            carAdPostRepository.deleteById(id);
            return ResponseEntity.ok("Deleted!");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own data");
    }

    @Override
    public ResponseEntity<?> updateById(CarAdPostEntity newCarAdPostEntity, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (carAdPostRepository.findById(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No car post with this id!");
        }

        Optional<CarAdPostEntity> optionalCarAdPostEntity = carAdPostRepository.findById(id);

        if (optionalCarAdPostEntity.isEmpty()) {
            return new ResponseEntity<>("Content not found", HttpStatus.NOT_FOUND);
        }

        Long userId = myUserDetailService.getUserId(authentication);
        CarAdPostEntity existingCarAdPostEntity = optionalCarAdPostEntity.get();

        if (!myUserDetailService.isAdmin(authentication.getName())) {
            if (!existingCarAdPostEntity.getUserEntityEmail().equals(userId)) {
                return new ResponseEntity<>("Unauthorized or userid do not match!", HttpStatus.UNAUTHORIZED);
            }
        }
        if (newCarAdPostEntity.getTitle() != null) {
            existingCarAdPostEntity.setTitle(newCarAdPostEntity.getTitle());
        }
        if (newCarAdPostEntity.getMake() != null) {
            existingCarAdPostEntity.setMake(newCarAdPostEntity.getMake());
        }
        if (newCarAdPostEntity.getModel() != null) {
            existingCarAdPostEntity.setModel(newCarAdPostEntity.getModel());
        }
        if (newCarAdPostEntity.getYear() != null) {
            existingCarAdPostEntity.setYear(newCarAdPostEntity.getYear());
        }
        if (newCarAdPostEntity.getMillage() != null) {
            existingCarAdPostEntity.setMillage(newCarAdPostEntity.getMillage());
        }
        if (newCarAdPostEntity.getPrice() != null) {
            existingCarAdPostEntity.setPrice(newCarAdPostEntity.getPrice());
        }
        if (newCarAdPostEntity.getFuelType() != null) {
            existingCarAdPostEntity.setFuelType(newCarAdPostEntity.getFuelType());
        }
        if (newCarAdPostEntity.getDescription() != null) {
            existingCarAdPostEntity.setDescription(newCarAdPostEntity.getDescription());
        }
        if (newCarAdPostEntity.getImage() != null){
            existingCarAdPostEntity.setImage(newCarAdPostEntity.getImage());
        }
        carAdPostRepository.save(existingCarAdPostEntity);

        return new ResponseEntity<>(carAdPostRepository.save(existingCarAdPostEntity), HttpStatus.OK);
    }

    @Override
    public List<String> getModelsAndCount() {

        Set<String>results = carAdPostRepository.findMakeCount();
        List<String> uResults = new ArrayList<>(results);

        return uResults;
    }

    @Override
    public Float getPrice(Boolean minOrMax){
        if (minOrMax){
            return carAdPostRepository.findMaxPrice();
        } else {
            return carAdPostRepository.findMinPrice();
        }
    }

    @Override
    public Object getOneById(Long id) {
        return carAdPostRepository.findById(id);
    }
}
