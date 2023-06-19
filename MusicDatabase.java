import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Set;
import java.util.Comparator;
import java.util.List;

public class MusicDatabase {

    private Hashtable<String, ArrayList<PlayableItem>> songNames;
    private TreeMap<String, ArrayList<PlayableItem>> artists;
    private int size;

    public MusicDatabase() {
        size = 0;
        this.songNames = new Hashtable<>();
        this.artists = new TreeMap<>();
    }

    private boolean listContains(PlayableItem thing, ArrayList<PlayableItem> things) {
        for (PlayableItem item : things) {
            if (item.isSameSong(thing)) {
                return true;
            }
        }
        return false;
    }

    public void addSong(String name, String artist, int popularity, int duration, String url) {
        PlayableItem song = new PlayableItem(name, artist, popularity, 0, duration, url);
        size++;
        // add new song name list if necessary
        if (!this.songNames.containsKey(name)) {
            ArrayList<PlayableItem> titles = new ArrayList<>();
            this.songNames.put(name, titles);
        }
        // add new artist name list if necessary
        if (!this.artists.containsKey(artist)) {
            ArrayList<PlayableItem> singers = new ArrayList<>();
            this.artists.put(artist, singers);
        }
        // add song or increment popularity
        if (listContains(song, this.songNames.get(name))) {
            song.setPopularity(song.getPopularity() + 1);
        } else {
            this.songNames.get(name).add(song);
            this.artists.get(artist).add(song);
        }
    }

    public boolean addSongs(File inputFile) {
        try {
            List<String> lines =  Files.readAllLines(inputFile.toPath());
            for (String line : lines.subList(1, lines.size())) {
                System.out.println(line);
                String[] split = line.split(",");
                addSong(split[0], split[1],
                        Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4]);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public ArrayList<PlayableItem> partialSearchBySongName(String songName) {
        ArrayList<PlayableItem> results = new ArrayList<>();
        Set<String> keys = this.songNames.keySet();
        for (String key : keys) {
            if (key.toLowerCase().contains(songName.toLowerCase()))  {
                results.addAll(this.songNames.get(key));
            }
        }
        return sorting(results);
    }

    public ArrayList<PlayableItem> partialSearchByArtistName(String artistName) {
        ArrayList<PlayableItem> results = new ArrayList<>();
        Set<String> keys = this.artists.keySet();
        for (String key : keys) {
            if (key.toLowerCase().contains(artistName.toLowerCase()))  {
                results.addAll(this.artists.get(key));
            }
        }
        return sorting(results);
    }

    public ArrayList<PlayableItem> searchHighestPopularity(int threshold) {
        ArrayList<PlayableItem> results = new ArrayList<>();
        Set<String> keys = this.songNames.keySet();
        for (String key : keys) {
            for (PlayableItem song : this.songNames.get(key)) {
                if (song.getPopularity() >= threshold) {
                    results.add(song);
                }
            }
        }
        return sorting(results);
    }

    public int size() {
        return size;
    }

    private static ArrayList<PlayableItem> sorting(ArrayList<PlayableItem> array) {
        array.sort(new Comparator<PlayableItem>() {
            public int compare(PlayableItem o1, PlayableItem o2) {
                int sComp = o2.getPopularity() - o1.getPopularity();
                if (sComp != 0) {
                    return sComp;
                }
                sComp = o1.getSongName().compareTo(o2.getSongName());
                if (sComp != 0) {
                    return sComp;
                }
                sComp = o1.getArtist().compareTo(o2.getArtist());

                return sComp;
            }
        });
        return array;
    }
}
