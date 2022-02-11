package pl.wsiz.foodservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsiz.foodservice.model.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByUserId(Long userId);

}
