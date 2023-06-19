
/**
 * <b>May not add any accessor/mutator for this class</b> to force them using
 * heap, key point is for the watched counts
 */
public class PlayableItem {
    private int lastTime;
    private int length;
    private String url;
    private String songName;
    private String artist;
    private int popularity;
    private int playedCounts; // How many times this video has been watched, initially to be 0

    public PlayableItem(String songName, String artist,
                        int popularity, int lastTime, int length, String url) {
        this.lastTime = lastTime;
        this.length = length;
        this.url = url;
        this.songName = songName;
        this.artist = artist;
        this.popularity = popularity;
        this.playedCounts = 0;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getSongName() {
        return this.songName;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public void setPopularity(int pop) {
        this.popularity = pop;
    }

    public boolean isComplete() {
        return this.lastTime == this.length;
    }

    public void atomicPlay() {
        if (this.lastTime < this.length) {
            this.lastTime++;
        } else {
            this.lastTime = 0;
            this.playedCounts++;
        }
    }

    public boolean isSameSong(PlayableItem another) {
        return this.songName.equals(another.songName) && this.artist.equals(another.artist)
                && this.length == another.length && this.url.equals(another.url);
    }

    public String toString() {
        return this.songName + "," + this.url + "," + this.lastTime + "," + this.length
                + "," + this.artist + "," + this.popularity + "," + this.playedCounts;
    }

    public int compareTo(PlayableItem another) {
        return this.playedCounts - another.playedCounts;
    }

}
