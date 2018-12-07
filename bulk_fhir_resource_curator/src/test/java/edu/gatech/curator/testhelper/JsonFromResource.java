package edu.gatech.curator.testhelper;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonFromResource
{
    public static byte[] getBytes(Resource resource) throws IOException {
        Path exportOutputJsonPath = resource.getFile().toPath();
        return Files.readAllBytes(exportOutputJsonPath);
    }
}
