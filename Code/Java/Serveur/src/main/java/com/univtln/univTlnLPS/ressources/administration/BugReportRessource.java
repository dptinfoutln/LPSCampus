package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.dao.administration.BugReportDAO;
import com.univtln.univTlnLPS.dao.administration.FormDevenirSuperDAO;
import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.dao.carte.PieceDAO;
import com.univtln.univTlnLPS.dao.scan.ScanDataDAO;
import com.univtln.univTlnLPS.model.administration.BugReport;
import com.univtln.univTlnLPS.model.administration.FormDevenirSuper;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
@Log
public class BugReportRessource {
    @PUT
    @Path("bugReports")
    @Consumes(MediaType.APPLICATION_JSON)
    public BugReport addBugReport(BugReport report) throws IllegalArgumentException {
        if (report.getId() != 0) throw new IllegalArgumentException();

        try (BugReportDAO bugReportDAO = BugReportDAO.of()) {
            EntityTransaction transaction = bugReportDAO.getTransaction();

            transaction.begin();
            bugReportDAO.persist(report);

            transaction.commit();
        }
        return report;
    }

    public static List<BugReport> getBugReportsByCatEF(String category){

        try (BugReportDAO bugReportDAO = BugReportDAO.of()) {

            return bugReportDAO.findByCat(category);
        }
    }

    @GET
    @Path("cat/bugReports")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Map<Long, BugReport> getBugReportsByCat(@QueryParam("category") String category) throws NotFoundException {
        return getBugReportsByCatEF(category).stream()
                .collect(Collectors.toMap(BugReport::getId, bugReport -> bugReport));
    }

    @GET
    @Path("bugReports/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public BugReport getBugReport(@PathParam("id") long id) throws NotFoundException {
        BugReport report;
        try (BugReportDAO bugReportDAO = BugReportDAO.of()) {

            report = bugReportDAO.find(id);
            if( report == null) throw new NotFoundException();
        }
        return report;
    }

    @GET
    @Path("bugReports/size")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public int getBugReportsSize() {
        try (BugReportDAO bugReportDAO = BugReportDAO.of()) {
            return bugReportDAO.findAll().size();
        }
    }

    @DELETE
    @Path("bugReports/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void removeBugReport(@PathParam("id") long id) throws NotFoundException {
        try (BugReportDAO bugReportDAO = BugReportDAO.of()) {
            EntityTransaction transaction = bugReportDAO.getTransaction();

            transaction.begin();
            BugReport report = bugReportDAO.find(id);
            if( report == null) throw new NotFoundException();
            bugReportDAO.remove(report);

            transaction.commit();
        }
    }

    @DELETE
    @Path("bugReports")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void deleteBugReports() {
        try (BugReportDAO bugReportDAO = BugReportDAO.of()) {
            bugReportDAO.deleteAll();
        }
    }
}
