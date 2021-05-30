package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.dao.administration.BugReportDAO;
import com.univtln.univTlnLPS.model.administration.BugReport;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Bug report ressource.
 */
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
@Log
public class BugReportRessource {
    /**
     * Add bug report string.
     *
     * @param report the report
     * @return the string
     * @throws IllegalArgumentException the illegal argument exception
     */
    @PUT
    @Path("bugReports")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addBugReport(BugReport report) throws IllegalArgumentException {
        if (report.getId() != 0) throw new IllegalArgumentException();

        report.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        try (BugReportDAO bugReportDAO = BugReportDAO.of()) {
            EntityTransaction transaction = bugReportDAO.getTransaction();

            transaction.begin();
            bugReportDAO.persist(report);

            transaction.commit();
        }
        return "success";
    }

    /**
     * Get bug reports by cat ef list.
     *
     * @param category the category
     * @return the list
     */
    public static List<BugReport> getBugReportsByCatEF(String category){

        try (BugReportDAO bugReportDAO = BugReportDAO.of()) {

            return bugReportDAO.findByCat(category);
        }
    }

    /**
     * Gets bug reports cat.
     *
     * @return the bug reports cat
     * @throws NotFoundException the not found exception
     */
    @GET
    @Path("cat")
    public List<String> getBugReportsCat() throws NotFoundException {
        return BugReportDAO.of().findAllCat();
    }

    /**
     * Gets bug reports by cat.
     *
     * @param category the category
     * @param debut    the debut
     * @param fin      the fin
     * @return the bug reports by cat
     * @throws NotFoundException the not found exception
     * @throws ParseException    the parse exception
     */
    @GET
    @Path("bugReports")
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public Map<Long, BugReport> getBugReportsByCat(@QueryParam("category") String category,
                                                   @QueryParam("debut") String debut,
                                                   @QueryParam("fin") String fin) throws NotFoundException, ParseException {
        if (category == null)
            return BugReportDAO.of().findAll().stream()
                    .collect(Collectors.toMap(BugReport::getId, bugReport -> bugReport));

        if (debut != null && fin != null){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date dateDebut = sdf.parse(debut);
            Date dateFin = sdf.parse(fin);

            return BugReportDAO.of().findByDateBetweenByCat(dateDebut, dateFin, category).stream()
                    .collect(Collectors.toMap(BugReport::getId, bugReport -> bugReport));
        }

        return getBugReportsByCatEF(category).stream()
                .collect(Collectors.toMap(BugReport::getId, bugReport -> bugReport));
    }

    /**
     * Gets bug report.
     *
     * @param id the id
     * @return the bug report
     * @throws NotFoundException the not found exception
     */
    @GET
    @Path("bugReports/{id}")
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public BugReport getBugReport(@PathParam("id") long id) throws NotFoundException {
        BugReport report;
        try (BugReportDAO bugReportDAO = BugReportDAO.of()) {

            report = bugReportDAO.find(id);
            if(report == null) throw new NotFoundException();
        }
        return report;
    }

    /**
     * Gets bug reports size.
     *
     * @return the bug reports size
     */
    @GET
    @Path("bugReports/size")
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public int getBugReportsSize() {
        try (BugReportDAO bugReportDAO = BugReportDAO.of()) {
            return bugReportDAO.findAll().size();
        }
    }

    /**
     * Remove bug report string.
     *
     * @param id the id
     * @return the string
     * @throws NotFoundException the not found exception
     */
    @DELETE
    @Path("bugReports/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String removeBugReport(@PathParam("id") long id) throws NotFoundException {
        try (BugReportDAO bugReportDAO = BugReportDAO.of()) {
            EntityTransaction transaction = bugReportDAO.getTransaction();

            transaction.begin();
            BugReport report = bugReportDAO.find(id);
            if( report == null) throw new NotFoundException();
            bugReportDAO.remove(report);

            transaction.commit();
        }
        return "success";
    }

    /**
     * Delete bug reports string.
     *
     * @return the string
     */
    @DELETE
    @Path("bugReports")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String deleteBugReports() {
        try (BugReportDAO bugReportDAO = BugReportDAO.of()) {
            bugReportDAO.deleteAll();
        }

        return "success";
    }
}
