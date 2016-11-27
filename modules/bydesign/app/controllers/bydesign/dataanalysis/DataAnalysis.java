/**
 * ---------------------------------
 * Copyright (c) 2016
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package controllers.bydesign.dataanalysis;

import models.common.database.ByDesignEvent;
import models.common.database.User;
import java.io.*;
import java.util.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import play.db.jpa.Transactional;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import utils.bydesign.dataanalysis.ErrorKind;
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
        if (email != null) {
            User currentUser = User.findByEmail(email);

            return ok(dataanalysis.render(currentUser, "", null, new HashMap<>(), null));
        }

        return redirect(controllers.common.security.routes.Security.index());
    }

    /**
     * <p>This retrieves the user code associated with the specified
     * {@code byDesign} event.</p>
     *
     * @param eventID The ID for a {@code byDesign} event.
     *
     * @return The code associated with the event.
     */
    @Transactional(readOnly = true)
    public Result getCode(long eventID) {
        // Retrieve the current user (if logged in)
        String email = session("connected");
        if (email != null) {
            return ok(ByDesignEvent.getUserEventCode(eventID));
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
        if (email != null) {
            User currentUser = User.findByEmail(email);

            // Variables used to render the page
            String fileName = "";
            ErrorKind errorKind = null;
            Date lastGeneratedDate = null;
            Map<Long, List<ByDesignEvent>> eventsMap = new HashMap<>();

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
                if (!isValidInput(contentType, extension)) {
                    // Make sure that we render the error alert and
                    // don't display a file name as the file we are currently
                    // analyzing.
                    fileName = "";
                    errorKind = ErrorKind.INVALID_INPUT_FILE;
                }
                else {
                    try {
                        // Attempt to parse the ID file
                        List<Long> idList = parseIDFile(idFile.getFile());
                        eventsMap = formEventsMap(idList);

                        /* Temporary code to export the data
                        String csvFile = "data.csv";
                        List<String> lessons = Arrays.asList("tutorial/tutorial1.json", "tutorial/tutorial2.json", "problems/problem1.json",
                                "problems/problem1a.json", "problems/problem1b.json", "problems/problem2.json", "problems/problem2a.json",
                                "problems/problem3.json", "problems/problem3a.json", "problems/problem3aa.json", "problems/problem3b.json",
                                "problems/problem4.json", "problems/problem4a.json", "problems/problem5.json", "problems/problem6.json",
                                "challenges/challenge1.json", "challenges/challenge2.json");
                        FileWriter writer = new FileWriter(csvFile);
                        List<String> topBar = new ArrayList<>(lessons.size());
                        topBar.addAll(lessons);
                        topBar.add(0, "AuthorID");
                        CSVUtils.writeLine(writer, topBar);
                        for (Long id : eventsMap.keySet()) {
                            List<Integer> data = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                            for (ByDesignEvent bde : eventsMap.get(id)) {
                                boolean found = false;
                                for (int i = 0; i < lessons.size() && !found; i++) {
                                    if (bde.lesson.equals(lessons.get(i))) {
                                        data.set(i, data.get(i) + 1);
                                        found = true;
                                    }
                                }
                            }

                            // Specify the size of the list up front to prevent resizing.
                            List<String> newList = new ArrayList<>(data.size());
                            newList.addAll(data.stream().map(String::valueOf).collect(Collectors.toList()));
                            newList.add(0, id.toString());
                            CSVUtils.writeLine(writer, newList);
                        }
                        writer.flush();
                        writer.close();*/

                        // No error detected
                        lastGeneratedDate = new Date();
                    } catch (IOException | IllegalArgumentException e) {
                        // If we encounter any kind of exception, then we
                        // render the error alert and don't display a file name
                        // as the file we are currently analyzing.
                        fileName = "";
                        errorKind = ErrorKind.DATABASE_ERROR;
                    }
                }
            }

            return ok(dataanalysis.render(currentUser, fileName, errorKind, eventsMap, lastGeneratedDate));
        }

        return redirect(controllers.common.security.routes.Security.index());
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>Returns a map containing all the events we can find for each
     * of the provided users.</p>
     *
     * @param idList The list of user IDs.
     *
     * @return A map of from {@link Long} user IDs to {@link List<ByDesignEvent>}.
     */
    @Transactional(readOnly = true)
    private Map<Long, List<ByDesignEvent>> formEventsMap(List<Long> idList) {
        Map<Long, List<ByDesignEvent>> userEventsMap = new HashMap<>();
        for (Long id : idList) {
            userEventsMap.put(id, ByDesignEvent.getUserEvents(id));
        }

        return userEventsMap;
    }

    /**
     * <p>This performs basic checks on the input file.</p>
     *
     * @param contentType The content type specified by the request.
     * @param extension The file extension.
     *
     * @return {@code true} if it is a valid input file, {@code false} otherwise.
     */
    private boolean isValidInput(String contentType, String extension) {
        // Check to see if it is a ".csv" extension
        boolean result = extension.equals(".csv");

        // Only check the content type if it is a csv file.
        if (result) {
            // Check to see if the content type is either "application/vnd.ms-excel"
            // if the client is using Windows or "text/csv" on MacOS/Linux.
            result = contentType.equals("application/vnd.ms-excel") || contentType.equals("text/csv");
        }

        return result;
    }

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

    /*
     * <p>Source: https://www.mkyong.com/java/how-to-export-data-to-csv-file-java/</p>
     *
     * Note: In the future, we might need to write our own.
     *
    static class CSVUtils {

        private static final char DEFAULT_SEPARATOR = ',';

        static void writeLine(Writer w, List<String> values) throws IOException {
            writeLine(w, values, DEFAULT_SEPARATOR, ' ');
        }

        static void writeLine(Writer w, List<String> values, char separators) throws IOException {
            writeLine(w, values, separators, ' ');
        }

        //https://tools.ietf.org/html/rfc4180
        private static String followCVSformat(String value) {

            String result = value;
            if (result.contains("\"")) {
                result = result.replace("\"", "\"\"");
            }
            return result;

        }

        static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

            boolean first = true;

            //default customQuote is empty

            if (separators == ' ') {
                separators = DEFAULT_SEPARATOR;
            }

            StringBuilder sb = new StringBuilder();
            for (String value : values) {
                if (!first) {
                    sb.append(separators);
                }
                if (customQuote == ' ') {
                    sb.append(followCVSformat(value));
                } else {
                    sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
                }

                first = false;
            }
            sb.append("\n");
            w.append(sb.toString());


        }

    }*/

}