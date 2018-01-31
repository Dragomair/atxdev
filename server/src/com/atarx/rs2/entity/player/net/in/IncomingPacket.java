package com.atarx.rs2.entity.player.net.in;

import com.atarx.core.network.StreamBuffer;
import com.atarx.rs2.entity.player.Player;

public abstract class IncomingPacket {

	public abstract int getMaxDuplicates();

	public abstract void handle(Player paramPlayer, StreamBuffer.InBuffer paramInBuffer, int paramInt1, int paramInt2);
}