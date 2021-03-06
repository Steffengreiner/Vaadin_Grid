package my.vaadin.app;

import java.time.LocalDateTime;
import java.util.Date;

public class WorkflowRun {
    String runName;
    String runningTaskName;
    LocalDateTime startTime;
    WorkflowRunStatus currentRunStatus;
    int submittedCount;
    int runningCount;
    int completedCount;
    int failedCount;

  public String getRunName() {
    return runName;
  }

  public void setRunName(String runName) {
    this.runName = runName;
  }

  public String getRunningTaskName() {
    return runningTaskName;
  }

  public void setRunningTaskName(String runningTaskName) {
    this.runningTaskName = runningTaskName;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public WorkflowRunStatus getCurrentRunStatus() {
    return currentRunStatus;
  }

  public void setCurrentRunStatus(WorkflowRunStatus currentRunStatus) {
    this.currentRunStatus = currentRunStatus;
  }

  public int getSubmittedCount() {
    return submittedCount;
  }

  public void setSubmittedCount(int submittedCount) {
    this.submittedCount = submittedCount;
  }

  public int getRunningCount() {
    return runningCount;
  }

  public void setRunningCount(int runningCount) {
    this.runningCount = runningCount;
  }

  public int getCompletedCount() {
    return completedCount;
  }

  public void setCompletedCount(int completedCount) {
    this.completedCount = completedCount;
  }

  public int getFailedCount() {
    return failedCount;
  }

  public void setFailedCount(int failedCount) {
    this.failedCount = failedCount;
  }

}
