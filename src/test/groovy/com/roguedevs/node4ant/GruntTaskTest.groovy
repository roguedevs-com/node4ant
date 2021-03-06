package com.roguedevs.node4ant

import org.apache.tools.ant.BuildException
import org.apache.tools.ant.BuildFileTest
import org.apache.tools.ant.util.FileUtils
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * Date: 5/14/13
 * Time: 5:22 PM
 */
class GruntTaskTest extends BuildFileTest {

    final shouldFail = new GroovyTestCase().&shouldFail

    protected StringBuffer logBuffer;
    protected static final String BUILD_PATH = "src/test/resources/testcases/taskdefs/nodetask/";
    protected static final String BUILD_FILE = BUILD_PATH + "node.xml";
    protected static final int TIME_TO_WAIT = 1;
    /** maximum time allowed for the build in milliseconds */
    protected static final int MAX_BUILD_TIME = 4000;
    protected static final int SECURITY_MARGIN = 2000; // wait 2 second extras
    // the test failed with 100 ms of margin on cvs.apache.org on August 1st,
    // 2003

    /** Utilities used for file operations */
    protected static final FileUtils FILE_UTILS = FileUtils.getFileUtils();

    protected File logFile;

    public GruntTaskTest(String name) {
        super(name);
    }

    public void setUp() {
        configureProject(BUILD_FILE);
    }

    public void tearDown() {
        if (logFile != null && logFile.exists()) {
            getProject().setProperty("logFile", logFile.getAbsolutePath());
        }
        //executeTarget("cleanup");
    }

    /**
     * Sets up to run the named project
     *
     * @param  filename name of project file to run
     */
    public void configureProject(String filename)
    throws BuildException {
        logBuffer = new StringBuffer();
        fullLogBuffer = new StringBuffer();
        super.configureProject(filename)
        //project = new Project();
        //project.init();
        //File antFile = new File(System.getProperty("root"), filename);
        //File antFile = new File(filename);
        //project.setUserProperty("ant.file" , antFile.getAbsolutePath());
        //project.addBuildListener(new AntTestListener(logLevel));
        //ProjectHelper.configureProject(project, antFile);
    }

    @Test
    void testConstructor(){
        GruntTask gruntTask = new GruntTask()
        assert gruntTask.cmdl.getExecutable() == 'grunt'
    }

    @Test
    void testExecNotFound(){
        GruntTask gruntTask = new GruntTask()
        gruntTask.project = this.project
        gruntTask.searchPath = false
        gruntTask.executable = '/not/path/to/grunt'
        String message = shouldFail(BuildException){
            gruntTask.execute()
        }
        assertTrue message.startsWith("Could not find ")
    }

    @Test
    void testExecPass(){
        GruntTask gruntTask = new GruntTask()
        gruntTask.setExecutable("/usr/local/share/npm/bin/grunt")
        gruntTask.setProject(this.project)
        gruntTask.createArg().setValue("--version")
        gruntTask.execute()
        assert logBuffer.toString().startsWith("g")
    }

    @Test
    void testExecInvalidCommand(){
        GruntTask gruntTask = new GruntTask()
        gruntTask.setProject(this.project)
        gruntTask.createArg().setValue("blah blah blah")
        String message = shouldFail(BuildException){
            gruntTask.execute();
        }
        assert message
    }

    @Test
    void testExecNoArguments(){
        GruntTask gruntTask = new GruntTask()
        gruntTask.setProject(this.project)
        String message = shouldFail(BuildException){
            gruntTask.execute();
        }
        assert message
    }
}
