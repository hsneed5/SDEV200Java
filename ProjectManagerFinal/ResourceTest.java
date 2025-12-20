public class ResourceTest {

    public static void main(String[] args) {
        testGettersSetters();
        testToString();

        System.out.println("ResourceTest completed successfully.");
    }

    private static void testGettersSetters() {
        Resource r = new Resource("Alice", "Developer", true, "alice@email.com");

        assert r.getResourceName().equals("Alice");
        assert r.getRole().equals("Developer");
        assert r.isAvailable();
        assert r.getContactInfo().equals("alice@email.com");

        r.setResourceName("Bob");
        r.setRole("Manager");
        r.setAvailability(false);
        r.setContactInfo("bob@email.com");

        assert r.getResourceName().equals("Bob");
        assert r.getRole().equals("Manager");
        assert !r.isAvailable();
        assert r.getContactInfo().equals("bob@email.com");
    }

    private static void testToString() {
        Resource r = new Resource("Alice", "Developer", true, "contact");
        assert r.toString() != null;
    }
}
