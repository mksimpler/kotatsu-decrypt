package org.mksimpler

import java.util.Base64

fun decrypt(encodedData: String): String {
    val xorKey = listOf('h','e','n','t','a','i','n','e','x','u','s','.','c','o','m')
    val keyLength = minOf(xorKey.size, 64)

    // Decode base64 string into characters (1 byte per char, unsigned)
    val decodedBytes = Base64.getDecoder().decode(encodedData)
    val decodedChars = decodedBytes.map { (it.toInt() and 0xFF).toChar() }.toMutableList()

    // XOR first 64 characters with the key
    for (i in 0 until keyLength) {
        decodedChars[i] = (decodedChars[i].code xor xorKey[i].code).toChar()
    }

    val decodedString = decodedChars.joinToString("")

    // Prime sieve: first 16 primes
    val sieve = BooleanArray(257)
    val primeIndexes = mutableListOf<Int>()
    var j = 2
    while (primeIndexes.size < 16) {
        if (!sieve[j]) {
            primeIndexes.add(j)
            for (k in j * 2..256 step j) sieve[k] = true
        }
        j++
    }

    // Hash from first 64 chars
    var hash = 0
    for (k in 0 until 64) {
        hash = hash xor decodedString[k].code
        repeat(8) {
            hash = if ((hash and 1) == 1) {
                (hash ushr 1) xor 12
            } else {
                hash ushr 1
            }
        }
    }
    hash = hash and 7

    // RC4 key scheduling
    val S = IntArray(256) { it }
    j = 0
    for (i in 0 until 256) {
        j = (j + S[i] + decodedString[i % 64].code) % 256
        S[i] = S[j].also { S[j] = S[i] }
    }

    // Decrypt rest of the chars
    val result = StringBuilder()
    var i = 0
    j = 0
    var keyStream = 0
    var rnd = 0
    val step = primeIndexes[hash]

    for (n in 0 until decodedString.length - 64) {
        i = (i + step) % 256
        j = (keyStream + S[(j + S[i]) % 256]) % 256
        keyStream = (keyStream + i + S[i]) % 256
        S[i] = S[j].also { S[j] = S[i] }

        rnd = S[(j + S[(i + S[(rnd + keyStream) % 256]) % 256]) % 256]
        val decryptedChar = decodedString[n + 64].code xor rnd
        result.append(decryptedChar.toChar())
    }

    return result.toString()
}
