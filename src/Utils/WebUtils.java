package Utils;

import java.io.PrintWriter;

public class WebUtils {
	public static void printConfirmPage(PrintWriter out, String serverName) {
		out.println
		("HTTP/1.1 200 OK\r\n" +
		"Server: " + serverName + "\r\n" +
		"Content-Type: text/html\r\n" +
		"\r\n" +
		"<!DOCTYPE html>\n" +
		"<html lang=\"en\">\n" +
		"<head>\n" +
		" <meta charset=\"utf-8\"/>\n" +
		" <title>" + serverName + "</title>\n" +
		"</head>\n" +
		"\n" +
		"<body bgcolor=\"#fdf5e6\">\n" +
		"<h1 align=\"center\">Gracias por registrarte.</h1>\n" +
		"</body></html>\n");
	}

    public static void printPage(PrintWriter out, String serverName, String page) {
        out.println
                (new StringBuilder().append("HTTP/1.1 200 OK\r\n").append("Server: ").
                        append(serverName).append("\r\n").append("Content-Type: text/html\r\n")
                        .append("\r\n").append(page).toString());
    }

	public static boolean usingPost(String inputLines) {
		return inputLines.toUpperCase().startsWith("POST");
	}
}
