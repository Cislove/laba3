/**
 * The {@code ConnectionDB} class is responsible for managing database operations
 * such as saving, retrieving, and removing records. It uses JPA (Jakarta Persistence API)
 * for interacting with the database and is annotated as an application-scoped bean.
 * 
 * This class provides the following functionalities:
 * - Saving a {@link TableRow} entity to the database.
 * - Retrieving all {@link TableRow} records from the database.
 * - Removing all records from the database.
 * 
 * It uses the {@link EntityManager} for database operations and employs
 * Jakarta Transactions for ensuring transactional integrity.
 * 
 * Annotations used:
 * - {@link ApplicationScoped} - Ensures a single instance of this class is used
 *   throughout the application.
 * - {@link Named} - Makes this bean accessible in Jakarta Expression Language (EL).
 * - {@link PostConstruct} - Initializes the bean after dependency injection.
 * - {@link Transactional} - Ensures methods are executed within a transaction.
 * 
 * Logging is used to track the initialization and any errors during database operations.
 * 
 * @author Рахимов Ильнар Ильдарович
 * @version 1.0
 */
package org.ifmo.laba3;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Getter
@Setter
@ApplicationScoped
@Named("connectionDB")
public class ConnectionDB implements Serializable {
    private static final Logger logger = Logger.getLogger(ConnectionDB.class.getName());

    @PersistenceContext(unitName = "records")
    private EntityManager entityManager;
    private final String REMOVE_RECORDS = "DELETE FROM TableRow r";

    @PostConstruct
    public void init() {
        logger.info("ConnectionDB init, Entity manager: " + entityManager);
    }

    @Transactional
    public void saveTableRow(TableRow tableRow) {
        entityManager.persist(tableRow);
    }

    @Transactional
    public List<TableRow> getAllRecords() {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<TableRow> query = cb.createQuery(TableRow.class);
            Root<TableRow> root = query.from(TableRow.class);
            query.select(root);
            return entityManager.createQuery(query).getResultList();
        } catch (PersistenceException e) {
            logger.severe("Error retrieving records: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional
    public void removeAllRecords() {
        entityManager.createQuery(REMOVE_RECORDS).executeUpdate();
    }
}
