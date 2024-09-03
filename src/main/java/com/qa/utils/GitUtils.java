package com.qa.utils;

public class GitUtils {

    public static void commitAndPush(String commitMessage) {
        try {
            // Add changes to staging
            executeCommand("git add .");
            
            // Commit changes
            executeCommand("git commit -m \"" + commitMessage + "\"");
            
            // Push changes to the remote repository
            executeCommand("git push origin master");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeCommand(String command) throws Exception {
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }
}
