package my.vaadin.app;

import my.vaadin.app.WorkflowRunService;
import my.vaadin.app.WorkflowRun;
import my.vaadin.app.WorkflowRunStatus;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Image;

import java.util.List;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.ProgressBar;

/**
 * This UI is the application entry point. A UI may either represent a browser window (or tab) or
 * some part of a html page where a Vaadin application is embedded.
 * <p>
**/

@Theme("mytheme")
public class MyUI extends UI {

  private WorkflowRunService service = WorkflowRunService.getInstance();

  //private Grid<WorkflowRun> grid = new Grid<> (WorkflowRun.class);
  @Override
  protected void init(VaadinRequest vaadinRequest) {

    //Create Basic Layout
    final VerticalLayout layout = new VerticalLayout();

    //Get Data from Service Class

    List<WorkflowRun> WorkflowRuns = service.findAll();

    //Generate Grid

    createGrid(layout, WorkflowRuns);

    // Add Component below Grid
    AddComponent(layout);

  }

  private Grid createGrid(VerticalLayout layout, List<WorkflowRun> workflowruns) {
    // Generate Basic Grid
    Grid<WorkflowRun> grid = new Grid<>();
    grid.setItems(workflowruns);
    layout.addComponents(grid);
    setContent(layout);

    grid.setSelectionMode(Grid.SelectionMode.MULTI);
    grid.setWidth(100, Unit.PERCENTAGE);
    WorkflowRun testrun = new WorkflowRun();

    // Add Elements to Grid
    grid.addColumn(WorkflowRun::getRunName).setCaption("Run Name").setId("Run Name");
    grid.addColumn(WorkflowRun::getCurrentRunStatus).setCaption("Status").setId("Status");
    grid.addColumn(WorkflowRun::getStartTime).setCaption("Start Time").setId("Start Time");

        /*
        Grid.Column testcolumn = grid.getColumn("Start Time");
        testcolumn.setRenderer(new DateRenderer("%1$tB %1$te, %1$tY",
            Locale.ENGLISH));*/

    grid.addColumn(w ->

    {Image image = new Image("", new ThemeResource("img/" + w.getCurrentRunStatus() + ".png"));

      image.setWidth("100px");
      image.setHeight("100px");

      return image;

    }).setCaption("StatusIcon");

    /*grid.getColumn("StatusIcon").setRenderer(new ImageRenderer());*/
    grid.addColumn(WorkflowRun::getCompletedCount).setCaption("Completed Count")
        .setId("Completed Count");
    grid.addColumn(WorkflowRun::getRunningCount).setCaption("Running Count").setId("Running Count");
    grid.addColumn(WorkflowRun::getSubmittedCount).setCaption("Submitted Count")
        .setId("Submitted Count");
    grid.addColumn(WorkflowRun::getFailedCount).setCaption("Failed Count").setId("Failed Count");
       /* switch (WorkflowRun::getCurrentRunStatus){
            case "Running": break;
            case "Queued": break;
            case "Completed": break;
            case "Error": break;
        }
*/
    return grid;
  }

  public void AddComponent(VerticalLayout layout) {
    final ProgressBar bar = new ProgressBar(0.0f);
    layout.addComponent(bar);
    layout.addComponent(new Button("Increase",
        new ClickListener() {
          @Override
          public void buttonClick(ClickEvent event) {
            float current = bar.getValue();
              if (current < 1.0f) {
                  bar.setValue(current + 0.10f);
              }
          }
        }));
  }

                /*
                 for (int i = 0; i< workflowruns.size(); i++)
              {
                 WorkflowRun currentWorkflowRun = workflowruns.get(i);
                 Field[] fields = currentWorkflowRun.getClass().getDeclaredFields();
                 for (int j = 0; j < fields.length; j++) {
                Field currentfield = fields[j];
                try {
                    addColumn(grid, currentWorkflowRun, currentfield);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                }
                }  */


/*
    private  Grid<WorkflowRun> addColumn(Grid<WorkflowRun> grid, WorkflowRun workflowrun, Field field)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String fieldName = field.getName();
        Method fieldgetter  = WorkflowRun.class.getDeclaredMethod("get" + fieldName);
        Object methodresult = fieldgetter.invoke(workflowrun);
        //grid.setItems(workflowruns);
        grid.addColumn(String.valueOf(methodresult)).setCaption(fieldName);
        return grid;
    }
*/

  @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
  @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
  public static class MyUIServlet extends VaadinServlet {

  }
}
