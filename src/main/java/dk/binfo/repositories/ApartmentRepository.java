package dk.binfo.repositories;

import dk.binfo.models.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *  @author Patrick Klæbel
 *  @author Jens Bäckvall
 *  @author Steen Petersen
 *  @author Morten Olsen
 *
 */
@Repository("apartmentRepository")
public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {

}