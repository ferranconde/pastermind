package Presentation.Views;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.List;

/**
 * Interface that represents a user panel
 */
interface IUserPanel
{
    /**
     * Adds the selected circle to the chosen color list
     * @param circle A circle representing the peg
     */
    void addCircleToChosenPlayList(Circle circle);

    /**
     * Generates a play using the desired parameters
     * @param numHoles Number of holes of the game
     * @param colorList List of colors used in this play
     * @param items List of pegs
     * @return The play codified as a string
     */
    default String generatePlay(int numHoles, List<String> colorList, List<Object> items)
    {
        String play = "";
        int count = 0;
        for (Object o : items)
        {
            Circle c = (Circle)o;

            //Transforming hex to color name
            List<String> colors = colorList;
            colors.add("WHITE");
            colors.add("BLACK");
            for (String color : colors)
            {
                if (color.equals("EMPTY"))
                    continue;

                if (Paint.valueOf(color.toLowerCase()).equals(c.getFill()))
                {
                    play += color + " ";
                    ++count;
                    break;
                }
            }
        }

        while (numHoles != count)
        {
            play += "EMPTY ";
            ++count;
        }

        play = play.substring(0, play.length() - 1);

        return play;
    }
}
