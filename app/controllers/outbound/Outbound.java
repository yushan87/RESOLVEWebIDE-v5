package controllers.outbound;

/**
 * TODO: Add JavaDocs for this class.
 */
public interface Outbound {

    // ===========================================================
    // Public Methods
    // ===========================================================

    public void sendErrors(String job, String errors);
    public void sendBugs(String job, String bugs);
    public void sendComplete(String job, String data);
    public void sendVcResult(Boolean result, String id, long proofDuration, long timeout);
}
