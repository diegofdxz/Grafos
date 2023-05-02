package org.example;
/*
Implementar un grafo no dirigido que permita cargar puertos y las aristas que conecten dichos
puertos, que permita resolver las siguientes tareas:
a. cada arista debe tener la distancia que separa dichos puertos;
b. realizar un barrido en profundidad desde el primer puerto en el grafo;
c. determinar el camino más corto desde puerto Madero al puerto de Rodas;
d. determinar el puerto con mayor número de aristas y eliminarlo.
 */
import java.util.*;


class Grafos {
    Map<String, Nodo> nodos;

    public Grafos() {
        this.nodos = new HashMap<>();
    }

    public void agregarNodo(String etiqueta) {
        nodos.put(etiqueta, new Nodo(etiqueta));
    }

    public void agregarArista(String etiquetaOrigen, String etiquetaDestino) {
        Nodo origen = nodos.get(etiquetaOrigen);
        Nodo destino = nodos.get(etiquetaDestino);
        origen.adyacentes.add(destino);
        destino.adyacentes.add(origen);
    }

    public void barridoProfundidad(String etiquetaInicio) {
        Nodo nodo = nodos.get(etiquetaInicio);
        Set<Nodo> visitados = new HashSet<>();
        Stack<Nodo> pila = new Stack<>();
        pila.push(nodo);
        while (!pila.isEmpty()) {
            Nodo actual = pila.pop();
            if (!visitados.contains(actual)) {
                visitados.add(actual);
                System.out.println(actual.etiqueta);
                for (Nodo adyacente : actual.adyacentes) {
                    if (!visitados.contains(adyacente)) {
                        pila.push(adyacente);
                    }
                }
            }
        }
    }

    public List<Nodo> dijkstra(String etiquetaOrigen, String etiquetaDestino) {
        Nodo origen = nodos.get(etiquetaOrigen);
        Nodo destino = nodos.get(etiquetaDestino);
        Map<Nodo, Integer> distancias = new HashMap<>();
        Map<Nodo, Nodo> predecesores = new HashMap<>();
        for (Nodo nodo : nodos.values()) {
            distancias.put(nodo, Integer.MAX_VALUE);
            predecesores.put(nodo, null);
        }
        distancias.put(origen, 0);
        Set<Nodo> visitados = new HashSet<>();
        Nodo actual = origen;
        while (actual != null) {
            visitados.add(actual);
            for (Nodo adyacente : actual.adyacentes) {
                if (!visitados.contains(adyacente)) {
                    int nuevaDistancia = distancias.get(actual) + 1;
                    if (nuevaDistancia < distancias.get(adyacente)) {
                        distancias.put(adyacente, nuevaDistancia);
                        predecesores.put(adyacente, actual);
                    }
                }
            }
            actual = null;
            int distanciaMinima = Integer.MAX_VALUE;
            for (Nodo nodo : distancias.keySet()) {
                if (!visitados.contains(nodo) && distancias.get(nodo) < distanciaMinima) {
                    actual = nodo;
                    distanciaMinima = distancias.get(nodo);
                }
            }
        }
        List<Nodo> camino = new ArrayList<>();
        actual = destino;
        while (actual != null) {
            camino.add(actual);
            actual = predecesores.get(actual);
        }
        Collections.reverse(camino);
        return camino;

    }

   public static class Arista extends Nodo {
        Nodo destino;
        int distancia;

        public Arista(Nodo destino, int distancia) {
            super(destino.etiqueta);
            this.distancia = distancia;
            destino.adyacentes.add(new Arista(this, distancia));
        }
    }
    public void eliminarNodoMasAristas() {
        Nodo nodoMaxAristas = null;
        int maxAristas = Integer.MIN_VALUE;

        for (Nodo nodo : nodos.values()) {
            int cantAristas = nodo.adyacentes.size();
            if (cantAristas > maxAristas) {
                maxAristas = cantAristas;
                nodoMaxAristas = nodo;
            }
        }

        nodos.remove(nodoMaxAristas.etiqueta);


    }
}
class Nodo{
    String etiqueta;
    ArrayList<Nodo> adyacentes;
    public Nodo(String etiqueta){
        this.etiqueta = etiqueta;
        this.adyacentes = new ArrayList<>();

    }
    public void agregarArista(Nodo destino) {
        Grafos.Arista arista = new Grafos.Arista(destino,int distancia);
        adyacentes.add(arista);
        destino.adyacentes.add(new Grafos.Arista(this, distancia));
    }
}
public class Menu{
    Scanner scanner = new Scanner(System.in);
    public void menu(){
        int opcion=-1;
        while (opcion != 6)
        System.out.println("1. Agregar Puerto");
        System.out.println("2. Agregar Arista");
        System.out.println("3. Barrido en profundidad");
        System.out.println("4. Camino mas corto");
        System.out.println("5. Eliminar puerto");
        System.out.println("6. Salir");
        opcion = scanner.nextInt();
        Grafos grafo = new Grafos();
        switch (opcion){
            case 1:
                System.out.println("Ingrese el nombre del puerto");
                String puerto = scanner.next();
                grafo.agregarNodo(puerto);
                break;
            case 2:
                System.out.println("Ingrese el nombre del puerto de origen");
                String origen = scanner.next();
                System.out.println("Ingrese el nombre del puerto de destino");
                String destino = scanner.next();
                grafo.agregarArista(origen, destino);
                break;
            case 3:
                System.out.println("Ingrese el nombre del puerto de inicio");
                String inicio = scanner.next();
                grafo.barridoProfundidad(inicio);
                break;
            case 4:
                System.out.println("Ingrese el nombre del puerto de origen");
                origen = scanner.next();
                System.out.println("Ingrese el nombre del puerto de destino");
                destino = scanner.next();
                grafo.dijkstra(origen, destino);
                break;
            case 5:
                grafo.eliminarNodoMasAristas();
                break;


        }
    }

}

