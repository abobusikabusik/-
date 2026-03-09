import java.lang.reflect.*;

public class Reflection_tasks
{
    public static String analyze_class(String className)
    {
        try
        {
            Class<?> cls;
            switch (className)
            {
                case "int": cls = int.class; break;
                case "double": cls = double.class; break;
                case "float": cls = float.class; break;
                case "long": cls = long.class; break;
                case "short": cls = short.class; break;
                case "byte": cls = byte.class; break;
                case "boolean": cls = boolean.class; break;
                case "char": cls = char.class; break;
                case "void": cls = void.class; break;
                default:
                    cls = Class.forName(className);
            }

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
        System.out.println("\nРеальний тип та стан об'єкта: ");
        System.out.println("Клас: " + cls.getName());

        System.out.println("\nПоля та їх значення: ");
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
                System.out.println("  " + f.getName() + " = [Немає доступу]");
            }
        }

        System.out.println("\nДоступні відкриті методи (без параметрів): ");
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
        System.out.print("\nВведіть номер методу, який хочете викликати (або 0 для виходу): ");
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
}