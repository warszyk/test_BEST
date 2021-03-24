package apiTC;

import com.aventstack.extentreports.Status;
import logging.ExtentManager;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.*;
import pojo.StrategyDroll;
import sql.SqlFactory;
import sql.StrategyProcessDbo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TC_1_1_Test extends TestBaseAPI{

    private SqlFactory SQL;

    @BeforeTest
    public void setUpTestReporter(){
        testLogger = ExtentManager.startTest(extentReports, "TC.1.1 – Uruchomienie procesu wysyłki dokumentów (Działanie strategiczne Dokument)");
    }

    @Parameters({"dbo_user", "dbo_password"})
    @BeforeTest
    public void setUpConfiguration(String dbo_user, String dbo_password) {
        SQL = new SqlFactory();
        SQL.connectToDataBase(dbo_user, dbo_password);
    }

    @Parameters({"sessionId", "requestId", "userId", "clientsId", "actionDefinitionId", "segmentationId"})
    @Test
    public void startDocumentationSendingProcess(String sessionId, String requestId, String userId,
                                                 String clientsId, String actionDefinitionId, String segmentationId) {

        String[] clientsIds = clientsId.split(",");
        List<Integer> clientsIdsList = new ArrayList<>();
        for (String id : clientsIds) {
            clientsIdsList.add(Integer.parseInt(id));
        }

        StrategyDroll strategyDroll = new StrategyDroll();
        strategyDroll.setClientIds(clientsIdsList);
        strategyDroll.setActionDefinitionId(Integer.parseInt(actionDefinitionId));
        strategyDroll.setSegmentationId(Integer.parseInt(segmentationId));

        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("sessionId", sessionId);
        headersMap.put("requestId", requestId);
        headersMap.put("userId", userId);

        testLogger.log(Status.INFO,"Making POST request to create 'strategyDroolsResult'");
        given().headers(headersMap).body(strategyDroll).contentType("application/json")
                .when().post("technical/strategyDroolsResult/create")
                .then().statusCode(200);
        testLogger.log(Status.PASS,"Request 'strategyDroolsResult' passed.");

        testLogger.log(Status.INFO,"Making GET request to get 'Document?'");
        given().headers(headersMap)
                .when().get("rest/strategyAction/DOCUMENT/10000/execution")
                .then().statusCode(200);
        testLogger.log(Status.PASS,"Request 'Document' passed.");

        testLogger.log(Status.INFO,"Assertions for correct statuses in in Document and StrategyDroolsResult tables.");
        String symbol = "Document";
        StrategyProcessDbo strategyProcessDbo = new StrategyProcessDbo();
        Assert.assertTrue(strategyProcessDbo.waitUntilAllStatusesProcessed(segmentationId, symbol), "Timeout occurred while waiting for all statuses being processed.");
        Assert.assertTrue(strategyProcessDbo.isAllStatusesInProgressionInStrategyDrollsTable(segmentationId, symbol), "Not all statuses are 'in progression' state StrategyDrolls table.");
        Assert.assertTrue(strategyProcessDbo.isDocumentAndStrategyProcessTablesHaveSameNrOfRecords(segmentationId, symbol),
                "Number of records in tables: StrategyDroolsResult and Document are not equal.");
        Assert.assertTrue(strategyProcessDbo.isAllStatusesInProgressionInDocumentTable(segmentationId, symbol), "Not all statuses are 'in progression' state in Document table.");
        testLogger.log(Status.PASS,"All statuses in Document and StrategyDroolsResult are as expected.");
    }

    @AfterTest
    public void closeSqlConnection() {
        SQL.disconnect();
    }
}