# Tabla Periódica - Aplicación Android

[![CI](https://github.com/bi0punk/Android101/actions/workflows/ci.yml/badge.svg)](https://github.com/bi0punk/Android101/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)
![Android](https://img.shields.io/badge/android-min%2023+-green.svg)

Este es un proyecto de una aplicación Android que permite a los usuarios ingresar un número atómico y obtener información detallada sobre el elemento químico correspondiente. La información se muestra en un formato tabular de dos columnas, haciendo uso de un `TableLayout`.

## Tabla de contenidos

- [Características](#características)
- [Stack](#stack)
- [Requisitos](#requisitos)
- [Instalación y Ejecución](#instalación-y-ejecución)
- [Tests](#tests)
- [CI](#ci)
- [Limitaciones](#limitaciones)
- [Licencia](#licencia)

## Demo

![Captura de pantalla de la aplicación](https://i.ibb.co/5RbWVP8/Selecci-n-044.png)

## Características

- **Entrada de Usuario:** Permite ingresar un número atómico para buscar el elemento correspondiente.
- **Solicitud HTTP:** Envía una solicitud HTTP a un servidor para obtener información sobre el elemento químico.
- **Visualización de Datos:** Muestra la información recibida en una tabla con dos columnas: una para la propiedad y otra para su valor.
- **Interfaz de Usuario:** UI simple y limpia con un diseño responsivo.

## Tecnologías Utilizadas

- **Lenguaje de Programación:** Java
- **Framework de Red:** OkHttp para realizar solicitudes HTTP
- **Diseño de UI:** XML con `RelativeLayout` y `TableLayout`
- **Servidor Backend:** El servidor responde con datos en formato JSON

## Requisitos

- **Android Studio:** Para desarrollar y ejecutar el proyecto.
- **JDK:** Java Development Kit para compilar y ejecutar la aplicación.
- **Dispositivo Android o Emulador:** Para probar la aplicación.

## Instalación y Ejecución

1. **Clonar el repositorio:**

   ```bash
   git clone https://github.com/bi0punk/Android101
   ```

2. **Abrir en Android Studio** (o compilar desde CLI):

   ```bash
   ./gradlew assembleDebug
   ```

3. El APK debug queda en `app/build/outputs/apk/debug/`.

## Tests

```bash
./gradlew test              # unit tests
./gradlew connectedCheck    # instrumented tests (requiere dispositivo/emulador)
```

Hay un test instrumentado de ejemplo en `app/src/androidTest/`.

## CI

GitHub Actions (`.github/workflows/ci.yml`):

- Valida el gradle wrapper.
- `./gradlew assembleDebug` (build del APK debug).
- `./gradlew test` (unit tests).

Usa Temurin JDK 17 + `setup-android`.

## Limitaciones

- La app consume un servidor backend externo para los datos de elementos (ver `MainActivity.java`); sin backend no devuelve datos.
- `minSdk 23`, `targetSdk 34`.
- Sin tests unitarios reales (solo el instrumentado de ejemplo).

## Licencia

MIT — ver [LICENSE](LICENSE).
