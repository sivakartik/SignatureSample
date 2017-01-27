# SignatureSample
====================
SignatureSample is an Android library for drawing signatures.

## Features
 * Customizable pen color and size
 * Bitmap support

##Installation

Latest version of the library can be found on Maven Central.

### For Gradle users

Open your `build.gradle` Then, include the library as dependency:
```gradle
compile 'com.ksk.marujolla.signature-pad:SignaturePad:0.11'
```

### For Maven users

Add this dependency to your `pom.xml`:
```xml
<dependency>
  <groupId>com.ksk.marujolla.signature-pad</groupId>
  <artifactId>SignaturePad</artifactId>
  <version>0.11</version>
  <type>pom</type>
</dependency>
```
 
##Usage

*Please see the `/SignatureSample-app` app for a more detailed code example of how to use the library.*

1. Add the `SignaturePad` view to the layout you want to show.
```xml
<ksk.marujolla.signaturepad.SignaturePad
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/signatureView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/white"
        app:penWidth="5dp"
        app:penColor="@android:color/holo_blue_dark"/>
```

2. Configure attributes.
 * `penWidth` - The width of the stroke (default: 5dp).
 * `penColor` - The color of the stroke (default: Color.BLACK).
 
3. Get signature data
 * `getSignatureBitmap()` - A signature bitmap with a white background.

4. Clear signature data
 * `clear()` - Clear's signature and a fresh screen is available.

## License

    Copyright 2017 Koteswara shiva kartik Marujolla

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
