# dearrow-kt

[![Maven Central Version](https://img.shields.io/maven-central/v/dev.zt64/dearrowkt)](https://central.sonatype.com/artifact/dev.zt64/dearrowkt)

## Setup

```
[versions]
dearrowkt = "0.1.0"

[libraries]
dearrow = { module = "dev.zt64:dearrowkt, version.ref = "dearrowkt" }
```

## Usage

```kotlin
import dev.zt64.dearrowkt.*

val client = DearrowClient()
val videoId = ""

val video = client.getVideo(videoId)

```

## License