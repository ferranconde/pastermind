package Data;

/**
 * Game controller of the Persistence Layer
 */
public class CtrlGameData extends CtrlGenericData
{
    /**
     * Self instance (Singleton pattern)
     */
    private static CtrlGameData instance = new CtrlGameData();


    /**
     * Initializes the Game controller
     */
    private CtrlGameData()
    {
        super("Game");
    }

    /**
     * Gets the instance of the Game controller
     * @return Game controller instance
     */
    public static CtrlGameData getInstance()
    {
        return instance;
    }
}
