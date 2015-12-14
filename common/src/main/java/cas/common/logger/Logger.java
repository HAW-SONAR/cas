package cas.common.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Logger implements ILogger{
	private final Set<IMessageReceiver> messageReceivers;
	private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public Logger() {
		messageReceivers = new HashSet<>();
	}
	@Override
	public void addMessageReceiver(final IMessageReceiver messageReceiver) {
		messageReceivers.add(messageReceiver);
	}
	@Override
	public void removeMessageReceiver(final IMessageReceiver messageReceiver) {
		messageReceivers.remove(messageReceiver);
	}
	@Override
	synchronized public void log(final String message) {
		final Date now = new Date();
		final String outMessage = formatter.format(now) +": " + message;
		System.out.println(outMessage);
		for(final IMessageReceiver messageReceiver: messageReceivers){
			messageReceiver.addMessage(outMessage);
		}
	}


}
