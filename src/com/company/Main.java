package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();
			User u = new User(login, User.Status.AVAILABLE);
			int resUser = u.send(Utils.getURL() + "/add");
			if (resUser != 200) { // 200 OK
				System.out.println("HTTP error occured: " + resUser);
				return;
			}

			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();

            System.out.println("Enter your message or @command: ");
			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()) break;
				int res;

				if (text.charAt(0)=='@'){
					res = Command.send(text, login);
				} else {

					Message m = new Message(login, text);
					res = m.send(Utils.getURL() + "/add");
				}

				if (res != 200) { // 200 OK
					System.out.println("HTTP error occured: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
