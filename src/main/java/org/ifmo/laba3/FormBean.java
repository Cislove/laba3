package org.ifmo.laba3;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Named("formBean")
@ApplicationScoped
public class FormBean implements Serializable {
    private static final Logger logger = Logger.getLogger(FormBean.class.getName());

    private final double lowerRangeR = 1.0;
    private final double upperRangeR = 5.0;

    double x, y, r;
    @Inject
    private MyTable myTable;

    public void submit(){
        LocalDateTime now = LocalDateTime.now();
        Point point = new Point(x, y, r);
        if(validate(point)) {
            boolean hitting = checkHitting(x, y, r);
            myTable.addRow(new TableRow(
                    new Point(x, y, r),
                    hitting,
                    now
            ));
        }
    }

    public void clear(){
        this.x = 0;
        this.y = 0;
        this.r = 0;
        myTable.removeAllRow();
    }

    private boolean checkHitting(double x, double y, double r){
        if (x >= 0 && y >= 0){
            return y <= r - x;
        } else if (x <= 0 && y < 0){
            return x * x + y * y <= r * r / 4;
        } else if (x <= 0 && y >= 0){
            return x >= -r && y <= r;
        } else {
            return false;
        }
    }

    private boolean validate(Point point){
        return point.getR() >= lowerRangeR && point.getR() <= upperRangeR;
    }

    @PostConstruct
    public void init(){
        logger.info("FormBean init");
    }

    @PreDestroy
    public void destroy(){
        logger.info("destroy FormBean");
    }
}
