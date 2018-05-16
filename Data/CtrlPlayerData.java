package Data;

/**
 * Player controller of the Persistence Layer
 */
public class CtrlPlayerData extends CtrlGenericData
{
    /**
     * Self instance (Singleton pattern)
     */
    private static CtrlPlayerData instance = new CtrlPlayerData();


    /**
     * Initializes the Player controller
     */
    private CtrlPlayerData()
    {
        super("Player");
    }

    /**
     * Gets the instance of the Player controller
     * @return Player controller instance
     */
    public static CtrlPlayerData getInstance()
    {
        return instance;
    }
}
