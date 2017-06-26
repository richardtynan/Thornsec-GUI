package core.exec;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class InputExec implements Runnable {

	private InputStreamReader reader;
	private OutputStream writer;
	private Boolean inputReceived;

	public InputExec(InputStream stream, OutputStream writer) {
		this.writer = writer;
		this.reader = new InputStreamReader(stream);
		this.inputReceived = false;
	}

	public void run() {
		try {
			int c = reader.read();
			synchronized (inputReceived) {
				this.inputReceived = true;
			}
			while (c != -1) {
				writer.write(c);
				c = reader.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isInputReceived() {
		synchronized (inputReceived) {
			return this.inputReceived;
		}
	}

}
