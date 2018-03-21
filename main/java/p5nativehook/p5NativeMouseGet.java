package p5nativehook;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;
import org.jnativehook.mouse.SwingMouseAdapter;
import org.jnativehook.mouse.SwingMouseWheelAdapter;
import processing.core.PApplet;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class p5NativeMouseGet extends PApplet implements NativeMouseInputListener, NativeMouseWheelListener {

    private PApplet myapplet;

    public p5NativeMouseGet(PApplet _applet) {
        myapplet=_applet;
        if (!GlobalScreen.isNativeHookRegistered()) {
            try {
                GlobalScreen.registerNativeHook();
            }
            catch (NativeHookException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        suppressLogger();
        GlobalScreen.addNativeMouseListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
        GlobalScreen.addNativeMouseWheelListener(this);
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

    private java.awt.event.MouseEvent getJavaMouseEvent(NativeMouseEvent nativeEvent) {
        return new java.awt.event.MouseEvent(
                new SwingMouseAdapter(),
                nativeEvent.getID() - (NativeMouseEvent.NATIVE_MOUSE_FIRST - NativeMouseEvent.NATIVE_MOUSE_FIRST),
                System.currentTimeMillis(),
                getJavaModifiers(nativeEvent.getModifiers()),
                nativeEvent.getX(),
                nativeEvent.getY(),
                nativeEvent.getClickCount(),
                false,
                nativeEvent.getButton());
    }

    java.awt.event.MouseWheelEvent getJavaMouseWheelEvent(NativeMouseWheelEvent nativeEvent) {
        int scrollType = MouseWheelEvent.WHEEL_UNIT_SCROLL;
        if (nativeEvent.getScrollType() == NativeMouseWheelEvent.WHEEL_BLOCK_SCROLL) {
            scrollType = MouseWheelEvent.WHEEL_BLOCK_SCROLL;
        }

        return new java.awt.event.MouseWheelEvent(
                new SwingMouseWheelAdapter(),
                nativeEvent.getID() - (NativeMouseWheelEvent.NATIVE_MOUSE_FIRST - NativeMouseWheelEvent.NATIVE_MOUSE_FIRST),
                System.currentTimeMillis(),
                getJavaModifiers(nativeEvent.getModifiers()),
                nativeEvent.getX(),
                nativeEvent.getY(),
                nativeEvent.getClickCount(),
                false,
                scrollType,
                nativeEvent.getScrollAmount(),
                nativeEvent.getWheelRotation());
    }


    public void nativeMouseClicked(NativeMouseEvent e) {
        if (myapplet!=null) {
            java.awt.event.MouseEvent jme=getJavaMouseEvent(e);
            processing.event.MouseEvent pe=new processing.event.MouseEvent(jme, jme.getWhen(), processing.event.MouseEvent.CLICK, jme.getModifiers(), jme.getX(), jme.getY(), jme.getButton(), jme.getClickCount());
            myapplet.postEvent(pe);
        }
    }
    public void nativeMousePressed(NativeMouseEvent e) {
        if (myapplet!=null) {
            java.awt.event.MouseEvent jme=getJavaMouseEvent(e);
            processing.event.MouseEvent pe=new processing.event.MouseEvent(jme, jme.getWhen(), processing.event.MouseEvent.PRESS, jme.getModifiers(), jme.getX(), jme.getY(), jme.getButton(), jme.getClickCount());
            myapplet.postEvent(pe);
        }
    }
    public void nativeMouseReleased(NativeMouseEvent e) {
        if (myapplet!=null) {
            java.awt.event.MouseEvent jme=getJavaMouseEvent(e);
            processing.event.MouseEvent pe=new processing.event.MouseEvent(jme, jme.getWhen(), processing.event.MouseEvent.RELEASE, jme.getModifiers(), jme.getX(), jme.getY(), jme.getButton(), jme.getClickCount());
            myapplet.postEvent(pe);
        }
    }
    public void nativeMouseMoved(NativeMouseEvent e) {
        if (myapplet!=null) {
            java.awt.event.MouseEvent jme=getJavaMouseEvent(e);
            processing.event.MouseEvent pe=new processing.event.MouseEvent(jme, jme.getWhen(), processing.event.MouseEvent.MOVE, jme.getModifiers(), jme.getX(), jme.getY(), jme.getButton(), jme.getClickCount());
            myapplet.postEvent(pe);
        }
    }
    public void nativeMouseDragged(NativeMouseEvent e) {
        if (myapplet!=null) {
            java.awt.event.MouseEvent jme=getJavaMouseEvent(e);
            processing.event.MouseEvent pe=new processing.event.MouseEvent(jme, jme.getWhen(), processing.event.MouseEvent.DRAG, jme.getModifiers(),jme.getX(),jme.getY(),jme.getButton(),jme.getClickCount());
            myapplet.postEvent(pe);
        }
    }
    public void nativeMouseEntered(NativeMouseEvent e) {
        if (myapplet!=null) {
            java.awt.event.MouseEvent jme=getJavaMouseEvent(e);
            processing.event.MouseEvent pe=new processing.event.MouseEvent(jme, jme.getWhen(), processing.event.MouseEvent.ENTER, jme.getModifiers(),jme.getX(),jme.getY(),jme.getButton(),jme.getClickCount());
            myapplet.postEvent(pe);
        }
    }
    public void nativeMouseExited(NativeMouseEvent e) {
        if (myapplet!=null) {
            java.awt.event.MouseEvent jme=getJavaMouseEvent(e);
            processing.event.MouseEvent pe=new processing.event.MouseEvent(jme, jme.getWhen(), processing.event.MouseEvent.EXIT, jme.getModifiers(),jme.getX(),jme.getY(),jme.getButton(),jme.getClickCount());
            myapplet.postEvent(pe);
        }
    }
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        if (myapplet!=null) {
            java.awt.event.MouseEvent jme=getJavaMouseWheelEvent(e);
            processing.event.MouseEvent pe=new processing.event.MouseEvent(jme, jme.getWhen(), processing.event.MouseEvent.WHEEL, jme.getModifiers(),jme.getX(),jme.getY(),jme.getButton(),jme.getClickCount());
            myapplet.postEvent(pe);
        }
    }

}
