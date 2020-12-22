import api.DrawingApi
import api.DrawingApiAwt
import api.DrawingApiJavaFX
import graph.*
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size == 1 && args[0] == "help") {
            println("(awt | javafx) filePath (labeling | adjacency)")
        } else if (args.size != 3) {
            System.err.println("Usage: (awt | javafx) filePath (labeling | adjacency)")
        } else {
            val api: DrawingApi = getDrawingApi(args[0])
            val graph = readGraph(api, args[1], args[2] == "labeling")
            graph.drawGraph()
        }
    }

    private fun getDrawingApi(apiName: String): DrawingApi {
        return when (apiName) {
            "awt" -> {
                DrawingApiAwt()
            }
            "javafx" -> {
                DrawingApiJavaFX()
            }
            else -> {
                throw IllegalArgumentException("Invalid api argument $apiName")
            }
        }
    }

    private fun readGraph(api: DrawingApi, pathToGraph: String, isLabeling: Boolean): Graph {
        val graphStr = readGraph(pathToGraph)
        return if (isLabeling) {
            LabelingGraph(api, readLabeling(graphStr))
        } else {
            AdjacencyGraph(api, readAdjacency(graphStr))
        }
    }

    private fun readGraph(pathToGraph: String): Collection<String> {
        return try {
            Files.readAllLines(Paths.get(pathToGraph))
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    private fun readLabeling(graphStr: Collection<String>): Collection<Vertex> {
        val graph = graphStr.stream()
                .map { line: String -> line.split(" ".toRegex()).toTypedArray() }
                .map { line: Array<String>? -> Arrays.stream(line).mapToInt { s: String -> s.toInt() }.toArray() }
                .collect(Collectors.toList())
        val mapVertex: MutableMap<Int, Vertex> = HashMap()
        for (i in graph.indices) {
            val cur = i + 1
            mapVertex[cur] = Vertex(cur)
            val curNeighbors = graph[i]
            for (other in curNeighbors.indices) {
                if (curNeighbors[other] == 1) {
                    mapVertex[cur]!!.addNeighbour(other + 1)
                }
            }
        }
        return mapVertex.values
    }

    private fun readAdjacency(graphStr: Collection<String>): Collection<Edge> {
        return graphStr.stream()
                .map { line: String -> line.split(" ".toRegex()).toTypedArray() }
                .map { indices: Array<String> -> Edge(indices[0].toInt(), indices[1].toInt()) }
                .collect(Collectors.toList())
    }
}