package com.atarx.rs2.entity.player.net.out.impl;

import com.atarx.core.network.StreamBuffer;
import com.atarx.core.network.StreamBuffer.OutBuffer;
import com.atarx.rs2.entity.player.net.Client;
import com.atarx.rs2.entity.player.net.out.OutgoingPacket;

public class SendEnterString extends OutgoingPacket {

	@Override
	public void execute(Client client) {
		OutBuffer outBuffer = StreamBuffer.newOutBuffer(5);
		outBuffer.writeHeader(client.getEncryptor(), getOpcode());
		client.send(outBuffer.getBuffer());
	}

	@Override
	public int getOpcode() {
		return 187;
	}

}
