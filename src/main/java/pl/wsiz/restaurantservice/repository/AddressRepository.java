package pl.wsiz.restaurantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wsiz.restaurantservice.model.Address;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByUserId(Long userId);
}
