package service;

public interface ChatAnonima {
	/**
	 * Creates new room.
	 * @param _room_name a String the name identify the public chat room.
	 * @return true if the room is correctly created, false otherwise.
	 */
	public boolean createRoom(String _room_name, String password);
	/**
	 * Joins in a public room.
	 * @param _room_name the name identify the public chat room.
	 * @return true if join success, false otherwise.
	 */
	public boolean joinRoom(String _room_name, String password);
	/**
	 * Leaves in a public room.
	 * @param _room_name the name identify the public chat room.
	 * @return true if leave success, false otherwise.
	 */
	public boolean leaveRoom(String _room_name, String password);
	/**
	 * Sends a string message to all members of a  a public room.
	 * @param _room_name the name identify the public chat room.
	 * @param _text_message a message String value.
	 * @return true if send success, false otherwise.
	 */
	public boolean sendMessage(String _room_name, String _text_message);
}