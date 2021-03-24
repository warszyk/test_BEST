package sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StrategyProcessDbo extends SqlFactory{

    private String getQuerySelectStatusFromStrategyProcessTable(Status status, boolean statusEqual, String segmentationId, String symbol) {
        String selectQuery = "SELECT sdr.status FROM StrategyProcess.dbo.StrategyDroolsResult sdr (NOLOCK)" +
                "JOIN StrategyProcess.dbo.ActionType at (NOLOCK) ON at.id = sdr.actionTypeId" +
                "WHERE sdr.status " + (statusEqual ? "=" : "<>") + " '%1$s'" +
                "AND sdr.segmentationId = %2$s" +
                "AND at.symbol = '%3$s'";

        return String.format(
                selectQuery
                , status.getDescription()
                , segmentationId
                , symbol);
    }

    private ResultSet selectNotForRealizationStatuses(String segmentationId, String symbol) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();

            String query = getQuerySelectStatusFromStrategyProcessTable(Status.FOR_REALIZATION, false, segmentationId, symbol);
            System.out.println("Execution of query: " + query);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public boolean waitUntilAllStatusesProcessed(String segmentationId, String symbol) {
        long end = System.currentTimeMillis() + (timeoutInMinutesForQueries * 1000L);

        while (System.currentTimeMillis() < end) {
            try {
                ResultSet resultSet = selectNotForRealizationStatuses(segmentationId, symbol);
                if (!resultSet.next()) {
                    return true;
                }

                Thread.sleep(timeoutInMinutesForQueries / 100);
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isAllStatusesInProgressionInStrategyDrollsTable(String segmentationId, String symbol) {
        ResultSet resultSet = selectNotForRealizationStatuses(segmentationId, symbol);
        try {
            while (resultSet.next()) {
                if (!resultSet.getString("status").equals(Status.IN_PROGRESS.getDescription())) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    private String getQueryStatusesFromStrategyProcessAndDocumentTables(Status status, boolean statusEqual, String segmentationId, String symbol) {
        String selectQuery = "SELECT sdr.status, d.status FROM StrategyProcess.dbo.StrategyDroolsResult sdr (NOLOCK)" +
                "JOIN StrategyProcess.dbo.ActionType at (NOLOCK) ON at.id = sdr.actionTypeId" +
                "JOIN StrategyProcess.dbo.Document d (NOLOCK) ON d.strategyRecordId = sdr.id" +
                "WHERE sdr.status " + (statusEqual ? "=" : "<>") + " '%1$s'" +
                "AND sdr.segmentationId = %2$s" +
                "AND at.symbol = '%3$s'";

        return String.format(
                selectQuery
                , status.getDescription()
                , segmentationId
                , symbol);
    }

    private ResultSet selectInProgressionStatuses(String segmentationId, String symbol) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            String query = getQueryStatusesFromStrategyProcessAndDocumentTables(Status.IN_PROGRESS, true, segmentationId, symbol);
            System.out.println("Execution of query: " + query);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public boolean isDocumentAndStrategyProcessTablesHaveSameNrOfRecords(String segmentationId, String symbol){
        ResultSet resultSet = selectInProgressionStatuses(segmentationId, symbol);
        try {
            while (resultSet.next()) {
                if (resultSet.getString(0).isEmpty() || resultSet.getString(1).isEmpty()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isAllStatusesInProgressionInDocumentTable(String segmentationId, String symbol){
        ResultSet resultSet = selectInProgressionStatuses(segmentationId, symbol);
        try {
            while (resultSet.next()) {
                if (!resultSet.getString(1).equals(Status.IN_PROGRESS.getDescription())) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
