package org.mksimpler.test

import org.junit.jupiter.api.Test
import org.mksimpler.decrypt

internal class Test {

    @Test
    fun test() {
        val INPUT_STRING = "UdkPzRUyzC5UR89RAfJbH7C5CM8vTZCa4thgUlUnOF3NmFlTjgbykCv0bF19XhyQcfpAO0htWA2SkWEBlmRrET6jP5whEQZkXYsfNesChZwhO2ikRF/bL4z5EkYMKhx4j0LfmPJHSg+NvFFuDp4GeIUOBmt6ur+IpvVOT/pvUY3xbYzaY25wEvKkTvk2OZKdZH8Ri41uNMaKTIwj6x3Ssi3l4AM8E0PGukKmhIhkJQgpYkFIw0sszjmL5RXVm9QkZdvvWKUJtpPt7G1PDnlFNJG2mXQNKfP73lLmHpVCXxJdg81n5fGgos+Tp0dw8jmvq98svcqh3CxCCXowuVexd+aArPciS0xCvVA8pnh2Pv9YO9ghi0Ok0491EAUTRsjLgqHI8VovlD6Df12kE5y2XVfJRBhCUX+WNbVv0BoTaZkgfGecTR0mstzDpMLl51UTjUkP2jzhwrP1BvTheP1oznBanLMRWfWyQMdr6/ISCjTpOuE8s2jEmvNqHqG195/isqX+8v4RKWRdN24vqURZsgzqiifSW11oOdgvD9MYKVekUoxX+ZRiwgAh6830I2AiYyWCp4WbZRuLXksDLWNU5/0t+6PuGYjujFz3HIFz8WTMTN4LK3KdRsHrlzWYN8dvONzDxcoQIJD2rI7mbXCvHjDOq66wvz6bGR+Rbnn+HfoedeV2cHY5fD3ZAZO/yiGR/poCg6kwa+hn0URs2ug="
        val OUTPUT_EXPECTED = "https://images.hentainexus.com/v2/78da1d8e3b8e02300c05ef921aadfc896387b3a471127b1b40489488bb6f96769e34f3dee555aec5c81494b159ad16ad27b6aad363a30be9640cab86e9a0e4a113856d92094690052fa942594dd722e99abd1d5f73464d7710715960d983bcef2a08115591095b8acfc904512e659f1bef517294eb283b88d238b283389ee671ef24e2368390d7593d1ad7ffbc05aaacf0d9ba5b879db3e22897511e5f1500fe3c1fbf5f723fe47c67306c00073c0f48bfbde2533e7f8d004760/001.png"

        val result = decrypt(INPUT_STRING)
            .replace("\\/", "/")
            .substringAfter("[{\"image\":\"")
            .substringBefore("\",\"label\":")

        assert(result.contentEquals(OUTPUT_EXPECTED))
    }
}