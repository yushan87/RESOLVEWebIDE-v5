package controllers.outbound;

/**
 * TODO: Add JavaDocs for this class.
 *
 */
public class PostOutbound implements Outbound {

    // ===========================================================
    // Global Variables
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    public PostOutbound() {

    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Sends the error message given by the compiler to the WebSocket
     * client.</p>
     *
     * @param job ID for the job we are executing.
     * @param errors The error message returned by the compiler.
     */
    @Override
    public void sendErrors(String job, String errors) {

    }

    /**
     * <p>Sends bug messages given by the compiler to the WebSocket
     * client.</p>
     *
     * @param job ID for the job we are executing.
     * @param bugs The bug message returned by the compiler.
     */
    @Override
    public void sendBugs(String job, String bugs) {

    }

    /**
     * <p>Sends the complete (and encoded) result to the WebSocket client.</p>
     *
     * @param job ID for the job we are executing.
     * @param data The data returned by the compiler.
     */
    @Override
    public void sendComplete(String job, String data) {

    }

    /**
     * <p>Sends the results from the prover to the WebSocket client.</p>
     *
     * @param result Proved/Not Proved.
     * @param id VC Number.
     * @param proofDuration Time used by the prover.
     * @param timeout The timeout set by the client.
     */
    @Override
    public void sendVcResult(Boolean result, String id, long proofDuration, long timeout) {

    }
}
