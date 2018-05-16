package Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic Controller of the Persistence Layer. Has all the main functionality used by its subclasses
 */
public abstract class CtrlGenericData
{
    /**
     * Name of the Folder where the controller will store its data
     */
    private final String folderName;


    /**
     * Creates a new controller and assigns the correct folder name to it
     * @param folderName Name of the folder where it will be created
     */
    CtrlGenericData(String folderName)
    {
        this.folderName = folderName;
    }

    /**
     * Creates all the folder structure required to save the data and an empty file
     * @param fileName Name of the file to create
     */
    private void createRootStructure(String fileName)
    {
        File catiFile = new File("Game/Data/" +
                folderName + "/" +
                fileName);
        try
        {
            catiFile.getParentFile().mkdirs();
            catiFile.createNewFile();
        } catch (IOException e)
        {
            //TODO: Handle proper exception check
            e.printStackTrace();
        }

    }

    /**
     * Retrieves an Object from the file system
     * @param identifier Name of the saved file
     * @return Object with the contents deserialized
     * @throws ClassNotFoundException The file is outdated and cannot be loaded
     * @throws IOException Couldn't read the data file because it's either locked or it doesn't have enough
     * permissions
     */
    public Object get(String identifier) throws ClassNotFoundException, IOException
    {
        if (identifier.contains(".kys"))
            identifier = identifier.replace(".kys", "");
        File file = new File("Game/Data/" +
                folderName + "/" +
                identifier + ".kys");

        if (!file.exists())
            return null;

        return new ObjectInputStream(new FileInputStream(file)).readObject();
    }

    /**
     * Retrieves all the Objects stored in the controller's folder
     * @return List of Objects deserialized
     * @throws ClassNotFoundException Some data files are outdated and cannot be loaded
     * @throws IOException Couldn't read the data files because they are either locked or it doesn't have enough
     * permissions
     */
    public List<Object> getAll() throws IOException, ClassNotFoundException
    {
        File[] listOfFiles = new File("Game/Data/" +
                folderName + "/").listFiles();

        List<Object> objects = new ArrayList<Object>();

        if (listOfFiles != null)
        {
            for (File file : listOfFiles)
            {
                objects.add(new ObjectInputStream(new FileInputStream(file)).readObject());
            }
        }

        return objects;
    }

    /**
     * Retrieves all the files serialized in the controller's folder
     * @return Array of files
     */
    @Deprecated
    public File[] getAllFiles()
    {
        return new File("Game/Data/" + folderName + "/").listFiles();
    }

    /**
     * Stores an Object into a file and serializes it
     * @param identifier Name of the file
     * @param object Object to store
     * @throws IOException Couldn't write the data file because it's either locked or it doesn't have enough
     * permissions
     */
    public void store(String identifier, Object object) throws IOException
    {
        createRootStructure(identifier + ".kys");
        File file = new File("Game/Data/" +
                folderName + "/" +
                identifier + ".kys");

        new ObjectOutputStream(new FileOutputStream(file)).writeObject(object);
    }

    /**
     * Checks if an Object is stored into the filesystem
     * @param identifier Name of the Object
     * @return True if it exists. False otherwise
     */
    public boolean exists(String identifier)
    {
        return new File("Game/Data/" +
                folderName + "/" +
                identifier + ".kys").exists();
    }
}
