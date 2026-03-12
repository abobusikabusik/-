import java.lang.reflect.*;

public class Reflection_tasks
{
    public static String analyze_class(String class_name)
    {
        try
        {
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
                default -> Class.forName(class_name);
            };

            return analyze_class(cls);

        }
        catch (Exception e)
        {
            return "чота тип не знайшовся...";
        }
    }


    public static String analyze_class(Class<?> cls)
    {
        StringBuilder sb = new StringBuilder();
        if (cls.getPackage() != null)
            sb.append("Package: ").append(cls.getPackage().getName()).append("\n");

        sb.append("Modifiers: ")
                .append(Modifier.toString(cls.getModifiers()))
                .append("\n");

        sb.append("Class: ").append(cls.getSimpleName()).append("\n");

        Class<?> superClass = cls.getSuperclass();

        if (superClass != null)
            sb.append("Base class: ").append(superClass.getName()).append("\n");


        sb.append("\nInterfaces:\n");

        for (Class<?> i : cls.getInterfaces())
            sb.append("  ").append(i.getName()).append("\n");


        sb.append("\nFields:\n");

        for (Field f : cls.getDeclaredFields())
            sb.append("  ")
                    .append(Modifier.toString(f.getModifiers()))
                    .append(" ")
                    .append(f.getType().getSimpleName())
                    .append(" ")
                    .append(f.getName())
                    .append("\n");


        sb.append("\nConstructors:\n");

        for (Constructor<?> c : cls.getDeclaredConstructors())
        {
            sb.append("  ")
                    .append(Modifier.toString(c.getModifiers()))
                    .append(" ")
                    .append(c.getName())
                    .append("(");

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

        for (Method m : cls.getDeclaredMethods())
        {
            sb.append("  ")
                    .append(Modifier.toString(m.getModifiers()))
                    .append(" ")
                    .append(m.getReturnType().getSimpleName())
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

    public static void analyze_object_state(Object obj)
    {
        if (obj == null)
        {
            System.out.println("Об'єкт є null!");
            return;
        }

        Class<?> cls = obj.getClass();
        System.out.println("Реальний тип та стан об'єкта: ");
        System.out.println("Клас: " + cls.getName());

        System.out.println("Поля та їх значення: ");
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields)
        {
            f.setAccessible(true);
            try
            {
                System.out.println("  " + f.getType().getSimpleName() + " " + f.getName() + " = " + f.get(obj));
            }
            catch (IllegalAccessException e)
            {
                System.out.println("  " + f.getName() + " = [Немає доступу] :(");
            }
        }

        System.out.println("Доступні відкриті методи (без параметрів): ");
        Method[] methods = cls.getDeclaredMethods();
        java.util.List<Method> callableMethods = new java.util.ArrayList<>();

        int index = 1;
        for (Method m : methods)
        {
            if (Modifier.isPublic(m.getModifiers()) && m.getParameterCount() == 0) {
                callableMethods.add(m);
                System.out.println("  [" + index + "] " + m.getName() + "()");
                index++;
            }
        }

        if (callableMethods.isEmpty())
        {
            System.out.println("Немає методів для виклику.");
            return;
        }

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Введіть номер методу, який хочете викликати (або 0 для виходу): ");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= callableMethods.size())
        {
            Method selectedMethod = callableMethods.get(choice - 1);
            try
            {
                System.out.println("Викликаємо метод " + selectedMethod.getName() + "()...");
                Object result = selectedMethod.invoke(obj);

                if (selectedMethod.getReturnType() != void.class)
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

    public static Object invoke_method_by_name(Object obj, String method_name, Object... params) throws FunctionNotFoundException
    {
        Class<?> cls = obj.getClass();
        Class<?>[] paramTypes = new Class<?>[params.length];
        
        for (int i = 0; i < params.length; i++) 
        {
            paramTypes[i] = params[i].getClass();
        }

        try 
        {
            Method method = cls.getMethod(method_name, paramTypes);
            System.out.println("Метод '" + method_name + "' знайдено успішно.");
            return method.invoke(obj, params);

        } 
        catch (NoSuchMethodException e) 
        {
            throw new FunctionNotFoundException("Помилочка: Метод '" + method_name + "' не знайдено у класі " + cls.getSimpleName());
        } 
        catch (Exception e) 
        {
            return "Помилочка виконання: " + e.getMessage();
        }
    }
}