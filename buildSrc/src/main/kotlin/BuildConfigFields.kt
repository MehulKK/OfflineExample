object BuildConfigFields {

    private object Type {
        const val STRING = "String"
    }

    data class Field(val type: String, val title: String, val value: String)
}
