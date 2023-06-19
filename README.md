# MusicPlayer
This is the java code for a music playing application that was made as the final project of a data science course. 
Since the course focused on data structures, the code implements several different ADTs.

The first class (MyPlayer) was written by the professor, but everything else was written by me, Aaron.

The second class (PlayableItem) is the creation of a new item that stores info about the playable item. 
It includes several getter and setter methods, as well as a method (atomic play) that plays one second of a song. 
The class has a method for differentiating between playable items, converting items to a neatly formatted string, and a comparator

The third class (MusicDatabase) is the music library that the user can download to before adding items to a playlist. 
It stores items in a hashtable for organizing by song name, and also in a treemap for organizing by artist name. 
The methods include a function that checks if a song has been downloaded and a function that adds songs. 
Also included are functions for partial search by song title or artist name as well as search by popularity.

The fourth class (Playlist) is what allows the user to create as many playlists as they want with as many songs as they want. 
There are 3 modes to listen to a playlist: normal, shuffle and most frequently played. 
Each playlist is an arraylist, that utilizes a priority queue in order to sort according to the number of listens for frequent mode.
Notable methods include adding and removing items to and from the playlist as well as changing the playing mode. 
Additionally, there are methods for playing songs given the number of seconds as well as skipping forward and backward. 
Finally, the user can see the entire playlist neatly displayed, with a marker next to the item that is currently being played.
