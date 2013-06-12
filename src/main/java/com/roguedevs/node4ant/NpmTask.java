package com.roguedevs.node4ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.ExecTask;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Date: 5/14/13
 * Time: 5:04 PM
 */
public class NpmTask extends ExecTask {
    //confirm node is available
    protected boolean searchPath = true;

    public NpmTask() {
        super.setExecutable("npm");
        this.setResolveExecutable(true);
        this.setFailonerror(true);
    }

    @Override
    public void execute() throws BuildException {
        //run node command with arguments
        // Quick fail if this is not a valid OS for the command
        if (!isValidOs()) {
            return;
        }

        checkConfiguration();
        runExec(prepareExec());
    }

    @Override
    protected void checkConfiguration() throws BuildException {
        cmdl.setExecutable(resolveExecutable(this.cmdl.getExecutable(), searchPath));
        File executableFile = new File(cmdl.getExecutable());
        if (!executableFile.exists()){
                throw new BuildException("Could not find "+ this.cmdl.getExecutable() + " on the path " );
        }

        if (this.cmdl.getArguments().length < 1){
            throw new BuildException("No command line arguments sent to " + this.cmdl.getExecutable());
        }

        super.checkConfiguration();
    }

    public boolean isSearchPath() {
        return searchPath;
    }

    public void setSearchPath(boolean searchPath) {
        this.searchPath = searchPath;
        super.setSearchPath(searchPath);
    }
}
