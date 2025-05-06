function decrypt(encodedData) {
    var xorKey = ["h", "e", "n", "t", "a", "i", "n", "e", "x", "u", "s", ".", "c", "o", "m"];
    var keyLength = Math.min(xorKey.length, 64);
    var decodedChars = atob(encodedData).split('');

    for (var i = 0; i < keyLength; i++) {
        decodedChars[i] = String.fromCharCode(
            decodedChars[i].charCodeAt(0) ^ xorKey[i].charCodeAt(0)
        );
    }
    decodedChars = decodedChars.join('');

    var sieve = [];
    var primeIndexes = [];
    for (let j = 2; primeIndexes.length < 16; ++j) {
        if (!sieve[j]) {
            primeIndexes.push(j);
            for (let k = j * 2; k <= 256; k += j) {
                sieve[k] = true;
            }
        }
    }

    var hash = 0;

    for (var j = 0; j < 64; j++) {
        hash ^= decodedChars.charCodeAt(j);

        for (var bit = 0; bit < 8; bit++) {
            if (hash & 1) {
                hash = (hash >>> 1) ^ 12;
            } else {
                hash = hash >>> 1;
            }
        }
    }

    hash = hash & 7; // Only use lowest 3 bits (value from 0 to 7)

    var S = [];
    var j = 0;
    var temp;

    for (let i = 0; i < 256; i++) {
        S[i] = i;
    }
    for (let i = 0; i < 256; i++) {
        j = (j + S[i] + decodedChars.charCodeAt(i % 64)) % 256;
        temp = S[i];
        S[i] = S[j];
        S[j] = temp;
    }

    var i = 0;
    var j = 0;
    var result = '';
    var rnd = 0;
    var keyStream = 0;
    var step = primeIndexes[hash];

    for (let n = 0; n + 64 < decodedChars.length; n++) {
        i = (i + step) % 256;
        j = (keyStream + S[(j + S[i]) % 256]) % 256;
        keyStream = (keyStream + i + S[i]) % 256;
        temp = S[i];
        S[i] = S[j];
        S[j] = temp;
        rnd = S[(j + S[(i + S[(rnd + keyStream) % 256]) % 256]) % 256];
        result += String.fromCharCode(decodedChars.charCodeAt(n + 64) ^ rnd);
    }

	return result;
}

var result = decrypt("UdkPzRUyzC5UR89RAfJbH7C5CM8vTZCa4thgUlUnOF3NmFlTjgbykCv0bF19XhyQcfpAO0htWA2SkWEBlmRrET6jP5whEQZkXYsfNesChZwhO2ikRF/bL4z5EkYMKhx4j0LfmPJHSg+NvFFuDp4GeIUOBmt6ur+IpvVOT/pvUY3xbYzaY25wEvKkTvk2OZKdZH8Ri41uNMaKTIwj6x3Ssi3l4AM8E0PGukKmhIhkJQgpYkFIw0sszjmL5RXVm9QkZdvvWKUJtpPt7G1PDnlFNJG2mXQNKfP73lLmHpVCXxJdg81n5fGgos+Tp0dw8jmvq98svcqh3CxCCXowuVexd+aArPciS0xCvVA8pnh2Pv9YO9ghi0Ok0491EAUTRsjLgqHI8VovlD6Df12kE5y2XVfJRBhCUX+WNbVv0BoTaZkgfGecTR0mstzDpMLl51UTjUkP2jzhwrP1BvTheP1oznBanLMRWfWyQMdr6/ISCjTpOuE8s2jEmvNqHqG195/isqX+8v4RKWRdN24vqURZsgzqiifSW11oOdgvD9MYKVekUoxX+ZRiwgAh6830I2AiYyWCp4WbZRuLXksDLWNU5/0t+6PuGYjujFz3HIFz8WTMTN4LK3KdRsHrlzWYN8dvONzDxcoQIJD2rI7mbXCvHjDOq66wvz6bGR+Rbnn+HfoedeV2cHY5fD3ZAZO/yiGR/poCg6kwa+hn0URs2ug=")

console.log(result);
console.log(JSON.parse(result)[0].image === "https://images.hentainexus.com/v2/78da1d8e3b8e02300c05ef921aadfc896387b3a471127b1b40489488bb6f96769e34f3dee555aec5c81494b159ad16ad27b6aad363a30be9640cab86e9a0e4a113856d92094690052fa942594dd722e99abd1d5f73464d7710715960d983bcef2a08115591095b8acfc904512e659f1bef517294eb283b88d238b283389ee671ef24e2368390d7593d1ad7ffbc05aaacf0d9ba5b879db3e22897511e5f1500fe3c1fbf5f723fe47c67306c00073c0f48bfbde2533e7f8d004760/001.png")
