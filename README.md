# dearrow-kt

[![Maven Central Version](https://img.shields.io/maven-central/v/dev.zt64/dearrowkt)](https://central.sonatype.com/artifact/dev.zt64/dearrowkt)
<br>
![badge-platform-jvm]
![badge-platform-js]
![badge-platform-js-node]
![badge-platform-linux]
![badge-platform-windows]
![badge-platform-macos]
![badge-platform-ios]
![badge-platform-tvos]
![badge-platform-watchos]

Kotlin multiplatform client for the [DeArrow API](https://wiki.sponsor.ajay.app/w/API_Docs/DeArrow).

## Setup

```
[versions]
dearrowkt = "x.y.z"

[libraries]
dearrow = { module = "dev.zt64:dearrowkt, version.ref = "dearrowkt" }
```

## Usage

```kotlin
val client = DearrowClient()
val videoId = "dQw4w9WgXcQ"

val branding = client.getBranding(videoId)
// OR        = client.getBrandingByHash(videoId)

println(video.titles)
println(video.thumbnails)
```

## License

[GPL v3.0](LICENSE)

[badge-platform-jvm]: http://img.shields.io/badge/-jvm-DB413D.svg?style=flat

[badge-platform-js]: http://img.shields.io/badge/-js-F8DB5D.svg?style=flat

[badge-platform-js-node]: https://img.shields.io/badge/-nodejs-68a063.svg?style=flat

[badge-platform-linux]: http://img.shields.io/badge/-linux-2D3F6C.svg?style=flat

[badge-platform-windows]: http://img.shields.io/badge/-windows-4D76CD.svg?style=flat

[badge-platform-macos]: http://img.shields.io/badge/-macos-111111.svg?style=flat

[badge-platform-ios]: http://img.shields.io/badge/-ios-CDCDCD.svg?style=flat

[badge-platform-tvos]: http://img.shields.io/badge/-tvos-808080.svg?style=flat

[badge-platform-watchos]: http://img.shields.io/badge/-watchos-C0C0C0.svg?style=flat

[badge-platform-wasm]: https://img.shields.io/badge/-wasm-624FE8.svg?style=flat