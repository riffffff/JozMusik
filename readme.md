# JozMusik - Aplikasi Pengelola Playlist Musik Android

JozMusik adalah aplikasi pemutar dan pengelola playlist musik berbasis Android yang dibangun menggunakan bahasa Kotlin. Aplikasi ini mengimplementasikan arsitektur MVVM (Model-View-ViewModel) serta Repository Pattern untuk menyatukan manajemen data lokal dan jaringan secara terstruktur.

## Informasi Akademik / Tugas Kuliah

* **Mata Kuliah**: [Nama Mata Kuliah, contoh: Pemrograman Platform Bergerak]
* **Dosen Pengampu**: [Nama Dosen Pengampu]
* **Program Studi / Jurusan**: [Nama Program Studi]
* **Instansi / Universitas**: [Nama Perguruan Tinggi]

### Anggota Kelompok / Pengembang

| Nama Mahasiswa | NIM | Peran |
| --- | --- | --- |
| [Nama Anda / Anggota 1] | [NIM 1] | Developer / Lead |
| [Nama Anggota 2] | [NIM 2] | Developer |

---

## Daftar Isi

* [Fitur Utama](#fitur-utama)
* [Arsitektur dan Teknologi](#arsitektur-dan-teknologi)
* [Struktur Proyek](#struktur-proyek)
* [Persyaratan Sistem](#persyaratan-sistem)
* [Instalasi dan Penggunaan](#instalasi-dan-penggunaan)
* [Integrasi REST API](#integrasi-rest-api)

## Fitur Utama

* **Autentikasi Pengguna**: Fitur pendaftaran akun dan autentikasi masuk menggunakan verifikasi data lokal.
* **Katalog Lagu**: Menampilkan daftar lagu rekomendasi yang tersimpan di dalam database lokal.
* **Manajemen Playlist**: Penambahan dan penghapusan lagu pada playlist personal pengguna.
* **Sinkronisasi Data**: Integrasi penyimpanan lokal (Room DB) dengan server remote (REST API).
* **Manajemen Sesi & Profil**: Pengelolaan sesi aktif pengguna dan informasi profil akun.

## Arsitektur dan Teknologi

Aplikasi ini menggunakan teknologi dan pustaka standar dalam pengembangan Android modern:

* **Bahasa Pemrograman**: Kotlin
* **Pola Arsitektur**: MVVM (Model-View-ViewModel) + Repository Pattern
* **UI & Navigasi**: Material Design 3, View Binding, Jetpack Navigation Component
* **Penyimpanan Lokal**: Room Database, SharedPreferences
* **Jaringan**: Retrofit2, Gson Converter, OkHttp Logging Interceptor
* **Pemrosesan Asinkron**: Kotlin Coroutines, Kotlin Flow

## Struktur Proyek

```
com.example.jozmusik
├── Adapter/              # Adapter untuk RecyclerView (Song & Playlist)
├── Data/                 # Sumber data (API, DAO, Entity, Repository, Preferences)
│   ├── Api/              # Layanan Retrofit dan data model API
│   ├── Dao/              # Data Access Object untuk Room DB
│   ├── Entity/           # Entitas database Room
│   ├── Preferences/      # Pengelola SharedPreferences
│   ├── Repository/       # Abstraksi pengelola data
│   └── Utils/            # Utilities dan Seeder DB
├── Model/                # Data model aplikasi
└── UI/                   # Tampilan dan ViewModel
    ├── Auth/             # Login & Register Activity
    ├── Home/             # Home Fragment & ViewModel
    ├── Playlist/         # Playlist Fragment & ViewModel
    ├── Profile/          # Profile Fragment
    └── SplashScreen/     # Splash Screen Activity
```

## Persyaratan Sistem

* **Android Studio**: Versi 2023.1 atau lebih baru
* **Android SDK**: Compile SDK 34, Min SDK 24 (Android 7.0 Nougat)
* **Gradle**: Versi 8.7 (KTS)
* **Java Development Kit (JDK)**: Java 8 (1.8)

## Instalasi dan Penggunaan

1. Clone repositori ini ke komputer lokal Anda:
   ```bash
   git clone <URL_REPOSITORY>
   ```
2. Buka Android Studio dan pilih menu **Open**, kemudian arahkan ke direktori proyek `JozMusik`.
3. Jalankan proses **Sync Project with Gradle Files** dan tunggu hingga seluruh dependensi terunduh.
4. Hubungkan perangkat Android fisik atau jalankan Android Virtual Device (Emulator).
5. Tekan tombol **Run** atau kombinasi tombol `Shift + F10` untuk menjalankan aplikasi.

## Integrasi REST API

Aplikasi terhubung ke layanan REST API dengan alamat dasar `https://ppbo-api.vercel.app/0BoBP/`.

| Method | Endpoint | Deskripsi |
| --- | --- | --- |
| `GET` | `/playlist/{userId}` | Mengambil playlist pengguna berdasarkan ID / Username |
| `POST` | `/playlist` | Menambahkan lagu baru ke dalam playlist |
| `DELETE` | `/playlist/{id}` | Menghapus lagu dari playlist berdasarkan ID lagu |
