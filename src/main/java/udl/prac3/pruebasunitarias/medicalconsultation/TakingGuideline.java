package udl.prac3.pruebasunitarias.medicalconsultation;

public class TakingGuideline {
    private dayMoment dMoment;
    private float duration;
    private Posology posology;
    private String instructions;

    public TakingGuideline(dayMoment dM, float du, float d, float f, FqUnit fu, String i) {
        if (dM == null || du <= 0) throw new IllegalArgumentException("Incorrect taking guideline parameters: day moment cannot be null and duration must be positive");

        this.dMoment = dM;
        this.duration = du;
        this.posology = new Posology(d, f, fu);
        this.instructions = i;
    }

    public dayMoment getdMoment() {
        return dMoment;
    }

    public void setdMoment(dayMoment dM) {
        if (dM == null) throw new IllegalArgumentException("Day moment cannot be null");
        this.dMoment = dM;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float du) {
        if (du <= 0) throw new IllegalArgumentException("Duration must be greater than zero");

        this.duration = du;
    }

    public Posology getPosology() {
        return posology;
    }

    public void setPosology(Posology p) {
        if (p == null) throw new IllegalArgumentException("Posology cannot be null");

        this.posology = p;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String i) {
        this.instructions = i;
    }

    @Override
    public String toString() {
        return "TakingGuideline{" +
                "moment=" + dMoment +
                ", duration=" + duration +
                ", posology=" + posology +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}