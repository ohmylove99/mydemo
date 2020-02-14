package org.octopus.api.ut;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AppServiceUT.class, //test case 1
    AppControllerUT.class     //test case 2
})
public class AppUT {

}
