package no.nplm

abstract class Spell {
    abstract fun suggest(word: String, nBest: Long = 0, maxWeight: Float = 0.0f, beam: Float = 0.0f): List<String>
    abstract fun isCorrect(word: String): Boolean
}