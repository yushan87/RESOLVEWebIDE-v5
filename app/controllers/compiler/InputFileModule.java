package controllers.compiler;

/* Libraries */
import edu.clemson.cs.r2jt.data.MetaFile;
import edu.clemson.cs.r2jt.data.ModuleKind;
import java.util.HashMap;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;

/**
 * TODO: Add JavaDocs for this class.
 *
 * @author Parker
 */
public class InputFileModule {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Map of all user created files.</p> */
    private HashMap<String, MetaFile> myUserFileMap;

    /** <p>Stack of user <code>MetaFile</code></p>. */
    private Stack<MetaFile> myUserMetaFiles;

    // ===========================================================
    // Constructors
    // ===========================================================

    public InputFileModule(MetaFile targetFile) {
        // Initialize all containers
        myUserMetaFiles = new Stack<MetaFile>();
        myUserFileMap = new HashMap<String, MetaFile>();

        // Store our MetaFile inside the Stack
        myUserMetaFiles.push(targetFile);
    }
    
    public InputFileModule(HttpServletRequest r, String fileSource, ModuleKind kind) {
        this(new MetaFile(r.getParameter("fileName"), r.getParameter("concept"),
                r.getParameter("pkg"), fileSource, kind));
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Create the MetaFile HashMap used to invoke the RESOLVE compiler
     * from the WebIDE.</p>
     */
    public void createMetaFiles() {
        // We run through the MetaFiles and add each file to the HashMap
        for (MetaFile mf : myUserMetaFiles) {
            String completePath = "";

            // Obtain the package path if not empty.
            if (!mf.getMyPkg().equals("")) {
                completePath += mf.getMyPkg() + ".";
            }

            // Add the filename to the complete path.
            completePath += mf.getMyFileName();

            // Add to our HashMap
            myUserFileMap.put(completePath, mf);
        }
    }

    /**
     * <p>Obtains the created map of user files.</p>
     *
     * @return HashMap of user files.
     */
    public HashMap getMetaFiles() {
        return myUserFileMap;
    }
}
