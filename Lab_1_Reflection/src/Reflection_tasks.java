import java.lang.reflect.*;

public class Reflection_tasks
{
    // analyzing class by its string name
    public static String analyze_class(String class_name)
    {
        try
        {
            // converting string name to actual Class object
            Class<?> cls = switch (class_name) 
            {
                case "int" -> int.class;
                case "double" -> double.class;
                case "float" -> float.class;
                case "long" -> long.class;
                case "short" -> short.class;
                case "byte" -> byte.class;
                case "boolean" -> boolean.class;
                case "char" -> char.class;
                case "void" -> void.class;
                default -> Class.forName(class_name); // for custom classes like "Cat"
            };

            // passing the found class to the main analyze method
            return analyze_class(cls);

        }
        catch (Exception e)
        {
            return "чота тип не знайшовся...";
        }
    }

    // main method to extract all class information (modifiers, fields, methods)
    public static String analyze_class(Class<?> cls)
    {
        StringBuilder sb = new StringBuilder();

        // checking if class belongs to a package
        if (cls.getPackage() != null)
            sb.append("Package: ").append(cls.getPackage().getName()).append("\n");

        // getting modifiers like "public", "abstract", "final"
        sb.append("Modifiers: ")
                .append(Modifier.toString(cls.getModifiers()))
                .append("\n");

        sb.append("Class: ").append(cls.getSimpleName()).append("\n");

        // checking if this class inherits from another class
        Class<?> super_class = cls.getSuperclass();
        if (super_class != null)
            sb.append("Base class: ").append(super_class.getName()).append("\n");

        sb.append("\nInterfaces:\n");

        // getting all interfaces that this class implements
        for (Class<?> i : cls.getInterfaces())
            sb.append("  ").append(i.getName()).append("\n");

        sb.append("\nFields:\n");

        // getDeclaredFields() gets all fields, including private ones
        for (Field f : cls.getDeclaredFields())
            sb.append("  ")
                    .append(Modifier.toString(f.getModifiers()))
                    .append(" ")
                    .append(f.getType().getSimpleName()) // "String" or "int"
                    .append(" ")
                    .append(f.getName()) // "name" or "age"
                    .append("\n");


        sb.append("\nConstructors:\n");
        for (Constructor<?> c : cls.getDeclaredConstructors())
        {
            sb.append("  ")
                    .append(Modifier.toString(c.getModifiers()))
                    .append(" ")
                    .append(c.getName())
                    .append("(");

            // getting parameters required to build the object
            Class<?>[] params = c.getParameterTypes();
            for (int i = 0; i < params.length; i++)
            {
                sb.append(params[i].getSimpleName());

                if (i < params.length - 1)
                    sb.append(", ");
            }

            sb.append(")\n");
        }

        sb.append("\nMethods:\n");

        // getDeclaredMethods() gets all methods, including private ones
        for (Method m : cls.getDeclaredMethods())
        {
            sb.append("  ")
                    .append(Modifier.toString(m.getModifiers()))
                    .append(" ")
                    .append(m.getReturnType().getSimpleName()) // what the method returns
                    .append(" ")
                    .append(m.getName())
                    .append("(");

            Class<?>[] params = m.getParameterTypes();
            for (int i = 0; i < params.length; i++)
            {
                sb.append(params[i].getSimpleName());

                if (i < params.length - 1)
                    sb.append(", ");
            }

            sb.append(")\n");
        }

        return sb.toString();
    }

    // analyzing the actual state of a living object (its current values)
    public static void analyze_object_state(Object obj)
    {
        if (obj == null)
        {
            System.out.println("Об'єкт є null.");
            return;
        }

        Class<?> cls = obj.getClass();
        System.out.println("Реальний тип та стан об'єкта: ");
        System.out.println("Клас: " + cls.getName());

        System.out.println("Поля та їх значення: ");
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields)
        {
            // bypassing private access restrictions to read values
            f.setAccessible(true);
            try
            {
                // f.get(obj) pulls the actual value from our specific object
                System.out.println("  " + f.getType().getSimpleName() + " " + f.getName() + " = " + f.get(obj));
            }
            catch (IllegalAccessException e)
            {
                System.out.println("  " + f.getName() + " = [Немає доступу] :(");
            }
        }

        System.out.println("Доступні відкриті методи (без параметрів): ");
        Method[] methods = cls.getDeclaredMethods();
        java.util.List<Method> callable_methods = new java.util.ArrayList<>();

        int index = 1;
        for (Method m : methods)
        {
            // choosing only public methods that take no parameters
            if (Modifier.isPublic(m.getModifiers()) && m.getParameterCount() == 0)
            {
                callable_methods.add(m);
                System.out.println("  [" + index + "] " + m.getName() + "()");
                index++;
            }
        }

        if (callable_methods.isEmpty())
        {
            System.out.println("Немає методів для виклику.");
            return;
        }

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        // using method to avoid errors
        int choice = read_valid_int(scanner, "Введіть номер методу, який хочете викликати (або 0 для виходу): ");

        if (choice > 0 && choice <= callable_methods.size())
        {
            Method selected_method = callable_methods.get(choice - 1);
            try
            {
                System.out.println("Викликаємо метод " + selected_method.getName() + "()...");

                // executing the method on our object
                Object result = selected_method.invoke(obj);

                // if the method returns something (not void), print it
                if (selected_method.getReturnType() != void.class)
                {
                    System.out.println("Результат: " + result);
                }
                else
                {
                    System.out.println("Метод виконався (нічого не повертає - void).");
                }
            }
            catch (Exception e)
            {
                System.out.println("Помилочка під час виклику: " + e.getMessage());
            }
        }
        else
        {
            System.out.println("Вихід з меню виклику.");
        }
    }

    // calling a specific method by providing its name as a string
    public static Object invoke_method_by_name(Object obj, String method_name, Object... params) throws FunctionNotFoundException
    {
        Class<?> cls = obj.getClass();

        // creating an array to store the types of our parameters
        Class<?>[] param_types = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) 
        {
            param_types[i] = params[i].getClass();
        }

        try 
        {
            // searching for the method that matches both the name and parameter types
            Method method = cls.getMethod(method_name, param_types);
            System.out.println("Метод '" + method_name + "' знайдено успішно.");
            return method.invoke(obj, params); // executing the method

        } 
        catch (NoSuchMethodException e) 
        {
            // throwing custom exception if method doesn't exist
            throw new FunctionNotFoundException("Помилочка: Метод '" + method_name + "' не знайдено у класі " + cls.getSimpleName());
        } 
        catch (Exception e) 
        {
            return "Помилочка виконання: " + e.getMessage();
        }
    }

    // creating one-dimensional array
    public static Object create_array(Class<?> type, int size)
    {
        return Array.newInstance(type, size);
    }

    // creating two-dimensional array (matrix)
    public static Object create_matrix(Class<?> type, int rows, int cols)
    {
        return Array.newInstance(type, rows, cols);
    }

    // changing size of an array
    public static Object resize_array(Object old_array, int new_size) 
    {
        int old_size = Array.getLength(old_array);
        Class<?> component_type = old_array.getClass().getComponentType();

        Object new_array = Array.newInstance(component_type, new_size);

        // copying elements from old array to the new one
        System.arraycopy(old_array, 0, new_array, 0, Math.min(old_size, new_size));

        return new_array;
    }

    // changing size of an array
    public static Object resize_matrix(Object old_matrix, int new_rows, int new_cols)
    {
        int old_rows = Array.getLength(old_matrix);
        Class<?> base_type = old_matrix.getClass().getComponentType().getComponentType();

        Object new_matrix = Array.newInstance(base_type, new_rows, new_cols);

        // copying row by row
        for (int i = 0; i < Math.min(old_rows, new_rows); i++)
        {
            Object old_row = Array.get(old_matrix, i);
            Object new_row = Array.get(new_matrix, i);
            int old_cols = Array.getLength(old_row);

            System.arraycopy(old_row, 0, new_row, 0, Math.min(old_cols, new_cols));
        }
        return new_matrix;
    }

    // making array to string
    public static String array_to_string(Object array)
    {
        if (array == null) return "null";
        if (!array.getClass().isArray()) return array.toString();

        int length = Array.getLength(array);
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < length; i++)
        {
            Object element = Array.get(array, i);

            // if elements is also an array, calling this method again
            if (element != null && element.getClass().isArray())
            {
                sb.append(array_to_string(element));
            }
            else
            {
                sb.append(element);
            }
            if (i < length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // reading safely integer
    public static int read_valid_int(java.util.Scanner scanner, String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            if (scanner.hasNextInt())
            {
                return scanner.nextInt(); // returning, if its a number
            }
            else
            {
                System.out.println("Помилочка: потрібно ввести саме цифру... Давай ще раз.");
                scanner.next(); // clearing invalid input from scanner memory
            }
        }
    }

    // creating a dynamic proxy (a fake object that looks and acts like the real one)
    public static Object create_proxy(Object target, Class<?> interface_type)
    {
        return java.lang.reflect.Proxy.newProxyInstance(
                target.getClass().getClassLoader(), // class loader of the real object
                new Class<?>[]{interface_type}, // list of interfaces the proxy must implement
                new Handler(target) // custom spy handler that will intercept calls
        );
    }
}