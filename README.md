# Diario espacial · PC2 DAM 2026-2

App Android (Kotlin + Jetpack Compose) que consume la API de la NASA (APOD y NeoWs)
y guarda los datos en SQLite con Room. Las pantallas leen siempre desde Room.

## Integrantes
| Rol | Nombre | Código |
|---|---|---|
| Alumno 1 · Opción Imágenes (APOD) | Cielo Camyla Valle Cristóbal | 25102283 |
| Alumno 2 · Opción Asteroides (NeoWs) | Italo Navarrete | 25100012 |

## Cómo ejecutar
1. Clonar el repositorio y abrirlo en Android Studio.
2. Generar una clave en https://api.nasa.gov (Generate API Key).
3. En `local.properties` (raíz del proyecto, no se sube a GitHub) agregar:
   `NASA_API_KEY=tu_clave`
4. Sync Gradle y ejecutar en un emulador con API 26 o superior.

## Tecnologías
Kotlin, Jetpack Compose (Material 3), Navigation Compose, Retrofit + Gson, Coil, Room, ViewModel + StateFlow.

## Acuerdo de coordinación (base común)
- Cielo preparó la base común y la subió a `main` antes de protegerla: dependencias,
  clave en BuildConfig, `RetrofitInstance`, `AppDatabase` y la navegación principal (barra inferior).
- Italo la integra así: agrega `AsteroidEntity`, `version = 2` y `asteroidDao()` en `AppDatabase`,
  y expone `asteroidesGraph(...)`, que Cielo conecta en `AppNavGraph` al integrar.
- Cada uno trabaja en su rama (`feature/imagenes`, `feature/asteroides`) y entrega por Pull Request
  revisado y aprobado por el otro. `main` está protegida.