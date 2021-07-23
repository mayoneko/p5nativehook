# p5nativehook

## ABOUT
p5nativehook is a library to provide global keyboard and mouse listeners for Processing.
Thank kwhat for provide [jnativehook](https://github.com/kwhat/jnativehook).
And I quoted from [Javaでネイティブにキーボード/マウスイベントを取得する](https://qiita.com/Getaji/items/8ad1887761ac222b61a2). Thank you.


## USAGE
import jar files of jnativehook and p5nativehook into your processing.

my environment is
- Processing 3.3.7
- jnativehook 2.1.0


## SAMPLE
```mousesample.pde
import org.jnativehook.*;
import org.jnativehook.dispatcher.*;
import org.jnativehook.mouse.*;

import p5nativehook.p5NativeMouseGet;

void setup(){
  size(500,500);
  background(255);
  p5NativeMouseGet mg=new p5NativeMouseGet(this);
}

void draw(){
}

void mousePressed(){
  //this mouseEvent will be called out of window.
  background(random(255),0,0); 
}
```

```keysample.pde
import org.jnativehook.*;
import org.jnativehook.dispatcher.*;
import org.jnativehook.keyboard.*;

import p5nativehook.p5NativeKeyGet;

void setup(){
  size(500,500);
  background(255);
  p5NativeKeyGet kg=new p5NativeKeyGet(this);
}

void draw(){
}

void keyPressed(){
  //this keyEvent will be called out of window.
  background(random(255),0,0); 
}
```

## できていないこと
- jnativehookの仕様上、一番始めによく分からないログを吐く（上のQiitaの関数を使って途中でログを止めている）
- jnativehookの仕様上、keyTypedには反応しない（keyPressedとkeyReleasedには反応する）
- jnativehookの仕様上、keyCodeは取得できるがkeyCharは取得できない
- mouseWheelの上下を認識していない
