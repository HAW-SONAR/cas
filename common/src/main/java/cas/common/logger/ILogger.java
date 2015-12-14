package cas.common.logger;

public interface ILogger {
	/**
	 * Add a Listener to the Logger
	 * If a registered Class is disposed on runtime, it has to be unregistred by using removeMessageReceiver!
	 * @param messageReceiver
	 */
	public void addMessageReceiver(IMessageReceiver messageReceiver);
	/**
	 * Removes a Listener from the Logger
	 * @param messageReceiver
	 */
	public void removeMessageReceiver(IMessageReceiver messageReceiver);
	/**
	 * Send a the Message to all registered Listeners and print it to the system.out
	 * @param message
	 */
	public void log(String message);
}
