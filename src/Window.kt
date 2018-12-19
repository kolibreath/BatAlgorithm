class Window(
        var location:DoubleArray,
        var objectives:Double
):Comparable<Window>{
    override fun compareTo(other: Window): Int {
        return if((this.objectives - other.objectives) > 0 )
            1
        else
            -1
    }
}