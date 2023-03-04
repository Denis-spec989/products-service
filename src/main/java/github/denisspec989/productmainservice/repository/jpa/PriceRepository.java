package github.denisspec989.productmainservice.repository.jpa;

import github.denisspec989.productmainservice.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PriceRepository extends JpaRepository<Price, UUID> {
}
