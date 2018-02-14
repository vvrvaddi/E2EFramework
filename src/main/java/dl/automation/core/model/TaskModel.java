package dl.automation.core.model;

import java.util.ArrayList;
import java.util.List;


public class TaskModel {
    private String id="";
    private List<TaskStepModel> steps=new ArrayList<>();

    public TaskModel(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<TaskStepModel> getSteps() {
        return steps;
    }
}
