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
    val path2Write = FileSystems.getDefault().getPath("routes_written.txt")
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
    lineNumber.forEachLine {
        if (lineNumber.lineNumber != 1) {
            writer.write(it)
            writer.newLine()
        }

    }
    writer.close()
}

val s = ".*?,UON.*?,.*?-.*?,\\d"

fun matchAndReplace(array: String) {
    val pattern = Pattern.compile("[,]")
    val matched = pattern.split(array)
    val size = matched.size

    val path2Write = FileSystems.getDefault().getPath("routes_written.txt")
    val writer = Files.newBufferedWriter(path2Write)

    // route ID
    writer.write("\"" + matched[0] + "\": {")
    // route End
    writer.write("\"end\": ")
    val end = getEnd(matched[3])
    writer.write("\"" + end + "\",")
    // route name
    writer.write("\"name\": " + "\"" + matched[3] + "\",")
    // route short name
    writer.write("\"short_name\": " + "\"" + matched[2] + "\",")

    // route start
    val start = getStart(matched[3])
    writer.write("\"" + "start" + "\": " + "\"" + start + "\"")

    // end of this route
    writer.write("\"}\"")

}

// extract the start of the route from the route name
fun getStart(routeName: String): String {
    val pattern = Pattern.compile("[.*?-]")
    val matched = pattern.matcher(routeName)
    val split = pattern.split(routeName)
    return split[matched.groupCount() - 1]
}

// extract the end of the route from the route name
fun getEnd(routeName: String): String {
    val pattern = Pattern.compile(".+?-")
    val matched = pattern.matcher(routeName)
    return matched.group()

}
