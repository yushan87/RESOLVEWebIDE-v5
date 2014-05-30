package controllers.outbound;

/* Libraries */
import controllers.ControllerUtils;
import play.mvc.WebSocket;

/**
 * TODO: Add JavaDocs for this class.
 */
public class WsOutbound implements Outbound {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>WebSocket that we send our messages to.</p>*/
    private WebSocket.Out<String> myOutSocket;

    // ===========================================================
    // Constructors
    // ===========================================================

    public WsOutbound(WebSocket.Out<String> socket) {
        myOutSocket = socket;
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
        String msg = "{\"job\":\"" + job + "\",";
        msg += "\"status\":\"error\",\"type\":\"error\",\"errors\":[{";
        msg += errors;
        msg += "}]}";
        myOutSocket.write(msg);
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
        String msg = "{\"job\":\"" + job + "\",";
        msg += "\"status\":\"error\",\"type\":\"bug\",\"bugs\":[{";
        msg += bugs;
        msg += "}]}";
        myOutSocket.write(msg);
    }

    /**
     * <p>Sends the complete (and encoded) result to the WebSocket client.</p>
     *
     * @param job ID for the job we are executing.
     * @param data The data returned by the compiler.
     */
    @Override
    public void sendComplete(String job, String data) {
        String msg = "{\"job\":\"" + job + "\",";
        msg += "\"status\":\"complete\",\"result\":\"";
        msg += ControllerUtils.encode(data);
        msg += "\"}";
        myOutSocket.write(msg);
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
        String msg = "{\"job\":\"verify\",\"status\":\"processing\",";
        msg += "\"result\":{\"id\":\"" + id + "\",\"result\":\"";
        if (result) {
            msg += "Proved, " + proofDuration + " ms";
        }
        else {
            if (proofDuration < timeout) {
                msg += "Unable to prove, " + proofDuration + " ms";
            }
            else {
                msg += "Timeout after " + timeout + " ms";
            }
        }
        msg += "\"}";
        msg += "}";
        myOutSocket.write(msg);
    }
}