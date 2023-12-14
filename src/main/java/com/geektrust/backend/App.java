package com.geektrust.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.appConfig.ApplicationConfig;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.exceptions.NoSuchCommandException;
import com.geektrust.backend.globalConstants.Constants;

public class App {

	private final CommandInvoker commandInvoker;
	private final String inputFile;
	
	public App(CommandInvoker commandInvoker, String inputFile) {
		this.commandInvoker = commandInvoker;
		this.inputFile = inputFile;
	}

	public void run() {
		try{
			List<String> lines = Files.readAllLines(Paths.get(inputFile));
			for(String line : lines) {
				List<String> tokens = Arrays.asList(line.split(" "));
				commandInvoker.executeCommand(tokens.get(Constants.INPUT_FILE_ARG_INDEX), tokens);
			}
		} catch (IOException e) {
			System.err.println(Constants.COMMAND_NOT_FOUND_MESSAGE);
		} catch (NoSuchCommandException e) {
			System.err.println(Constants.COMMAND_NOT_FOUND_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if(args.length < Constants.INPUT_FILE_ARG) {
			throw new IllegalArgumentException("MISSING_INPUT_FILE_ARGUMENT");
		}
		ApplicationConfig applicationConfig = new ApplicationConfig();
		CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
		App app = new App(commandInvoker, args[Constants.INPUT_FILE_ARG_INDEX]);
		app.run();
	}

}
