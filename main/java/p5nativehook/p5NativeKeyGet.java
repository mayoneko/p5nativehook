package p5nativehook;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.keyboard.SwingKeyAdapter;
import processing.core.PApplet;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class p5NativeKeyGet extends PApplet implements NativeKeyListener {

    private PApplet myapplet;

    public p5NativeKeyGet(PApplet _applet) {
        myapplet = _applet;
        if (!GlobalScreen.isNativeHookRegistered()) {
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        suppressLogger();
        GlobalScreen.addNativeKeyListener(this);
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
    }

    private void suppressLogger() {
        scheduleSuppress();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(new Runnable() {
                                     public void run() {
                                         scheduleSuppress();
                                     }
                                 }
                , 2, TimeUnit.SECONDS);
    }

    private void scheduleSuppress() {
        Logger jNativeHookLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        if (jNativeHookLogger.getLevel() != Level.WARNING) {
            synchronized (jNativeHookLogger) {
                jNativeHookLogger.setLevel(Level.WARNING);
            }
        }
    }

    private int getJavaModifiers(int nativeModifiers) {
        int modifiers = 0x00;
        if ((nativeModifiers & NativeInputEvent.SHIFT_MASK) != 0) {
            modifiers |= KeyEvent.SHIFT_MASK;
            modifiers |= KeyEvent.SHIFT_DOWN_MASK;
        }
        if ((nativeModifiers & NativeInputEvent.META_MASK) != 0) {
            modifiers |= KeyEvent.META_MASK;
            modifiers |= KeyEvent.META_DOWN_MASK;
        }
        if ((nativeModifiers & NativeInputEvent.META_MASK) != 0) {
            modifiers |= KeyEvent.CTRL_MASK;
            modifiers |= KeyEvent.CTRL_DOWN_MASK;
        }
        if ((nativeModifiers & NativeInputEvent.ALT_MASK) != 0) {
            modifiers |= KeyEvent.ALT_MASK;
            modifiers |= KeyEvent.ALT_DOWN_MASK;
        }
        if ((nativeModifiers & NativeInputEvent.BUTTON1_MASK) != 0) {
            modifiers |= KeyEvent.BUTTON1_MASK;
            modifiers |= KeyEvent.BUTTON1_DOWN_MASK;
        }
        if ((nativeModifiers & NativeInputEvent.BUTTON2_MASK) != 0) {
            modifiers |= KeyEvent.BUTTON2_MASK;
            modifiers |= KeyEvent.BUTTON2_DOWN_MASK;
        }
        if ((nativeModifiers & NativeInputEvent.BUTTON3_MASK) != 0) {
            modifiers |= KeyEvent.BUTTON3_MASK;
            modifiers |= KeyEvent.BUTTON3_DOWN_MASK;
        }

        return modifiers;
    }

    private java.awt.event.KeyEvent getJavaKeyEvent(NativeKeyEvent nativeEvent) {
        int keyLocation = java.awt.event.KeyEvent.KEY_LOCATION_UNKNOWN;
        switch (nativeEvent.getKeyLocation()) {
            case NativeKeyEvent.KEY_LOCATION_STANDARD:
                keyLocation = java.awt.event.KeyEvent.KEY_LOCATION_STANDARD;
                break;

            case NativeKeyEvent.KEY_LOCATION_NUMPAD:
                keyLocation = java.awt.event.KeyEvent.KEY_LOCATION_NUMPAD;
                break;

            case NativeKeyEvent.KEY_LOCATION_LEFT:
                keyLocation = java.awt.event.KeyEvent.KEY_LOCATION_STANDARD;
                break;

            case NativeKeyEvent.KEY_LOCATION_RIGHT:
                keyLocation = java.awt.event.KeyEvent.KEY_LOCATION_RIGHT;
                break;
        }

        int keyCode = java.awt.event.KeyEvent.VK_UNDEFINED;
        switch (nativeEvent.getKeyCode()) {
            case NativeKeyEvent.VC_ESCAPE:
                keyCode = java.awt.event.KeyEvent.VK_ESCAPE;
                break;

            // Begin Function Keys
            case NativeKeyEvent.VC_F1:
                keyCode = java.awt.event.KeyEvent.VK_F1;
                break;

            case NativeKeyEvent.VC_F2:
                keyCode = java.awt.event.KeyEvent.VK_F2;
                break;

            case NativeKeyEvent.VC_F3:
                keyCode = java.awt.event.KeyEvent.VK_F3;
                break;

            case NativeKeyEvent.VC_F4:
                keyCode = java.awt.event.KeyEvent.VK_F4;
                break;

            case NativeKeyEvent.VC_F5:
                keyCode = java.awt.event.KeyEvent.VK_F5;
                break;

            case NativeKeyEvent.VC_F6:
                keyCode = java.awt.event.KeyEvent.VK_F6;
                break;

            case NativeKeyEvent.VC_F7:
                keyCode = java.awt.event.KeyEvent.VK_F7;
                break;

            case NativeKeyEvent.VC_F8:
                keyCode = java.awt.event.KeyEvent.VK_F8;
                break;

            case NativeKeyEvent.VC_F9:
                keyCode = java.awt.event.KeyEvent.VK_F9;
                break;

            case NativeKeyEvent.VC_F10:
                keyCode = java.awt.event.KeyEvent.VK_F10;
                break;

            case NativeKeyEvent.VC_F11:
                keyCode = java.awt.event.KeyEvent.VK_F11;
                break;

            case NativeKeyEvent.VC_F12:
                keyCode = java.awt.event.KeyEvent.VK_F12;
                break;

            case NativeKeyEvent.VC_F13:
                keyCode = java.awt.event.KeyEvent.VK_F13;
                break;

            case NativeKeyEvent.VC_F14:
                keyCode = java.awt.event.KeyEvent.VK_F14;
                break;

            case NativeKeyEvent.VC_F15:
                keyCode = java.awt.event.KeyEvent.VK_F15;
                break;

            case NativeKeyEvent.VC_F16:
                keyCode = java.awt.event.KeyEvent.VK_F16;
                break;

            case NativeKeyEvent.VC_F17:
                keyCode = java.awt.event.KeyEvent.VK_F17;
                break;

            case NativeKeyEvent.VC_F18:
                keyCode = java.awt.event.KeyEvent.VK_F18;
                break;

            case NativeKeyEvent.VC_F19:
                keyCode = java.awt.event.KeyEvent.VK_F19;
                break;
            case NativeKeyEvent.VC_F20:
                keyCode = java.awt.event.KeyEvent.VK_F20;
                break;

            case NativeKeyEvent.VC_F21:
                keyCode = java.awt.event.KeyEvent.VK_F21;
                break;

            case NativeKeyEvent.VC_F22:
                keyCode = java.awt.event.KeyEvent.VK_F22;
                break;

            case NativeKeyEvent.VC_F23:
                keyCode = java.awt.event.KeyEvent.VK_F23;
                break;

            case NativeKeyEvent.VC_F24:
                keyCode = java.awt.event.KeyEvent.VK_F24;
                break;
            // End Function Keys


            // Begin Alphanumeric Zone
            case NativeKeyEvent.VC_BACKQUOTE:
                keyCode = java.awt.event.KeyEvent.VK_BACK_QUOTE;
                break;

            case NativeKeyEvent.VC_1:
                keyCode = java.awt.event.KeyEvent.VK_1;
                break;

            case NativeKeyEvent.VC_2:
                keyCode = java.awt.event.KeyEvent.VK_2;
                break;

            case NativeKeyEvent.VC_3:
                keyCode = java.awt.event.KeyEvent.VK_3;
                break;

            case NativeKeyEvent.VC_4:
                keyCode = java.awt.event.KeyEvent.VK_4;
                break;

            case NativeKeyEvent.VC_5:
                keyCode = java.awt.event.KeyEvent.VK_5;
                break;

            case NativeKeyEvent.VC_6:
                keyCode = java.awt.event.KeyEvent.VK_6;
                break;

            case NativeKeyEvent.VC_7:
                keyCode = java.awt.event.KeyEvent.VK_7;
                break;

            case NativeKeyEvent.VC_8:
                keyCode = java.awt.event.KeyEvent.VK_8;
                break;

            case NativeKeyEvent.VC_9:
                keyCode = java.awt.event.KeyEvent.VK_9;
                break;

            case NativeKeyEvent.VC_0:
                keyCode = java.awt.event.KeyEvent.VK_0;
                break;


            case NativeKeyEvent.VC_MINUS:
                keyCode = java.awt.event.KeyEvent.VK_MINUS;
                break;

            case NativeKeyEvent.VC_EQUALS:
                keyCode = java.awt.event.KeyEvent.VK_EQUALS;
                break;

            case NativeKeyEvent.VC_BACKSPACE:
                keyCode = java.awt.event.KeyEvent.VK_BACK_SPACE;
                break;


            case NativeKeyEvent.VC_TAB:
                keyCode = java.awt.event.KeyEvent.VK_TAB;
                break;

            case NativeKeyEvent.VC_CAPS_LOCK:
                keyCode = java.awt.event.KeyEvent.VK_CAPS_LOCK;
                break;


            case NativeKeyEvent.VC_A:
                keyCode = java.awt.event.KeyEvent.VK_A;
                break;

            case NativeKeyEvent.VC_B:
                keyCode = java.awt.event.KeyEvent.VK_B;
                break;

            case NativeKeyEvent.VC_C:
                keyCode = java.awt.event.KeyEvent.VK_C;
                break;

            case NativeKeyEvent.VC_D:
                keyCode = java.awt.event.KeyEvent.VK_D;
                break;

            case NativeKeyEvent.VC_E:
                keyCode = java.awt.event.KeyEvent.VK_E;
                break;

            case NativeKeyEvent.VC_F:
                keyCode = java.awt.event.KeyEvent.VK_F;
                break;

            case NativeKeyEvent.VC_G:
                keyCode = java.awt.event.KeyEvent.VK_G;
                break;

            case NativeKeyEvent.VC_H:
                keyCode = java.awt.event.KeyEvent.VK_H;
                break;

            case NativeKeyEvent.VC_I:
                keyCode = java.awt.event.KeyEvent.VK_I;
                break;

            case NativeKeyEvent.VC_J:
                keyCode = java.awt.event.KeyEvent.VK_J;
                break;

            case NativeKeyEvent.VC_K:
                keyCode = java.awt.event.KeyEvent.VK_K;
                break;

            case NativeKeyEvent.VC_L:
                keyCode = java.awt.event.KeyEvent.VK_L;
                break;

            case NativeKeyEvent.VC_M:
                keyCode = java.awt.event.KeyEvent.VK_M;
                break;

            case NativeKeyEvent.VC_N:
                keyCode = java.awt.event.KeyEvent.VK_N;
                break;

            case NativeKeyEvent.VC_O:
                keyCode = java.awt.event.KeyEvent.VK_O;
                break;

            case NativeKeyEvent.VC_P:
                keyCode = java.awt.event.KeyEvent.VK_P;
                break;

            case NativeKeyEvent.VC_Q:
                keyCode = java.awt.event.KeyEvent.VK_Q;
                break;

            case NativeKeyEvent.VC_R:
                keyCode = java.awt.event.KeyEvent.VK_R;
                break;

            case NativeKeyEvent.VC_S:
                keyCode = java.awt.event.KeyEvent.VK_S;
                break;

            case NativeKeyEvent.VC_T:
                keyCode = java.awt.event.KeyEvent.VK_T;
                break;

            case NativeKeyEvent.VC_U:
                keyCode = java.awt.event.KeyEvent.VK_U;
                break;

            case NativeKeyEvent.VC_V:
                keyCode = java.awt.event.KeyEvent.VK_V;
                break;

            case NativeKeyEvent.VC_W:
                keyCode = java.awt.event.KeyEvent.VK_W;
                break;

            case NativeKeyEvent.VC_X:
                keyCode = java.awt.event.KeyEvent.VK_X;
                break;

            case NativeKeyEvent.VC_Y:
                keyCode = java.awt.event.KeyEvent.VK_Y;
                break;

            case NativeKeyEvent.VC_Z:
                keyCode = java.awt.event.KeyEvent.VK_Z;
                break;


            case NativeKeyEvent.VC_OPEN_BRACKET:
                keyCode = java.awt.event.KeyEvent.VK_OPEN_BRACKET;
                break;

            case NativeKeyEvent.VC_CLOSE_BRACKET:
                keyCode = java.awt.event.KeyEvent.VK_CLOSE_BRACKET;
                break;

            case NativeKeyEvent.VC_BACK_SLASH:
                keyCode = java.awt.event.KeyEvent.VK_BACK_SLASH;
                break;


            case NativeKeyEvent.VC_SEMICOLON:
                keyCode = java.awt.event.KeyEvent.VK_SEMICOLON;
                break;

            case NativeKeyEvent.VC_QUOTE:
                keyCode = java.awt.event.KeyEvent.VK_QUOTE;
                break;

            case NativeKeyEvent.VC_ENTER:
                keyCode = java.awt.event.KeyEvent.VK_ENTER;
                break;


            case NativeKeyEvent.VC_COMMA:
                keyCode = java.awt.event.KeyEvent.VK_COMMA;
                break;

            case NativeKeyEvent.VC_PERIOD:
                keyCode = java.awt.event.KeyEvent.VK_PERIOD;
                break;

            case NativeKeyEvent.VC_SLASH:
                keyCode = java.awt.event.KeyEvent.VK_SLASH;
                break;

            case NativeKeyEvent.VC_SPACE:
                keyCode = java.awt.event.KeyEvent.VK_SPACE;
                break;
            // End Alphanumeric Zone


            case NativeKeyEvent.VC_PRINTSCREEN:
                keyCode = java.awt.event.KeyEvent.VK_PRINTSCREEN;
                break;

            case NativeKeyEvent.VC_SCROLL_LOCK:
                keyCode = java.awt.event.KeyEvent.VK_SCROLL_LOCK;
                break;

            case NativeKeyEvent.VC_PAUSE:
                keyCode = java.awt.event.KeyEvent.VK_PAUSE;
                break;


            // Begin Edit Key Zone
            case NativeKeyEvent.VC_INSERT:
                keyCode = java.awt.event.KeyEvent.VK_INSERT;
                break;

            case NativeKeyEvent.VC_DELETE:
                keyCode = java.awt.event.KeyEvent.VK_DELETE;
                break;

            case NativeKeyEvent.VC_HOME:
                keyCode = java.awt.event.KeyEvent.VK_HOME;
                break;

            case NativeKeyEvent.VC_END:
                keyCode = java.awt.event.KeyEvent.VK_END;
                break;

            case NativeKeyEvent.VC_PAGE_UP:
                keyCode = java.awt.event.KeyEvent.VK_PAGE_UP;
                break;

            case NativeKeyEvent.VC_PAGE_DOWN:
                keyCode = java.awt.event.KeyEvent.VK_PAGE_DOWN;
                break;
            // End Edit Key Zone


            // Begin Cursor Key Zone
            case NativeKeyEvent.VC_UP:
                keyCode = java.awt.event.KeyEvent.VK_UP;
                break;
            case NativeKeyEvent.VC_LEFT:
                keyCode = java.awt.event.KeyEvent.VK_LEFT;
                break;
            case NativeKeyEvent.VC_CLEAR:
                keyCode = java.awt.event.KeyEvent.VK_CLEAR;
                break;
            case NativeKeyEvent.VC_RIGHT:
                keyCode = java.awt.event.KeyEvent.VK_RIGHT;
                break;
            case NativeKeyEvent.VC_DOWN:
                keyCode = java.awt.event.KeyEvent.VK_DOWN;
                break;
            // End Cursor Key Zone


            // Begin Numeric Zone
            case NativeKeyEvent.VC_NUM_LOCK:
                keyCode = java.awt.event.KeyEvent.VK_NUM_LOCK;
                break;

            case NativeKeyEvent.VC_SEPARATOR:
                keyCode = java.awt.event.KeyEvent.VK_SEPARATOR;
                break;
            // End Numeric Zone


            // Begin Modifier and Control Keys
            case NativeKeyEvent.VC_SHIFT:
                keyCode = java.awt.event.KeyEvent.VK_SHIFT;
                break;

            case NativeKeyEvent.VC_CONTROL:
                keyCode = java.awt.event.KeyEvent.VK_CONTROL;
                break;

            case NativeKeyEvent.VC_ALT:
                keyCode = java.awt.event.KeyEvent.VK_ALT;
                break;

            case NativeKeyEvent.VC_META:
                keyCode = java.awt.event.KeyEvent.VK_META;
                break;

            case NativeKeyEvent.VC_CONTEXT_MENU:
                keyCode = java.awt.event.KeyEvent.VK_CONTEXT_MENU;
                break;
            // End Modifier and Control Keys


      /* Begin Media Control Keys
      case NativeKeyEvent.VC_POWER:
      case NativeKeyEvent.VC_SLEEP:
      case NativeKeyEvent.VC_WAKE:
      case NativeKeyEvent.VC_MEDIA_PLAY:
      case NativeKeyEvent.VC_MEDIA_STOP:
      case NativeKeyEvent.VC_MEDIA_PREVIOUS:
      case NativeKeyEvent.VC_MEDIA_NEXT:
      case NativeKeyEvent.VC_MEDIA_SELECT:
      case NativeKeyEvent.VC_MEDIA_EJECT:
      case NativeKeyEvent.VC_VOLUME_MUTE:
      case NativeKeyEvent.VC_VOLUME_UP:
      case NativeKeyEvent.VC_VOLUME_DOWN:
      case NativeKeyEvent.VC_APP_MAIL:
      case NativeKeyEvent.VC_APP_CALCULATOR:
      case NativeKeyEvent.VC_APP_MUSIC:
      case NativeKeyEvent.VC_APP_PICTURES:
      case NativeKeyEvent.VC_BROWSER_SEARCH:
      case NativeKeyEvent.VC_BROWSER_HOME:
      case NativeKeyEvent.VC_BROWSER_BACK:
      case NativeKeyEvent.VC_BROWSER_FORWARD:
      case NativeKeyEvent.VC_BROWSER_STOP:
      case NativeKeyEvent.VC_BROWSER_REFRESH:
      case NativeKeyEvent.VC_BROWSER_FAVORITES:
      // End Media Control Keys */


            // Begin Japanese Language Keys
            case NativeKeyEvent.VC_KATAKANA:
                keyCode = java.awt.event.KeyEvent.VK_KATAKANA;
                break;

            case NativeKeyEvent.VC_UNDERSCORE:
                keyCode = java.awt.event.KeyEvent.VK_UNDERSCORE;
                break;

            //case VC_FURIGANA:

            case NativeKeyEvent.VC_KANJI:
                keyCode = java.awt.event.KeyEvent.VK_KANJI;
                break;

            case NativeKeyEvent.VC_HIRAGANA:
                keyCode = java.awt.event.KeyEvent.VK_HIRAGANA;
                break;

            //case VC_YEN:
            // End Japanese Language Keys


            // Begin Sun keyboards
            case NativeKeyEvent.VC_SUN_HELP:
                keyCode = java.awt.event.KeyEvent.VK_HELP;
                break;

            case NativeKeyEvent.VC_SUN_STOP:
                keyCode = java.awt.event.KeyEvent.VK_STOP;
                break;

            //case VC_SUN_FRONT:

            //case VC_SUN_OPEN:

            case NativeKeyEvent.VC_SUN_PROPS:
                keyCode = java.awt.event.KeyEvent.VK_PROPS;
                break;

            case NativeKeyEvent.VC_SUN_FIND:
                keyCode = java.awt.event.KeyEvent.VK_FIND;
                break;

            case NativeKeyEvent.VC_SUN_AGAIN:
                keyCode = java.awt.event.KeyEvent.VK_AGAIN;
                break;

            //case NativeKeyEvent.VC_SUN_INSERT:

            case NativeKeyEvent.VC_SUN_COPY:
                keyCode = java.awt.event.KeyEvent.VK_COPY;
                break;

            case NativeKeyEvent.VC_SUN_CUT:
                keyCode = java.awt.event.KeyEvent.VK_CUT;
                break;
            // End Sun keyboards
        }

        return new java.awt.event.KeyEvent(
                new SwingKeyAdapter(),
                nativeEvent.getID() - (NativeKeyEvent.NATIVE_KEY_FIRST - KeyEvent.KEY_FIRST),
                System.currentTimeMillis(),
                getJavaModifiers(nativeEvent.getModifiers()),
                keyCode,
                nativeEvent.getKeyChar(),
                keyLocation);
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (myapplet != null) {
            java.awt.event.KeyEvent jke = getJavaKeyEvent(e);
            processing.event.KeyEvent pe = new processing.event.KeyEvent(jke, jke.getWhen(), processing.event.KeyEvent.PRESS, jke.getModifiers(), jke.getKeyChar(), jke.getKeyCode());
            myapplet.postEvent(pe);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (myapplet != null) {
            java.awt.event.KeyEvent jke = getJavaKeyEvent(e);
            processing.event.KeyEvent pe = new processing.event.KeyEvent(jke, jke.getWhen(), processing.event.KeyEvent.RELEASE, jke.getModifiers(), jke.getKeyChar(), jke.getKeyCode());
            myapplet.postEvent(pe);
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        if (myapplet != null) {
            java.awt.event.KeyEvent jke = getJavaKeyEvent(e);
            processing.event.KeyEvent pe = new processing.event.KeyEvent(jke, jke.getWhen(), processing.event.KeyEvent.TYPE, jke.getModifiers(), jke.getKeyChar(), jke.getKeyCode());
            myapplet.postEvent(pe);
        }
    }
}
