package dk.sdu.mmmi.modulemon.Battle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 */
public class GdxTestIntercepter implements InvocationInterceptor, ApplicationListener {

    public GdxTestIntercepter(){
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "LIBGDX TEST RUNNER";
        cfg.width = 852;
        cfg.height = 480;
        cfg.useGL30 = false;
        cfg.resizable = true;
        cfg.allowSoftwareMode = true;

        new LwjglApplication(this, cfg);
    }

    private AtomicReference<Throwable> testThrowable;
    private Invocation<Void> invocationToRun;
    private boolean resultReady = false;

    @Override
    public void interceptTestMethod(Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable {

        testThrowable = new AtomicReference<>();
        resultReady = false;
        invocationToRun = invocation;

        while(!resultReady){
            try {
                Thread.sleep(10);
            }catch(InterruptedException ie){ //We catch this, because we don't want an interrupted exception to fail a test case.
                System.out.println("[Warning] Thread sleep interrupted.");
            }
        }

        Throwable t = testThrowable.get();
        if (t != null) {
            throw t;
        }
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        if(invocationToRun != null && !resultReady) {
            try {
                invocationToRun.proceed();
            } catch (Throwable t) {
                testThrowable.set(t);
            }finally {
                invocationToRun = null;
                resultReady = true;
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
