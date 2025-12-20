import java.time.LocalDate;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class GanttDataModelTest {

    public static void main(String[] args) {
        testSorting();
        testDuration();
        testTimelineOffsets();
        testToString();

        System.out.println("GanttDataModelTest completed successfully.");
    }

    private static void testSorting() {
        Project p = new ConcreteProject("Proj",
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                "Manager");

        Task t1 = new Task("B", LocalDate.of(2024,1,10), LocalDate.of(2024,1,20));
        Task t2 = new Task("A", LocalDate.of(2024,1,1),  LocalDate.of(2024,1,5));

        p.addTask(t1);
        p.addTask(t2);

        GanttDataModel model = new GanttDataModel(p);

        List<Task> sorted = model.getTasksSortedByStartDate();
        assert sorted.get(0) == t2;
        assert sorted.get(1) == t1;
    }

    private static void testDuration() {
        Task t = new Task("Test",
                LocalDate.of(2024,1,1),
                LocalDate.of(2024,1,11)); // 10 days

        GanttDataModel model = new GanttDataModel(null);

        Duration d = model.getTaskDuration(t);
        assert d.toDays() == 10;
    }

    private static void testTimelineOffsets() {
        Project p = new ConcreteProject("Proj",
                LocalDate.of(2024,1,1),
                LocalDate.of(2024,12,31),
                "Manager");

        Task t = new Task("Task",
                LocalDate.of(2024,1,5),
                LocalDate.of(2024,1,10));

        p.addTask(t);

        GanttDataModel model = new GanttDataModel(p);

        Map<Task, Integer> offsets = model.calculateTaskTimelineOffsets();
        assert offsets.get(t) == 4;
    }

    private static void testToString() {
        Project p = new ConcreteProject("Proj",
                LocalDate.of(2024,1,1),
                LocalDate.of(2024,12,31),
                "Manager");

        GanttDataModel model = new GanttDataModel(p);

        assert model.toString() != null;
    }
}
