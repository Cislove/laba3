package org.ifmo.laba3;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Named("table")
@ApplicationScoped
public class MyTable implements Serializable {
    @Inject
    private ConnectionDB connection;
    private static final Logger logger = Logger.getLogger(MyTable.class.getName());
    private List<TableRow> points = new ArrayList<>();

    public List<TableRow> getPoints() {
        return this.connection.getAllRecords();
    }

    @PostConstruct
    public void init() {
        logger.info("PointList init");
    }

    public void addRow(TableRow row) {
        this.connection.saveTableRow(row);
    }

    public void removeAllRow() {
        this.connection.removeAllRecords();
    }
}
