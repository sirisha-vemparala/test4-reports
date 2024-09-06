package com.qa.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GitUtils {

    public static void commitAndPush(String commitMessage, String workingDir) {
        try {
            // Add changes to staging
            String addResult = executeCommand("git add reports/", workingDir);
            System.out.println("Git add result: " + addResult);
            
            // Commit changes
            String commitResult = executeCommand("git commit -m \"" + commitMessage + "\"", workingDir);
            if (commitResult.contains("nothing to commit")) {
                System.out.println("No changes to commit");
                return;
            }
            System.out.println("Git commit result: " + commitResult);
            
            // Push changes to the remote repository
            String pushResult = executeCommand("git push origin master", workingDir);
            System.out.println("Git push result: " + pushResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String executeCommand(String command, String workingDir) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        processBuilder.directory(new java.io.File(workingDir));
        
        Process process = processBuilder.start();
        
        // Capture output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        
        StringBuilder output = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        
        StringBuilder errorOutput = new StringBuilder();
        while ((line = errorReader.readLine()) != null) {
            errorOutput.append(line).append("\n");
        }
        
        int exitCode = process.waitFor();
        
        // Log output and error
        if (exitCode != 0) {
            System.err.println("Error executing command: " + command);
            System.err.println("Exit code: " + exitCode);
            System.err.println(errorOutput.toString());
        }
        
        return output.toString();
    }
}
