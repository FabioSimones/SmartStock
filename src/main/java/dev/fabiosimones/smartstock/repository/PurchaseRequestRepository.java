package dev.fabiosimones.smartstock.repository;

import dev.fabiosimones.smartstock.entity.PurchaseRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseRequestRepository extends MongoRepository<PurchaseRequestEntity, String> {
}
