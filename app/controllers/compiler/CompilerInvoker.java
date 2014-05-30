package controllers.compiler;

/* Libraries */
import controllers.outbound.Outbound;
import edu.clemson.cs.r2jt.ResolveCompiler;
import edu.clemson.cs.r2jt.compilereport.CompileReport;

/**
 * TODO: Add JavaDocs for this class.
 *
 * @author Parker
 */
public class CompilerInvoker {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>ResolveCompiler Instance</p> */
    private ResolveCompiler myRC;

    /** <p>List of arguments we use to invoke the compiler.</p> */
    private String[] myCompileArgs;

    /** <p>Temporary working directory for the compiled Java files</p> */
    private String myTempWsDir;

    /** <p>Outbound Message Sender</p> */
    private Outbound myOutbound;

    /** <p>Listener for the prover</p> */
    private WsListener myListener;

    // ===========================================================
    // Constructors
    // ===========================================================

    public CompilerInvoker(ResolveCompiler rc, String[] a, String twd,
                           Outbound outbound, WsListener listener) {
        myRC = rc;
        myCompileArgs = a;
        myTempWsDir = twd;
        myOutbound = outbound;
        myListener = listener;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Generates a Jar executable file with the filename provided
     * using the RESOLVE compiler.</p>
     *
     * @param job Name of the job we are running.
     * @param filename Name of the file we are compiling.
     */
    public void generateFacilityJar(String job, String filename) {
        boolean result = runCompiler(job);

        if (!result) {
            StringBuilder resultMsg = new StringBuilder("{");
            resultMsg.append("\"jarName\":\"");
            resultMsg.append(filename);
            resultMsg.append("\",\"downloadDir\":\"");
            resultMsg.append(myTempWsDir);
            resultMsg.append("\"}");
            myOutbound.sendComplete(job, resultMsg.toString());
        }
    }

    /**
     * <p>Generates Java code for the file with the filename provided
     * using the RESOLVE compiler.</p>
     *
     * @param job Name of the job we are running.
     */
    public void generateJava(String job) {
        boolean result = runCompiler(job);

        // Send complete message to client if we don't have any errors.
        if (!result) {
            myOutbound.sendComplete(job, myRC.getReport().getOutput());
        }
    }

    /**
     * <p>Generates VCs for the file with the filename provided using the
     * RESOLVE compiler.</p>
     *
     * @param job Name of the job we are running.
     */
    public void generateVCs(String job) {
        boolean result = runCompiler(job);

        // Send complete message to client if we don't have any errors.
        if (!result) {
            myOutbound.sendComplete(job, myRC.getReport().getOutput());
        }
    }

    /**
     * <p>Verifies the file with the filename provided with the
     * RESOLVE compiler.</p>
     *
     * @param job Name of the job we are running.
     */
    public void verifyResolve(String job) {
        boolean result = runCompiler(job);

        // Send complete message to client if we don't have any errors.
        if (!result) {
            myOutbound.sendComplete(job, myRC.getReport().getOutput());
        }
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>Runs the RESOLVE Compiler.</p>
     *
     * @param job Name of the job we are running.
     *
     * @return True if it contains either an error or a bug report.
     */
    private boolean runCompiler(String job) {
        // Run the compiler
        try {
            myRC.compile(myCompileArgs, myListener);
        }
        catch (Exception ex) {
            // Not handled at the moment.
        }

        // Results from the RESOLVE Compiler
        CompileReport cr = myRC.getReport();

        // Check the compile environment for errors.
        if (cr.hasErrors()) {
            myOutbound.sendErrors(job, cr.getErrors());
        }
        // Check the compile environment for bugs reports.
        else if (cr.hasBugReports()) {
            myOutbound.sendBugs(job, cr.getBugReports());
        }

        return (cr.hasError() | cr.hasBugReports());
    }
}
