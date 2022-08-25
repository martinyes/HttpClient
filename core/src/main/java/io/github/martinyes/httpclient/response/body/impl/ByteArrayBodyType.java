package io.github.martinyes.httpclient.response.body.impl;

import io.github.martinyes.httpclient.response.body.BodyType;
import io.github.martinyes.httpclient.response.WrappedHttpResponse;
import io.github.martinyes.httpclient.scheme.data.response.RawResponse;

import java.nio.CharBuffer;
import java.nio.charset.*;

/**
 * Byte array Body Handler.
 *
 * @author martin
 * @since 2
 * @version 2
 */
public final class ByteArrayBodyType implements BodyType<byte[]> {

    private Charset charset;

    public ByteArrayBodyType() { }

    public ByteArrayBodyType(Charset charset) {
        this.charset = charset;
    }

    @Override
    public byte[] apply(RawResponse res) {
        CharsetEncoder encoder = (charset == null ? StandardCharsets.UTF_8.newEncoder() : charset.newEncoder());

        try {
            encoder.onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPLACE)
                    .replaceWith(new byte[]{0});

            return encoder.encode(CharBuffer.wrap(res.message().toString())).array();
        } catch (CharacterCodingException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}