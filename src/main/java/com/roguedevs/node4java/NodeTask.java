package com.roguedevs.node4java;

import org.apache.tools.ant.BuildException;

import org.apache.tools.ant.taskdefs.ExecTask;
import org.apache.tools.ant.types.Commandline;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Date: 5/14/13
 * Time: 5:04 PM
 */
public class NodeTask extends ExecTask {

//    protected String _executable = "";
//    protected String cmdArgs = "";
//    protected boolean haveCmdArgs = false;
    protected boolean searchPath = true;

    public NodeTask() {
        super.setExecutable("node");
        this.setResolveExecutable(true);
        this.setFailonerror(true);
    }

//    /**
//     * Set the name of the executable program.
//     * @param value the name of the executable program.
//     */
//    public void setExecutable(String value) {
//        super.setExecutable(value);
//        this._executable = value;
//    }

//    /**
//     * Set the arguments for the command.
//     *
//     * @param _cmdArgs
//     */
//    public void setCmdArgs(String _cmdArgs) {
//        createArg().setValue(_cmdArgs);
//        this.haveCmdArgs = true;
//        this.cmdArgs = _cmdArgs;
//    }

    @Override
    public void execute() throws BuildException {
        //run node command with arguments
        // Quick fail if this is not a valid OS for the command
        if (!isValidOs()) {
            return;
        }
        //cmdl.setExecutable(resolveExecutable(this.cmdl.getExecutable(), searchPath));
        //Commandline.Argument cmdlArgs = new Commandline.Argument();
        //cmdlArgs.setLine(cmdArgs);
        checkConfiguration();
        runExec(prepareExec());
    }

    @Override
    protected void checkConfiguration() throws BuildException {
    //protected void checkConfiguratin(){
        cmdl.setExecutable(resolveExecutable(this.cmdl.getExecutable(), searchPath));
        //cmdl.setExecutable(resolveExecutable(this._executable, searchPath));
        //checkConfiguration();
        File executableFile = new File(cmdl.getExecutable());
        if (!executableFile.exists()){
            throw new BuildException("Could not find "+ this.cmdl.getExecutable() + " on the path");
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
