package udl.prac3.pruebasunitarias.services;

import org.junit.jupiter.api.*;
import udl.prac3.pruebasunitarias.data.*;
import udl.prac3.pruebasunitarias.medicalconsultation.MedicalHistory;
import udl.prac3.pruebasunitarias.medicalconsultation.MedicalPrescription;
import udl.prac3.pruebasunitarias.services.exceptions.NotCompletedMedicalPrescription;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class HealthNationalServiceImplTest {

    private HealthNationalServiceImpl hns;

    @BeforeEach
    void setUp() {
        hns = new HealthNationalServiceImpl();
    }

    @Test
    void getMedicalHistory_createsAndCachesHistory() throws Exception {
        HealthCardID cip = new HealthCardID("ABCD1234EFGH5678");

        MedicalHistory h1 = hns.getMedicalHistory(cip);
        MedicalHistory h2 = hns.getMedicalHistory(cip);

        assertNotNull(h1);
        assertSame(h1, h2, "Debería devolver la misma instancia cacheada para el mismo CIP");
    }

    @Test
    void sendHistoryAndPrescription_incompletePrescription_throwsNotCompleted() throws Exception {
        HealthCardID cip = new HealthCardID("ABCD1234EFGH5678");
        String illness = "MIGRAINE";

        MedicalHistory history = hns.getMedicalHistory(cip);
        MedicalPrescription presc = hns.getMedicalPrescription(cip, illness);

        NotCompletedMedicalPrescription ex = assertThrows(
                NotCompletedMedicalPrescription.class,
                () -> hns.sendHistoryAndPrescription(cip, history, illness, presc)
        );

        assertTrue(ex.getMessage().toLowerCase().contains("not complete"));
    }

    @Test
    void sendHistoryAndPrescription_completePrescription_generatesCode() throws Exception {
        HealthCardID cip = new HealthCardID("ABCD1234EFGH5678");
        String illness = "MIGRAINE";

        MedicalHistory history = hns.getMedicalHistory(cip);
        MedicalPrescription presc = hns.getMedicalPrescription(cip, illness);

        ProductID prod = new ProductID("123456789001");
        String[] g = { "BEFORELUNCH", "30", "2.0", "1.0", "DAY", "Take with water" };
        presc.addLine(prod, g);

        presc.setPrescDate(new Date());
        presc.setEndDate(new Date(System.currentTimeMillis() + 86400000L)); // +1 día
        presc.seteSign(new DigitalSignature(new byte[]{1,2,3}));

        MedicalPrescription returned = hns.sendHistoryAndPrescription(cip, history, illness, presc);

        assertNotNull(returned.getPrescCode(), "El HNS debería generar el ePrescripCode");
        assertEquals(16, returned.getPrescCode().getCode().length(), "El código debe ser de 16 chars");
    }
}
