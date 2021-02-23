package io.github.martinyes.httpclient.data.response.body;

import io.github.martinyes.httpclient.data.response.BodyHandler;
import io.github.martinyes.httpclient.data.response.impl.WrappedHttpResponse;

import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

/**
 * Byte array Body Handler.
 *
 * @author martin
 */
public class ByteArrayBodyHandler implements BodyHandler<byte[]> {

    @Override
    public byte[] apply(WrappedHttpResponse res) {
        CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();

        try {
            encoder.onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPLACE)
                    .replaceWith(new byte[]{0});

            return encoder.encode(CharBuffer.wrap(res.getBody().toString())).array();
        } catch (CharacterCodingException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}