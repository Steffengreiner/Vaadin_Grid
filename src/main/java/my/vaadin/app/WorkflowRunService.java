package my.vaadin.app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An in memory dummy "database" for the example purposes. In a typical Java app
 * this class would be replaced by e.g. EJB or a Spring based service class.
 * <p>
 * In demos/tutorials/examples, get a reference to this service class with
 * {@link WorkflowRunService#getInstance()}.
 */
public class WorkflowRunService {

  private static WorkflowRunService instance;
  private static final Logger LOGGER = Logger.getLogger(WorkflowRunService.class.getName());

  private final HashMap<String, WorkflowRun> workflowruns = new HashMap<>();
  private long nextId = 0;

  private WorkflowRunService() {
  }

  /**
   * @return a reference to an example facade for WorkflowRun objects.
   */
  public static WorkflowRunService getInstance() {
    if (instance == null) {
      instance = new WorkflowRunService();
      instance.ensureTestData();
    }
    return instance;
  }

  /**
   * @return all available WorkflowRun objects.
   */
  public synchronized List<WorkflowRun> findAll() {
    return findAll(null);
  }

  public synchronized List<WorkflowRun> findAll(String stringFilter) {
    ArrayList<WorkflowRun> arrayList = new ArrayList<>();
    for (WorkflowRun workflowruns : workflowruns.values()) {
      try {
        boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
            || workflowruns.toString().toLowerCase().contains(stringFilter.toLowerCase());
        if (passesFilter) {
          arrayList.add(workflowruns);
        }
      } catch (RuntimeException rn) {
        Logger.getLogger(WorkflowRunService.class.getName()).log(Level.SEVERE, null, rn);
      }
    }
    return arrayList;
  }

  /**
   * @return the amount of all WorkflowRun in the system
   */
  public synchronized long count() {
    return workflowruns.size();
  }

  /**
   * Deletes a WorkflowRun from a system
   *
   * @param value
   *            the WorkflowRun to be deleted
   */
  public synchronized void delete(WorkflowRun value) {
    workflowruns.remove(value.getRunName());
  }

  /**
   * Persists or updates WorkflowRun in the system. Also assigns an identifier
   * for new WorkflowRun instances.
   *
   * @param entry
   */
  public synchronized void save(WorkflowRun entry) {
    if (entry == null) {
      LOGGER.log(Level.SEVERE,
          "WorkflowRun is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
      return;
    }
    if (entry.getRunName() == null) {
      entry.setRunName("Test");
    }
    workflowruns.put(entry.getRunName(), entry);
  }

  /**
   * Sample data generation
   */
  public void ensureTestData() {
    if (findAll().isEmpty()) {
      final String[] names = new String[] { "Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
          "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
          "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
          "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
          "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
          "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
          "Jaydan Jackson", "Bernard Nilsen" };
      Random ra = new Random(0);
      for (String name : names) {
        WorkflowRun r = new WorkflowRun();
        r.setRunName(name);
        r.setCompletedCount(10);
        r.setFailedCount(11);
        r.setRunningCount(3);
        r.setSubmittedCount(12);
        r.setRunningTaskName("TemplateTask");
        r.setStartTime(LocalDateTime.now());
        r.setCurrentRunStatus(WorkflowRunStatus.values()[ra.nextInt(WorkflowRunStatus.values().length)]);
        save(r);
      }
    }
  }

}
