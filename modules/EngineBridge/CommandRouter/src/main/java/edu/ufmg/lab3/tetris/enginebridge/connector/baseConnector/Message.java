package edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector;

public class Message {
	private byte app = 0;
	private byte player = 0;
	private byte move = 0;
	private byte units = 0;
	public static final Message NULL_MESSAGE = new Message();

	public byte getApp() {
		return app;
	}

	public void setApp(byte app) {
		this.app = app;
	}

	public byte getPlayer() {
		return player;
	}

	public void setPlayer(byte player) {
		this.player = player;
	}

	public byte getMove() {
		return move;
	}

	public void setMove(byte move) {
		this.move = move;
	}

	public byte getUnits() {
		return units;
	}

	public void setUnits(byte units) {
		this.units = units;
	}

	@Override
	public String toString() {
		return String.format("%02x %02x %02x %02x", app, player, move, units);
	}
}
