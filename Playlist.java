import java.util.PriorityQueue;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Random;
public class Playlist {
    private String name;
    private int playingMode;
    private int playingIndex;
    private int size;
    private int time;
    private PlayableItem cur;
    private Stack<PlayableItem> history;
    private PriorityQueue<PlayableItem> freqListened;
    private ArrayList<PlayableItem> freqArray;
    private ArrayList<PlayableItem> playlist;

    public Playlist() {
        this.name = "Default";
        size = 0;
        time = 0;
        playingMode = 0;
        playingIndex = -1;
        freqListened = new PriorityQueue<>(PlayableItem::compareTo);
        freqArray = new ArrayList<>();
        playlist = new ArrayList<>();
        history = new Stack<>();
    }
    public Playlist(String name) {
        this.name = name;
        size = 0;
        time = 0;
        playingMode = 0;
        playingIndex = -1;
        freqListened = new PriorityQueue<>(PlayableItem::compareTo);
        freqArray = new ArrayList<>();
        playlist = new ArrayList<>();
        history = new Stack<>();
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int size() {
        return size;
    }
    public void addPlayableItem(PlayableItem newItem) {
        playlist.add(newItem);
        freqListened.add(newItem);
        size++;
        getFreq();

    }
    public void addPlayableItem(ArrayList<PlayableItem> newItem) {
        for (PlayableItem item : newItem) {
            addPlayableItem(item);
        }
    }
    public boolean removePlayableItem(int number) {
        if (size > number) {
            PlayableItem tempRemove = playlist.get(number);
            playlist.remove(tempRemove);
            freqListened.remove(tempRemove);
            freqArray.remove(tempRemove);
            size--;
            return true;
        } else {
            return false;
        }
    }
    public void switchPlayingMode(int newMode) {
        playingMode = newMode;
        playingIndex = -1;
        history = new Stack<>();
    }

    private void getFreq() {
        freqArray = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            freqArray.add(freqListened.poll());
        }
        for (int i = 0; i < size; i++) {
            freqListened.add(freqArray.get(i));
        }
    }

    public void play(int seconds) {
        // assigns cur to first item in playlist
        if (cur == null) {
            playingIndex = -1;
            goForward();
        }
        time = 0;
        System.out.println("Seconds 0 : " + cur.getSongName() + " start.");
        for (int i = 0; i < seconds; i++) {
            getFreq();
            if (cur.isComplete()) {
                System.out.println("Seconds "
                        + time + " : " + cur.getSongName() + " complete.");
                if (playingMode != 1 && playingIndex >= size - 1) {
                    System.out.println("No more music to play.");
                    return;
                } else {
                    cur.atomicPlay();
                    time++;
                    startNextSong();
                }
            } else {
                cur.atomicPlay();
                time++;
            }
        }
    }

    private void startNextSong() {
        if (playingMode != 1 && playingIndex >= size - 1) {
            System.out.println("No more music to play.");
        } else {
            goForward();
            System.out.println("Seconds " + time + " : " + cur.getSongName() + " start.");
        }
    }


    public PlayableItem goForward() {
        Random rand = new Random();
        if (size == 0) {
            return null;
        }
        // set playing index
        if (playingMode == 1) {
            playingIndex = rand.nextInt(size);
        } else {
            if (playingIndex >= size - 1) {
                return null;
            } else {
                playingIndex++;
            }
        }
        // set cur to next song
        cur = playlist.get(playingIndex);
        history.add(cur);
        return cur;
    }

    public void goBack() {
        if (history.isEmpty()) {
            System.out.println("No more steps to go back");
        } else if (history.size() == 1) {
            System.out.println("No more steps to go back");
            cur = history.pop();
            playingIndex = 0;
        } else {
            history.pop();
            cur = history.peek();
            playingIndex = playlist.indexOf(cur);
            System.out.println(cur.getSongName());
        }
    }

    private int checkTime(PlayableItem song) {
        return Integer.parseInt(song.toString().split(",")[2]);
    }

    public String showPlaylistStatus() {
        StringBuilder status = new StringBuilder();
        int index = 0;
        for (PlayableItem item : playlist) {
            status.append(index).append(". ").append(item.toString());
            if (index == playingIndex) {
                status.append(" - Currently playing");
            }
            status.append("\n");
            index++;
        }
        return status.toString();
    }
}

