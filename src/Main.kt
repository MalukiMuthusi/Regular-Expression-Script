import java.io.BufferedWriter
import java.io.LineNumberReader
import java.nio.file.FileSystems
import java.nio.file.Files
import java.util.regex.Pattern

fun main() {

    // reader
    val path = FileSystems.getDefault().getPath("routes.txt")
    val reader = Files.newBufferedReader(path)
    val lineNumber = LineNumberReader(reader)

    // writer
    val path2Write = FileSystems.getDefault().getPath("routes_written.json")
    val writer = Files.newBufferedWriter(path2Write)

    /*
    * pattern
    *
    * given 10000114011,UON,114R,Ngara-Rwaka-Ndenderu-Limuru,3
    *   match 10000114011   ```route ID```
    *   match UON
    *   match 114R  ```route_short name```
    *   match Ngara ```start of the route```
    *   match Limuru ```end of route```
    *   match 3
    * */


    // read each line in the file
    writer.write("{")
    lineNumber.forEachLine {
        if (lineNumber.lineNumber != 1) {
            matchAndReplace(it, writer)
        }

    }
    writer.write("}")
    writer.close()
}

fun matchAndReplace(array: String, writer: BufferedWriter) {
    val pattern = Pattern.compile("[,]")
    val matched = pattern.split(array)

    // route ID
    val route_id = matched[0]
    writer.write("\"" + route_id + "\": {")
    // route End
    writer.write("\"end\": ")
    val end = getEnd(matched[3])
    writer.write("\"" + end + "\",")
    // route name
    val route_name = matched[3]
    writer.write("\"name\": " + "\"" + route_name + "\",")
    // route short name
    val routeShortName = matched[2]
    writer.write("\"short_name\": " + "\"" + routeShortName + "\",")

    // route start
    val start = getStart(matched[3])
    writer.write("\"" + "start" + "\": " + "\"" + start + "\"")

    // end of this route
    writer.write("},")


}

// extract the start of the route from the route name
fun getStart(routeName: String): String {
    val pattern = Pattern.compile("[-]")
    pattern.matcher(routeName)
    val split = pattern.split(routeName)
    return split[0]
}

// extract the end of the route from the route name
fun getEnd(routeName: String): String {
    val pattern = Pattern.compile("[-]")
    pattern.matcher(routeName)
    val split = pattern.split(routeName)
    return split[split.size - 1]
}
