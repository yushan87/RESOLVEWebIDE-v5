package controllers.compiler;

/* Libraries */
import controllers.outbound.Outbound;
import controllers.outbound.WsOutbound;
import edu.clemson.cs.r2jt.proving2.Metrics;
import edu.clemson.cs.r2jt.proving2.ProverListener;
import edu.clemson.cs.r2jt.proving2.model.PerVCProverModel;

/**
 * TODO: Add JavaDocs for this class.
 *
 * @author Chuck
 */
public class WsListener implements ProverListener {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** Outbound Message Sender */
    private Outbound myOutbound;

    // ===========================================================
    // Constructors
    // ===========================================================

    public WsListener(WsOutbound outbound) {
        myOutbound = outbound;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Progress updates. (Currently not supported by our prover)</p>
     *
     * @param v Some value returned by our prover.
     */
    @Override
    public void progressUpdate(double v) { }

    /**
     * <p>Results returned by the prover.</p>
     *
     * @param b Proved/Not Proved.
     * @param perVCProverModel VC Model.
     * @param metrics Metrics information provided by the Prover.
     */
    @Override
    public void vcResult(boolean b, PerVCProverModel perVCProverModel, Metrics metrics) {
        myOutbound.sendVcResult(b, perVCProverModel.getTheoremName(),
                metrics.getProofDuration(), metrics.getTimeout());
    }
}