package core.exec;

import java.io.OutputStream;

public class ManageExec {

	private String pass;
	private String cmd;
	private String connect;
	private String port;
	private String user;
	private String ip;
	private OutputStream out;

	public ManageExec(String connect, String port, String user, String ip, String pass, String cmd, OutputStream out) {
		this.connect = connect;
		this.port = port;
		this.user = user;
		this.ip = ip;
		this.pass = pass;
		this.cmd = cmd;
		this.out = out;
	}

	public ProcessExec runNonBlock() {
		if (connect.equals("direct")) {

			String connectionString = "ssh -o ConnectTimeout=5 -p " + port + " " + user + "@" + ip
					+ " cat > script.sh; chmod +x script.sh;";
			String executionString = "ssh -t -t -o ConnectTimeout=5 -p " + port + " " + user + "@" + ip
					+ " ./script.sh; rm -rf script.sh; exit;";

			System.out.println(connectionString);
			System.out.println(executionString);
			
			ProcessExec exec1 = new ProcessExec(connectionString, out, System.err);
			exec1.exec();
			exec1.writeAllClose(this.cmd.getBytes());
			exec1.waitFor();
			ProcessExec exec2 = new ProcessExec(executionString, out, System.err);
			exec2.exec();
			while (!exec2.isInputReceived()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			exec2.writeAllOpen(pass.getBytes());
			return exec2;
		} else {
			return null;
		}
	}

	public void runBlock() {
		ProcessExec exec = runNonBlock();
		exec.waitFor();
	}

}