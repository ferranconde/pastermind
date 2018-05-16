package Data;

/**
 * Ranking controller of the Persistence Layer
 */
public class CtrlRankingData extends CtrlGenericData
{
    /**
     * Self instance (Singleton pattern)
     */
    private static CtrlRankingData instance = new CtrlRankingData();


    /**
     * Initializes the Ranking controller
     */
    private CtrlRankingData()
    {
        super("Ranking");
    }

    /**
     * Gets the instance of the Ranking controller
     * @return Ranking controller instance
     */
    public static CtrlRankingData getInstance()
    {
        return instance;
    }
}
