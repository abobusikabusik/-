import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// catches every method call before it reaches the real object
public class Handler implements InvocationHandler
{
    private final Object target; // original object

    // passing the real object here
    public Handler(Object target)
    {
        this.target = target;
    }

    // this method is automatically triggered every time someone tries to call a method on our proxy
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
    {
        // starting the stopwatch
        long start_time = System.nanoTime();

        // letting the real object fo its actual job
        Object result = method.invoke(target, args);

        // stopping the stopwatch right after the real method finishes
        long end_time = System.nanoTime();

        // printing our profiling result (how long it took)
        System.out.println("Метод [" + method.getName() + "] виконувався: " + (end_time - start_time) + " наносекунд.");

        return result;
    }
}
