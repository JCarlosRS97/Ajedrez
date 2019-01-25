package Utils;

import java.io.PrintWriter;
import java.util.List;

public class WebUtils {
	public static void printHeader(PrintWriter out, String serverName) {
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
		"<h1 align=\"center\">" + serverName + "</h1>\n" +
		"Here are the request line and request headers\n" +
		"sent by your browser:\n" +
		"<pre>");
	}
	public static boolean usingPost(List<String> inputLines) {
		return (inputLines.get(0).toUpperCase().startsWith("POST"));
	}
	public static void printTrailer(PrintWriter out) {
		out.println("</pre></body></html>\n");
	}
}
