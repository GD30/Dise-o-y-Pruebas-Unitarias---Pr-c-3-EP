package udl.prac3.pruebasunitarias.medicalconsultation;

public class Posology {

    private float dose;
    private float freq;
    private FqUnit freqUnit;

    public Posology (float d, float f, FqUnit u) {

if (d <= 0 || f <= 0 || u == null) {
            throw new IllegalArgumentException("Incorrect posology parameters");
        }
        this.dose = d;
        this.freq = f;
        this.freqUnit = u;
    }

    public float getDose() {
        return dose;
    }

    public void setDose(float d) {
        if (d <= 0) {
            throw new IllegalArgumentException("Dose must be greater than zero");
        }
        this.dose = d;
    }

    public float getFreq() {
        return freq;
    }

    public void setFreq(float f) {
        if (f <= 0) {
            throw new IllegalArgumentException("Frequency must be greater than zero");
        }
        this.freq = f;
    }

    public FqUnit getFreqUnit() {
        return freqUnit;
    }

    public void setFreqUnit(FqUnit u) {
        if (u == null) {
            throw new IllegalArgumentException("Frequency unit cannot be null");
        }
        this.freqUnit = u;
    } // Initializes attributes
        // the getters and setters
}
