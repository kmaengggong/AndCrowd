package com.fiveis.andcrowd.entity.etc;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = -786619450L;

    public static final QReport report = new QReport("report");

    public final NumberPath<Integer> projectId = createNumber("projectId", Integer.class);

    public final NumberPath<Integer> projectType = createNumber("projectType", Integer.class);

    public final StringPath reportContent = createString("reportContent");

    public final DateTimePath<java.time.LocalDateTime> reportDate = createDateTime("reportDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> reportId = createNumber("reportId", Integer.class);

    public final NumberPath<Integer> reportStatus = createNumber("reportStatus", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QReport(String variable) {
        super(Report.class, forVariable(variable));
    }

    public QReport(Path<? extends Report> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReport(PathMetadata metadata) {
        super(Report.class, metadata);
    }

}

