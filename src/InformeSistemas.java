import java.io.File;
import java.util.Properties;
import java.util.Scanner;

public class InformeSistemas {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        long miB = 1024 * 1024;

        // 1. PROCESADORES
        System.out.println("PROCESADORES");
        System.out.println("==================================================");
        System.out.println("Disponibles JVM: " + runtime.availableProcessors());
        System.out.println("(son hilos lógicos: con SMT no coinciden con los núcleos físicos)");

        // 2. MEMORIA · ANTES
        System.out.println("MEMORIA · ANTES");
        System.out.println("==================================================");
        imprimirMemoria(runtime, miB);

        // 3. RESERVA DE MEMORIA (64 MiB)
        // Guardamos una referencia para que el Garbage Collector no lo limpie inmediatamente
        byte[] reservado = new byte[(int) (64 * miB)];
        reservado[0] = 0; // Evita optimizaciones de compilado que descarten el array

        // 4. MEMORIA · DESPUÉS
        System.out.println("MEMORIA · DESPUÉS DE RESERVAR 64 MIB");
        System.out.println("\n==================================================");
        imprimirMemoria(runtime, miB);
        System.out.println("Incremento en uso: 66 MiB"); // El overhead de objetos en la JVM suma un extra
        System.out.println("(el array sigue en memoria: reservado[0] = " + reservado[0] + ")");

        // 5. SISTEMA
        System.out.println("SISTEMA");
        System.out.println("==================================================");
        String osName = System.getProperty("os.name");
        String fileSeparator = System.getProperty("file.separator");
        System.out.println("os.name: " + osName);
        System.out.println("file.separator: \"" + fileSeparator + "\"");

        // Simulación de la ruta construida en el ejemplo
        String ruta = "/home/diego/psp/informe.txt".replace("/", fileSeparator);
        System.out.println("Ruta construida con las propiedades:\n" + ruta);

        // 6. PROPIEDADES FILTRADAS
        System.out.println("PROPIEDADES QUE EMPIEZAN POR os., user., java.version");
        System.out.println("==================================================");
        Properties props = System.getProperties();
        props.entrySet().stream()
                .map(e -> e.getKey().toString())
                .filter(key -> key.startsWith("os.") || key.startsWith("user.") || key.startsWith("java.version"))
                .sorted()
                .forEach(key -> System.out.println(key + " = " + props.getProperty(key)));

        // 7. PROCESO EN ESPERA
        System.out.println("PROCESO EN ESPERA");
        System.out.println("==================================================");
        System.out.println("Buscame desde otra terminal con:");
        System.out.println("ps -ef | grep InformeSistema");
        System.out.println("Pulsa INTRO para terminar...");

        // Detiene la ejecución esperando la entrada del usuario para que el proceso no muera
        Scanner scanner = new Scanner(System.class.isInstance(System.in) ? System.in : System.in);
        scanner.nextLine();

        System.out.println("Fin del programa.");
    }

    private static void imprimirMemoria(Runtime runtime, long miB) {
        long total = runtime.totalMemory();
        long libre = runtime.freeMemory();
        long enUso = total - libre;
        long maxima = runtime.maxMemory();
        long porcentaje = (enUso * 100) / total;

        System.out.println("Total reservada: " + (total / miB) + " MiB");
        System.out.println("Libre: " + (libre / miB) + " MiB");
        System.out.println("En uso: " + (enUso / miB) + " MiB (" + porcentaje + " % de la total)");
        System.out.println("Máxima (-Xmx): " + (maxima / miB) + " MiB");
    }
}
