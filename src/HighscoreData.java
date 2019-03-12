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

