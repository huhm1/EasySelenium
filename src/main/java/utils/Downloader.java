package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.openqa.selenium.Cookie;

public class Downloader {
	public static void download(String URL, Set<Cookie> cookies, String filePath) {

		File file = new File(filePath);
		HttpResponse response = null;
		CookieStore cookieStore = Cookies.copyCookies(cookies);

		HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore)
				.setRedirectStrategy(new LaxRedirectStrategy()).build();

		HttpGet request = new HttpGet(URL);

		try {
			file = client.execute(request, new downloadResponseHandler(file));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static class downloadResponseHandler implements ResponseHandler<File> {

		private final File file;

		public downloadResponseHandler(File file) {
			this.file = file;
		}

		@Override
		public File handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			InputStream source = response.getEntity().getContent();
			FileUtils.copyInputStreamToFile(source, this.file);
			return this.file;
		}

	}
}
