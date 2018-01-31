package com.atarx.rs2.entity.player.net.out.impl;

import com.atarx.rs2.entity.player.net.Client;
import com.atarx.rs2.entity.player.net.out.OutgoingPacket;

public class SendCharacterDetail extends OutgoingPacket {

	@Override
	public void execute(Client client) {
	//String name = client.getPlayer().getUsername();

	}

	@Override
	public int getOpcode() {
	return 36;
	}

}
