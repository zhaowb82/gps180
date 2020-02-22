
package com.gps.gate.protocol;

import com.gps.gate.BaseProtocol;
import com.gps.gate.PipelineBuilder;
import com.gps.gate.TrackerServer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class TestProtocol extends BaseProtocol {

    public TestProtocol() {
        port = 6868;
        addServer(new TrackerServer(false, getName(), getPort()) {
            @Override
            protected void addProtocolHandlers(PipelineBuilder pipeline) {
                pipeline.addLast(new HttpResponseEncoder());
                pipeline.addLast(new HttpRequestDecoder());
                pipeline.addLast(new HttpObjectAggregator(16384));
                pipeline.addLast(new TestProtocolDecoder(TestProtocol.this));
            }
        });
    }

}
