package com.qa.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GitUtils {

    public static void commitAndPush(String commitMessage) {
        try {
            // Add changes to staging
            executeCommand("git add reports/");
            
            // Commit changes
            String commitResult = executeCommand("git commit -m \"" + commitMessage + "\"");
            if (commitResult.contains("nothing to commit")) {
                System.out.println("No changes to commit");
                return;
            }
            
            // Push changes to the remote repository
            executeCommand("git push origin master");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String executeCommand(String command) throws Exception {
        Process process = Runtime.getRuntime().exec(command);
        
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
        
        process.waitFor();
        
        // Log output and error
        if (errorOutput.length() > 0) {
            System.err.println("Error executing command: " + command);
            System.err.println(errorOutput.toString());
        }
        
        return output.toString();
    }
}
