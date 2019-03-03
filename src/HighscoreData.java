public class HighscoreData {
    public String username;
    public float points;
    public float totalPoints;

    public HighscoreData(String username,float points,float totalPoints){
        this.username = username;
        this.points = points;
        this.totalPoints = totalPoints;
    }

    public float getTotalPoints(){return totalPoints;}
}

