# QuranApp
Clean Architecture,MMVM/MVI, Dagger Hilt,Databinding, Retrofit,Coroutine, coroutine flow

https://codelabs.developers.google.com/android-kotlin-fundamentals/


### Kotlin Flow:
A flow is an asynchronous version of a Sequence, a type of collection whose values are lazily produced. Just like a sequence, a flow produces each value on-demand whenever the value is needed, and flows can contain an infinite number of values.

Flow produces values one at a time (instead of all at once) that can generate values from async operations like network requests, database calls, or other async code. It supports coroutines throughout its API, so you can transform a flow using coroutines as well!

We can build, transform, and consume a Flow using coroutines. You can also control concurrency, which means coordinating the execution of several coroutines declaratively with Flow.



## Jetpack Navigation :

> Navigation Graph
> NavHostFragment
> NavController

app:startDestination is an attribute that specifies the destination that is launched by default when the user first opens the app.

<LinearLayout
    .../>
    <androidx.appcompat.widget.Toolbar
        .../>
    <fragment
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:id="@+id/my_nav_host_fragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        app:navGraph="@navigation/mobile_navigation"
        app:defaultNavHost="true"
        />
    <com.google.android.material.bottomnavigation.BottomNavigationView
        .../>
</LinearLayout>

android:name="androidx.navigation.fragment.NavHostFragment" and app:defaultNavHost="true" connect the system back button to the NavHostFragment
app:navGraph="@navigation/mobile_navigation" associates the NavHostFragment with a navigation graph. This navigation graph specifies all the destinations the user can navigate to, in this NavHostFragment.


## NavController :

when a user does something like clicking a button, you need to trigger a navigate command. A special class called the NavController is what triggers the fragment swaps in the NavHostFragment.

// Command to navigate to flow_step_one_dest
findNavController().navigate(R.id.flow_step_one_dest)

Note that you pass in either a destination or action ID to navigate. These are the IDs defined in the navigation graph XML. This is an example of passing in a destination ID.



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






