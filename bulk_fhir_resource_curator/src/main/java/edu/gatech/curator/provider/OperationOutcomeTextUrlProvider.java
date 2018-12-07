package edu.gatech.curator.provider;

import okhttp3.HttpUrl;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class OperationOutcomeTextUrlProvider {
    public HttpUrl parse(String divString) throws MalformedURLException {
        Pattern pattern = Pattern.compile("(?<=&quot;)(.*)(?=&quot;)");

        Matcher m = pattern.matcher(divString);

        if (m.find()) {
            String urlString = m.group(0);
            return HttpUrl.parse(urlString);
        }

        throw new MalformedURLException("");
    }
}
