package me.r1411.maxcliques;

import me.r1411.maxcliques.objects.Graph;
import me.r1411.maxcliques.objects.Vertex;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        File workingDir = new File(System.getProperty("user.dir"));
        File inputFile = new File(workingDir, "input.txt");
        File outputFile = new File(workingDir, "output.txt");

        byte[][] graphMatrix;

        try {
            graphMatrix = getMatrixFromFile(inputFile);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не удалось прочитать файл: " + inputFile.getAbsolutePath(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Задана некорректная матрица смежности вершин", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Graph graph = new Graph(graphMatrix);
        Set<Set<Vertex>> maxCliques = bronKerbosch(graph, new HashSet<>(), new HashSet<>(), graph.getVertices(), new HashSet<>());
        writeResultToFile(maxCliques, outputFile);
        JOptionPane.showMessageDialog(null, "Максимальные клики вычислены." + System.lineSeparator() + "Выходной файл: " + outputFile.getAbsolutePath(), "Готово", JOptionPane.INFORMATION_MESSAGE);
    }

    private static Set<Set<Vertex>> bronKerbosch(Graph g, Set<Set<Vertex>> maxCliques, Set<Vertex> R, Set<Vertex> P, Set<Vertex> X) {
        if(P.isEmpty() && X.isEmpty()) {
            maxCliques.add(R);
        }

        for(Vertex v : P) {
            bronKerbosch(g, maxCliques, union(R, v), intersect(P, g.getAdjacentTo(v)), intersect(X, g.getAdjacentTo(v)));
            P = difference(P, v);
            X = union(X, v);
        }

        return maxCliques;
    }

    // a AND b
    static Set<Vertex> intersect(Set<Vertex> a, Set<Vertex> b) {
        Set<Vertex> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        return intersection;
    }

    // a OR {v}
    static Set<Vertex> union(Set<Vertex> a, Vertex v) {
        Set<Vertex> union = new HashSet<>(a);
        union.add(v);
        return union;
    }

    // a NOT {v}
    static Set<Vertex> difference(Set<Vertex> a, Vertex v) {
        Set<Vertex> diff = new HashSet<>(a);
        diff.remove(v);
        return diff;
    }

    private static void writeResultToFile(Set<Set<Vertex>> maxCliques, File outputFile) {
        List<String> lines = new ArrayList<>();
        lines.add("Максимальные клики: ");

        for(Set<Vertex> clique : maxCliques) {
            lines.add(clique.toString());
        }

        try {
            Files.write(outputFile.toPath(), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не удалось записать файл: " + outputFile.getAbsolutePath(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private static byte[][] getMatrixFromFile(File file) throws IOException, NumberFormatException, IndexOutOfBoundsException {
        List<String> lines = Files.readAllLines(file.toPath());
        int vertexCount = lines.size();
        byte[][] matrix = new byte[vertexCount][vertexCount];

        for(int i = 0; i < vertexCount; i++) {
            String[] row = lines.get(i).trim().split(" ");
            for(int j = 0; j < row.length; j++) {
                matrix[i][j] = Byte.parseByte(row[j]);
            }
        }

        return matrix;
    }
}
