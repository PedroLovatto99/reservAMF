package reservAMF.Reservas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<ReservaModel, Long> {

    @EntityGraph(attributePaths = "sala")
    Page<ReservaModel> findAll(Pageable pageable);

}
