package template.other;

/**
 * Created by cloud on 2019/12/19.
 */
public class AutoTestPM implements PM {
    int testTimes = 0;

    @Override
    public void analyze() {
        System.out.println("analyze");
    }

    @Override
    public void design() {
        System.out.println("design");
    }

    @Override
    public void develop() {
        System.out.println("developed");
    }

    @Override
    public boolean test() {
        System.out.println("test");
        testTimes++;
        boolean testPass = this.testTimes==5;
        if(!testPass){
            System.out.println("test not pass, continue develop");
        }else{
            System.out.println("test pass");
        }
        return testPass;
    }

    @Override
    public void release() {
        System.out.println("release");
    }

    public static void main(String[] args) {
        AutoTestPM autoTestPM  =  new AutoTestPM();
        autoTestPM.kickoff();
    }




}
