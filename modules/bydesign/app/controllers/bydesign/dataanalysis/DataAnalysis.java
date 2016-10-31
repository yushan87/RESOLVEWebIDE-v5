package controllers.bydesign.dataanalysis;

import models.common.database.User;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import play.db.jpa.Transactional;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.bydesign.dataanalysis.dataanalysis;

/**
 * <p>This class serves as a controller class for analyzing
 * data retrieved from the database for the various users.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class DataAnalysis extends Controller {

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This renders the {@code byDesign} data analysis page.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    @Transactional(readOnly = true)
    public Result index() {
        // Retrieve the current user (if logged in)
        String email = session("connected");
        if(email != null) {
            User currentUser = User.findByEmail(email);

            return ok(dataanalysis.render(currentUser, "", false));
        }

        return redirect(controllers.common.security.routes.Security.index());
    }

    /**
     * <p>This handles file upload that contains the {@code byDesign}
     * user IDs and displays all relevant data from the database for
     * all the different IDs.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    @RequireCSRFCheck
    @Transactional(readOnly = true)
    public Result upload() {
        // Retrieve the current user (if logged in)
        String email = session("connected");
        if(email != null) {
            User currentUser = User.findByEmail(email);

            // Variables used to render the page
            String fileName = "";
            boolean hasError = true;

            // Retrieve the file that was posted to the backend
            MultipartFormData<File> body = request().body().asMultipartFormData();
            FilePart<File> idFile = body.getFile("idFile");
            if (idFile != null) {
                // Obtain the extension from the file
                // Note that we are disallowing file names that begin with "dot"
                String extension = "";
                fileName = idFile.getFilename();
                int lastDotIndex = fileName.lastIndexOf(".");
                if (lastDotIndex > 0) {
                    extension = fileName.substring(lastDotIndex, fileName.length());
                }

                // Only deal with CSV files
                String contentType = idFile.getContentType();
                if (!contentType.equals("application/vnd.ms-excel") || !extension.equals(".csv")) {
                    // Make sure that we render the error alert and
                    // don't display a file name as the file we are currently
                    // analyzing.
                    fileName = "";
                    hasError = true;
                }
                else {
                    try {
                        // Attempt to parse the ID file
                        List<Long> idList = parseIDFile(idFile.getFile());
                        System.out.println(idList);

                        // No error detected
                        hasError = false;
                    } catch (IOException | IllegalArgumentException e) {
                        // If we encounter any kind of exception, then we
                        // render the error alert and don't display a file name
                        // as the file we are currently analyzing.
                        fileName = "";
                        hasError = true;
                    }
                }
            }

            return ok(dataanalysis.render(currentUser, fileName, hasError));
        }

        return redirect(controllers.common.security.routes.Security.index());
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>This helper method converts a CSV file containing IDs (as a {@link String})
     * to a list of IDs as {@link Long}.</p>
     *
     * @param idFile The CSV file object containing the user IDs.
     *
     * @return A list of IDs as {@link Long}.
     *
     * @throws IOException This exception (or a more specific exception that inherits
     * from {@link IOException}) is thrown when an error occurs while reading the file.
     *
     * @throws IllegalArgumentException This exception (or a more specific exception that inherits
     * from {@link IllegalArgumentException}) is thrown when the file is not consistent with the
     * column headers or if we contain anything other than strings of long values.
     */
    private List<Long> parseIDFile(File idFile) throws IOException, IllegalArgumentException {
        List<Long> idList = new LinkedList<>();

        // We perform the following transformations to the provided CSVFormat
        // 1. Manually set the header to ID.
        // 2. We check to see if each line is consistent with the header provided.
        //    (ie. It is an error if it contains more values on the same line.)
        Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180
                .withHeader("ID").parse(new FileReader(idFile));
        for (CSVRecord record : csvRecords) {
            // Make sure we only have one column
            if (!record.isConsistent()) {
                throw new IllegalArgumentException();
            }

            idList.add(Long.parseLong(record.get("ID")));
        }

        return idList;
    }

}