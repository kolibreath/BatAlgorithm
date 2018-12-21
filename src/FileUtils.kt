import java.io.File

class FileUtils {

    companion object {

        fun File.write(content:String){
            this.appendText(content)
        }
    }
}