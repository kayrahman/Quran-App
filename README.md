# memorize_quran
MMVM, Dagger Hilt,Databinding, Retrofit,Coroutine


## Controlling media through MediaSession:
Media sessions are an integral link between the Android platform and media apps. Not only does it inform Android that media is playing—so that it can forward media actions into the correct session—but it also informs the platform what is playing and how it can be controlled.


### MediaDataCompat :

### Concatenating media source :
 Used to implement playlists


### Player Notification Manager:
Each time the player state changes it will update the drawer again.

### Media Description Adapter :
Player Notification Manager will use this adapter to get information about the currently playing item. Each time the notification is rebuilt the adapter is called to get this information.


### MediaItems: 
In ExoPlayer every piece of media is represented by a MediaItem. To play a piece of media you need to build a corresponding MediaItem, add it to the player, prepare the player, and call play to start the playback.


### Notification Listener :

## Media Session and Media Browser Service are used to expose the controls to external applications such as Google assistant, Remote Controls, Android Auto, WearOs.




## What is Synchronization?
In a multi-threaded world, we need to access shared objects across threads, and If we do not synchronize our work, unwanted situations can occur.

### Volatile :
Volatile fields provide memory visibility and guarantee that the value that is being read, comes from the main memory and not the cpu-cache, so the value in cpu-cache is always considered to be dirty, and It has to be fetched again.
### synchronized :
There are two types of synchronization available in Java (and Kotlin). Synchronized methods and synchronized statements.
To understand synchronized methods (denoted by the synchronized function modifier keyword in Java and @Synchronized annotation in Kotlin).
In a room scenario, Synchronized resembles a lock on the door that has only one key, that people need to use to open the door and lock it. So when one person (thread) goes in, they can use the key to lock the door, and since no one else has the key, they cannot go in until the key is returned to them by the original person. This is exactly what synchronized does in Java and Kotlin.






