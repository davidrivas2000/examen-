import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExamenPilaMapa {

    // Clase interna para manejar una pila de enteros
    static class PilaEnteros {
        private int[] datos;
        private int tope; // -1 si está vacía

        public PilaEnteros(int capacidad) {
            datos = new int[capacidad];
            tope = -1;
        }

        public boolean estaVacia() { return tope == -1; }
        public boolean estaLlena() { return tope + 1 == datos.length; }

        public void apilar(int x) {
            if (estaLlena()) throw new IllegalStateException("Pila llena");
            tope++;
            datos[tope] = x;
        }

        public int desapilar() {
            if (estaVacia()) throw new IllegalStateException("Pila vacía");
            int v = datos[tope];
            tope--;
            return v;
        }
    }

    // Verifica si los paréntesis están balanceados usando PilaEnteros
    public static boolean esBalanceado(String s) {
        PilaEnteros pila = new PilaEnteros(s.length());

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                pila.apilar(1); // apilar marcador de '('
            } else if (c == ')') {
                if (pila.estaVacia()) return false; // cierre sin apertura
                pila.desapilar();
            }
        }
        return pila.estaVacia();
    }

    // Actualiza la calificación si el id existe y el nuevo valor está en 0..100
    public static boolean actualizarCalificacion(Map<Integer, Integer> califPorId, int id, int nuevo) {
        if (nuevo < 0 || nuevo > 100) return false; // rango inválido
        if (califPorId.containsKey(id)) {
            califPorId.put(id, nuevo);
            return true;
        }
        return false; // id no existe
    }

    // Menú interactivo
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<Integer, Integer> mapa = new HashMap<Integer, Integer>();
        // datos de ejemplo
        mapa.put(101, 70);
        mapa.put(102, 88);
        mapa.put(103, 55);

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú ExamenPilaMapa ---");
            System.out.println("1. Verificar paréntesis balanceados");
            System.out.println("2. Actualizar calificación (por ID)");
            System.out.println("3. Mostrar todas las calificaciones");
            System.out.println("4. Salir");
            System.out.print("Seleccione opción: ");

            String opcion = sc.nextLine();
            if (opcion.equals("1")) {
                System.out.print("Ingrese la cadena a verificar: ");
                String cadena = sc.nextLine();
                boolean res = esBalanceado(cadena);
                System.out.println("Balanceado: " + res);
            } else if (opcion.equals("2")) {
                try {
                    System.out.print("Ingrese ID (entero): ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Ingrese nueva calificación (0-100): ");
                    int nueva = Integer.parseInt(sc.nextLine());

                    boolean actualizado = actualizarCalificacion(mapa, id, nueva);
                    if (actualizado) {
                        System.out.println("Calificación actualizada correctamente.");
                    } else {
                        if (!mapa.containsKey(id)) System.out.println("Error: ID no existe.");
                        else System.out.println("Error: calificación fuera de rango.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Use números enteros.");
                }
            } else if (opcion.equals("3")) {
                System.out.println("Calificaciones actuales:");
                for (Map.Entry<Integer, Integer> e : mapa.entrySet()) {
                    System.out.println("ID " + e.getKey() + " -> " + e.getValue());
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

