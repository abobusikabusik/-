import java.lang.reflect.*;

public class Reflection_tasks {

    public static String analyzeClass(String className) {

        try {

            Class<?> cls;

            switch (className) {
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

            return analyzeClass(cls);

        } catch (Exception e) {
            return "Тип не знайдено!";
        }
    }


    public static String analyzeClass(Class<?> cls) {

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

        for (Constructor<?> c : cls.getDeclaredConstructors()) {

            sb.append("  ")
                    .append(Modifier.toString(c.getModifiers()))
                    .append(" ")
                    .append(c.getName())
                    .append("(");

            Class<?>[] params = c.getParameterTypes();

            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].getSimpleName());

                if (i < params.length - 1)
                    sb.append(", ");
            }

            sb.append(")\n");
        }


        sb.append("\nMethods:\n");

        for (Method m : cls.getDeclaredMethods()) {

            sb.append("  ")
                    .append(Modifier.toString(m.getModifiers()))
                    .append(" ")
                    .append(m.getReturnType().getSimpleName())
                    .append(" ")
                    .append(m.getName())
                    .append("(");

            Class<?>[] params = m.getParameterTypes();

            for (int i = 0; i < params.length; i++) {

                sb.append(params[i].getSimpleName());

                if (i < params.length - 1)
                    sb.append(", ");
            }

            sb.append(")\n");
        }

        return sb.toString();
    }
}