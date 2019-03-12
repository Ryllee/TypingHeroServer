/**
 * Inkapslingsklass för highscore som underlättar hantering
 *
 * @author Eric Rylander
 * @version 2019-03-12
 */
public class HighscoreData {
    public String username;
    public float points;
    public float totalPoints;

    /**
     * Skapar en HighscoreData
     * @param username spelarens username
     * @param points spelarens points från sparfil
     * @param totalPoints spelarens totalpoints från sparfil
     */
    public HighscoreData(String username,float points,float totalPoints){
        this.username = username;
        this.points = points;
        this.totalPoints = totalPoints;
    }

    /**
     * Hämtar totalpoints
     * @return totalpoints
     */
    public float getTotalPoints(){return totalPoints;}
}

