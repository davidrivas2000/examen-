import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExamenColaMapa {

    // Cola circular de enteros
    static class ColaCircularEnteros {
        private int[] datos;
        private int cabeza;
        private int cola;
        private int tam;

        public ColaCircularEnteros(int capacidad) {
            datos = new int[capacidad];
            cabeza = 0;
            cola = 0;
            tam = 0;
        }

        public boolean estaVacia() { return tam == 0; }
        public boolean estaLlena() { return tam == datos.length; }

        public void encolar(int x) {
            if (estaLlena()) throw new IllegalStateException("Cola llena");
            datos[cola] = x;
            cola = (cola + 1) % datos.length;
            tam++;
        }

        public int desencolar() {
            if (estaVacia()) throw new IllegalStateException("Cola vacía");
            int v = datos[cabeza];
            cabeza = (cabeza + 1) % datos.length;
            tam--;
            return v;
        }
    }

    // Calcula el balance de paréntesis encolando +1 y -1
    public static int balanceConCola(String s) {
        ColaCircularEnteros cola = new ColaCircularEnteros(s.length());

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') cola.encolar(1);
            else if (c == ')') cola.encolar(-1);
            // otros caracteres se ignoran
        }

        int suma = 0;
        while (!cola.estaVacia()) {
            suma += cola.desencolar();
        }
        return suma;
    }

    // Registra intentos por nombre en un mapa (incrementa si ya existe)
    public static int registrarIntento(Map<String, Integer> intentos, String nombre) {
        int valorActual;
        if (intentos.containsKey(nombre)) {
            valorActual = intentos.get(nombre) + 1;
            intentos.put(nombre, valorActual);
        } else {
            valorActual = 1;
            intentos.put(nombre, valorActual);
        }
        return valorActual;
    }

    // Menú interactivo
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Integer> intentos = new HashMap<String, Integer>();
        // datos de ejemplo
        intentos.put("Ana", 1);
        intentos.put("Juan", 2);

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú ExamenColaMapa ---");
            System.out.println("1. Verificar paréntesis con cola (suma)");
            System.out.println("2. Registrar intento (por nombre)");
            System.out.println("3. Mostrar intentos por nombre");
            System.out.println("4. Salir");
            System.out.print("Seleccione opción: ");

            String opcion = sc.nextLine();
            if (opcion.equals("1")) {
                System.out.print("Ingrese la cadena a procesar: ");
                String cadena = sc.nextLine();
                int suma = balanceConCola(cadena);
                System.out.println("Suma resultante (0 = balanceado): " + suma);
            } else if (opcion.equals("2")) {
                System.out.print("Ingrese el nombre: ");
                String nombre = sc.nextLine();
                int total = registrarIntento(intentos, nombre);
                System.out.println("Intentos de " + nombre + ": " + total);
            } else if (opcion.equals("3")) {
                System.out.println("Intentos registrados:");
                for (Map.Entry<String, Integer> e : intentos.entrySet()) {
                    System.out.println(e.getKey() + " -> " + e.getValue());
                }
            } else if (opcion.equals("4")) {
                salir = true;
                System.out.println("Saliendo...");
            } else {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        sc.close();
    }
}

