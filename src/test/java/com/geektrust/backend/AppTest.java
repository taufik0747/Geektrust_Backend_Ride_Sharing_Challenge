package com.geektrust.backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.nio.file.*;
import java.util.Arrays;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.exceptions.NoSuchCommandException;
import org.junit.jupiter.api.io.TempDir;

public class AppTest {

    @TempDir
    Path tempDir;

    @Test
    void testRun_SuccessfulCommandExecution() throws Exception {
        CommandInvoker mockInvoker = mock(CommandInvoker.class);
        Path inputFile = tempDir.resolve("testInput.txt");
        Files.write(inputFile, Arrays.asList("ADD_DRIVER driver1 1 1", "ADD_RIDER rider1 2 2"));

        App app = new App(mockInvoker, inputFile.toString());
        app.run();

        verify(mockInvoker).executeCommand("ADD_DRIVER", Arrays.asList("ADD_DRIVER", "driver1", "1", "1"));
        verify(mockInvoker).executeCommand("ADD_RIDER", Arrays.asList("ADD_RIDER", "rider1", "2", "2"));
    }
    

    @Test
void testRun_IOExceptionHandling() {
    CommandInvoker mockInvoker = mock(CommandInvoker.class);
    String nonExistentFile = "nonexistent.txt";

    App app = new App(mockInvoker, nonExistentFile);

    app.run();
    // Verify that the appropriate error message is printed
}

@Test
void testRun_NoSuchCommandExceptionHandling() throws Exception {
    CommandInvoker mockInvoker = mock(CommandInvoker.class);
    doThrow(new NoSuchCommandException()).when(mockInvoker).executeCommand(eq("INVALID_COMMAND"), anyList());

    Path inputFile = tempDir.resolve("testInput.txt");
    Files.write(inputFile, Arrays.asList("INVALID_COMMAND"));

    App app = new App(mockInvoker, inputFile.toString());

    app.run();
    // Verify that the appropriate error message is printed
}


@Test
void testMain_IllegalArgumentExceptionForMissingInputFile() {
    String[] args = {}; // Empty arguments

    assertThrows(IllegalArgumentException.class, () -> App.main(args));
}



}
