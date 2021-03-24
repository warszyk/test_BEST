package pojo;

import java.util.List;

public class StrategyDroll
{
    private List<Integer> clientIds;

    private int actionDefinitionId;

    private int segmentationId;

    public void setClientIds(List<Integer> clientIds){
        this.clientIds = clientIds;
    }
    public List<Integer> getClientIds(){
        return this.clientIds;
    }
    public void setActionDefinitionId(int actionDefinitionId){
        this.actionDefinitionId = actionDefinitionId;
    }
    public int getActionDefinitionId(){
        return this.actionDefinitionId;
    }
    public void setSegmentationId(int segmentationId){
        this.segmentationId = segmentationId;
    }
    public int getSegmentationId(){
        return this.segmentationId;
    }
}

