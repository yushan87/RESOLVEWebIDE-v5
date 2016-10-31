package controllers.bydesign.dataanalysis;

import models.common.database.User;
import java.io.File;
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
                    hasError = false;

                    // TODO: Do some logic to retrieve and display the data
                    File file = idFile.getFile();
                }
            }

            return ok(dataanalysis.render(currentUser, fileName, hasError));
        }

        return redirect(controllers.common.security.routes.Security.index());
    }

}