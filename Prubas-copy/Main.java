import java.util.ArrayList;
import java.util.List;
 
class Main
{
    // Función recursivo para imprimir la ruta del vértice `u` dado desde el vértice fuente `v`
    private static void printPath(int[][] path, int v, int u, List<Integer> route)
    {
        if (path[v][u] == v) {
            return;
        }
        printPath(path, v, path[v][u], route);
        route.add(path[v][u]);
    }
 
    // Función para imprimir el costo más corto con información de ruta entre
    // todos los pares de vértices
    private static void printSolution(int[][] path, int n)
    {
        for (int v = 0; v < n; v++)
        {
            for (int u = 0; u < n; u++)
            {
                if (u != v && path[v][u] != -1)
                {
                    List<Integer> route = new ArrayList<>();
                    route.add(v);
                    printPath(path, v, u, route);
                    route.add(u);
                    System.out.printf("El camino mas corto desde %d —> %d es %s\n",
                        v, u, route);
                }
            }
        }
    }
 
    // Función para ejecutar el algoritmo de Floyd-Warshall
    public static void floydWarshall(int[][] adjMatrix)
    {
        // caso base
        if (adjMatrix ==null || adjMatrix.length == 0) {
            return;
        }
 
        // número total de vértices en `adjMatrix`
        int n = adjMatrix.length;
 
        // cost[] y path[] almacena la ruta más corta
        // (coste más corto/ruta más corta) información
        int[][] cost = new int[n][n];
        int[][] path = new int[n][n];
 
        // inicializar cost[] y path[]
        for (int v = 0; v < n; v++)
        {
            for (int u = 0; u < n; u++)
            {
                // inicialmente, el costo sería el mismo que el peso del borde
                cost[v][u] = adjMatrix[v][u];
 
                if (v == u) {
                    path[v][u] = 0;
                }
                else if (cost[v][u] != Integer.MAX_VALUE) {
                    path[v][u] = v;
                }
                else {
                    path[v][u] = -1;
                }
            }
        }
 
        // ejecutar Floyd-Warshall
        for (int k = 0; k < n; k++)
        {
            for (int v = 0; v < n; v++)
            {
                for (int u = 0; u < n; u++)
                {
                    // Si el vértice `k` está en el camino más corto de `v` a `u`,
                    // luego actualice el valor de cost[v][u] y path[v][u]
 
                    if (cost[v][k] != Integer.MAX_VALUE
                            && cost[k][u] != Integer.MAX_VALUE
                            && (cost[v][k] + cost[k][u] < cost[v][u]))
                    {
                        cost[v][u] = cost[v][k] + cost[k][u];
                        path[v][u] = path[k][u];
                    }
                }
 
                // si los elementos diagonales se vuelven negativos, el
                // el graph contiene un ciclo de peso negativo
                if (cost[v][v] < 0)
                {
                    System.out.println("Ciclo de peso negativo encontrado!!");
                    return;
                }
            }
        }
 
        // Imprime el camino más corto entre todos los pares de vértices
        printSolution(path, n);
    }
 
    public static void main(String[] args)
    {
        // definir infinito
        int I = Integer.MAX_VALUE;
 
        // dada la representación de adyacencia de la matriz
        int[][] adjMatrix = new int[][]
        {
            { 0, I, -2, I },
            { 4, 0, 3, I },
            { I, I, 0, 2 },
            { I, -1, I, 0 }
        };
 
        // Ejecutar el algoritmo de Floyd–Warshall
        floydWarshall(adjMatrix);
    }
}