package com.example.jozmusik.Data.Utils

import com.example.jozmusik.Data.Dao.SongDao
import com.example.jozmusik.Data.Entity.Song
import kotlinx.coroutines.flow.first
import java.util.UUID

class DataSetup {
    companion object {
        suspend fun setupInitialData(songDao: SongDao) {
            // Check if data exists first
            val existingSongs = songDao.getAllSongs().first()

            if (existingSongs.isEmpty()) {
                // Insert dummy data directly to Room
                val dummySongs = listOf(
                    // Pop Songs
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Shape of You",
                        artist = "Ed Sheeran",
                        imageUrl = "https://pixabay.com/get/g20072f0bbc99fbe95ca90b898f1c2a5ee4e0edc99f4f1662ea90e0c16a7f2efee9af0be273b53c4e07897b4a32dd0ce9_640.jpg"
                    ),
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Blinding Lights",
                        artist = "The Weeknd",
                        imageUrl = "https://pixabay.com/get/g3249763e617207d57dfb36186029d4e8a0a3ed53cb8b5a7de2a21e4ce04c26460a7527b14e7195521f8ab225e365c0ab_640.jpg"
                    ),
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Rolling in the Deep",
                        artist = "Adele",
                        imageUrl = "https://pixabay.com/get/g89e1fb3d9_1280.jpg"
                    ),
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Stay With Me",
                        artist = "Sam Smith",
                        imageUrl = "https://pixabay.com/get/g4dcc8b83006599c4a4f16b1c8b245d5c7e9da8a2d146991ed2a9567d95c1c1f67b90d8caa4a4e208d5515c864b431c5_640.jpg"
                    ),
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Uptown Funk",
                        artist = "Mark Ronson ft. Bruno Mars",
                        imageUrl = "https://pixabay.com/get/gd80cc73675d6a40a1a9b2f9667e8c0a0e11899150902b4f48a7b1dbf5bd93e80ab83bf62c85cda0c29e44dfa0eb1305_640.jpg"
                    ),

                    // Rock Songs
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Believer",
                        artist = "Imagine Dragons",
                        imageUrl = "https://pixabay.com/get/g41e0b5dd9_1280.jpg"
                    ),
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Radioactive",
                        artist = "Imagine Dragons",
                        imageUrl = "https://pixabay.com/get/g3c84c5635f4b1c98303fc47eed5c0c1c9a204a49a3cb5a9347241565d9a5aac8de013e9fd1a0a75c298eae1fead249e_640.jpg"
                    ),
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Don't Stop Believin'",
                        artist = "Journey",
                        imageUrl = "https://pixabay.com/get/g1b1e9455e6b4c5c14dd71307ba6125adee0380c0f44dd2a16adeae04bdbce22e9d6a5dc57f83b7e7b4bd013da6ab1e3_640.jpg"
                    ),

                    // R&B and Soul
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "All of Me",
                        artist = "John Legend",
                        imageUrl = "https://pixabay.com/get/gd4802585c_1280.jpg"
                    ),
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Shallow",
                        artist = "Lady Gaga & Bradley Cooper",
                        imageUrl = "https://pixabay.com/get/ge96d98e9d_1280.jpg"
                    ),

                    // Electronic and Dance
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Wake Me Up",
                        artist = "Avicii",
                        imageUrl = "https://pixabay.com/get/g621246e10_1280.jpg"
                    ),
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Faded",
                        artist = "Alan Walker",
                        imageUrl = "https://pixabay.com/get/g7ced26e3c_1280.jpg"
                    ),

                    // Hip Hop
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Old Town Road",
                        artist = "Lil Nas X ft. Billy Ray Cyrus",
                        imageUrl = "https://pixabay.com/get/g324348d4c_1280.jpg"
                    ),
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Can't Stop the Feeling!",
                        artist = "Justin Timberlake",
                        imageUrl = "https://pixabay.com/get/g5ebdbb00d_1280.jpg"
                    ),
                    // Alternative
                    Song(
                        id = UUID.randomUUID().toString(),
                        title = "Take Me to Church",
                        artist = "Hozier",
                        imageUrl = "https://pixabay.com/get/g49d5842c8_1280.jpg"
                    )
                )

                try {
                    songDao.insertSongs(dummySongs)
                    println("Successfully inserted initial songs to database")
                } catch (e: Exception) {
                    println("Error inserting initial songs: ${e.message}")
                }
            }
        }
    }
}