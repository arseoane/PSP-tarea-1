public class InformeSistemas {

    public static void main() {
        long[] reservado = new long[8 * 1024 * 1024];

        Runtime runtime = Runtime.getRuntime();

        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        int processors = runtime.availableProcessors();

        System.out.println("=== ESTADO DEL EQUIPO (JVM) ===");
        System.out.println("Núcleos de CPU disponibles: " + processors);
        System.out.println("Memoria total asignada: " + (totalMemory / 1024 / 1024) + " MB");
        System.out.println("Memoria libre: " + (freeMemory / 1024 / 1024) + " MB");
        System.out.println("Memoria máxima permitida: " + (maxMemory / 1024 / 1024) + " MB");
    }
}