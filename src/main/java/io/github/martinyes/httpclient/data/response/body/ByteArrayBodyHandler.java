package io.github.martinyes.httpclient.data.response.body;

import io.github.martinyes.httpclient.data.response.BodyHandler;
import io.github.martinyes.httpclient.data.response.impl.WrappedHttpResponse;

import java.nio.CharBuffer;
import java.nio.charset.*;

/**
 * Byte array Body Handler.
 *
 * @author martin
 * @since 2
 */
public final class ByteArrayBodyHandler implements BodyHandler<byte[]> {

    private Charset charset;

    public ByteArrayBodyHandler() {

    }

    public ByteArrayBodyHandler(Charset charset) {
        this.charset = charset;
    }

    @Override
    public byte[] apply(WrappedHttpResponse res) {
        CharsetEncoder encoder = (charset == null ? StandardCharsets.UTF_8.newEncoder() : charset.newEncoder());

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