package me.manzhos.tests;

import me.manzhos.DataProviders.ComputerCreationDataProvider;
import me.manzhos.base.TestBase;
import me.manzhos.pages.AddComputerPage;
import me.manzhos.pages.AllComputersPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by omanzhos on 2/28/2019.
 */
public class AddingComputerHappyPathTest extends TestBase {

    private Logger log = LoggerFactory.getLogger(AddingComputerHappyPathTest.class);
    private AddComputerPage addComputerPage = new AddComputerPage();
    private AllComputersPage allComputersPage = new AllComputersPage();

    @Test(dataProvider = "addComputer", dataProviderClass = ComputerCreationDataProvider.class)
    public void addComputerWithFieldsFromDataProviderTest(String computerName, String introductionDate, String discontinuedDate,
                                            String company, String expectedComputerName, String expectedIntroductionDate,
                                            String expectedDiscontinuedDate, String expectedCompanyName){
        log.info("---- Test" + getClass().getName() +" is started----");
        allComputersPage.initializeComputerCreation();
        addComputerPage.populateNewComputerFields(computerName, introductionDate, discontinuedDate, company);
        addComputerPage.confirmComputerCreation();
        Assert.assertEquals(allComputersPage.getNotificationMessage(), "Done! Computer " + computerName + " has been created", "Another computer name was displayed");

        allComputersPage.searchComputerByName(computerName);
        Assert.assertEquals(allComputersPage.getComputerNameFromSearchResult(), expectedComputerName, "Another computer name was returned");
        Assert.assertEquals(allComputersPage.getIntroducedDateFromSearchResults(), expectedIntroductionDate, "Another introduced date was returned");
        Assert.assertEquals(allComputersPage.getDiscontinuedDateFromSearchResults(), expectedDiscontinuedDate, "Another discontinued date was returned");
        Assert.assertEquals(allComputersPage.getCompanyNameFromSearchResults(), expectedCompanyName, "Another company name is returned");
        log.info("--------");
    }

    @Test
    public void checkCancelButtonDoesntCreateNewRecordTest() {
        log.info("---- Test" + getClass().getName() +" is started----");
        String computerName = "Another new Computer%";
        allComputersPage.initializeComputerCreation();
        addComputerPage.populateNewComputerFields(computerName,"", "", "");
        addComputerPage.cancelComputerCreation();

        allComputersPage.searchComputerByName(computerName);
        Assert.assertTrue(allComputersPage.isZeroResult());
        log.info("--------");
    }

}
