package oopproject.rpmsfinal;

public class PatientSummary {
    private final String userId;
    private final String name;

    public PatientSummary(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
