package edu.gatech.curator.manager;

import edu.gatech.curator.entity.SourceSystemEntity;
import edu.gatech.curator.repository.ObservationRepository;
import org.hl7.fhir.dstu3.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ObservationDataManagerTest {
    @InjectMocks
    private ObservationDataManager subject;

    @Mock
    private ObservationRepository observationRepository;

    private SourceSystemEntity sourceSystem;
    private List<Observation> observations;
    private Date observationDate;

    @Before
    public void setUp() throws Exception {
        sourceSystem = mock(SourceSystemEntity.class);
        when(sourceSystem.getId()).thenReturn(42l);
        when(sourceSystem.getName()).thenReturn("iClinic");

        observationDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-02");
        observations = new ArrayList<Observation>(){{
            add(new Observation(){{
                setId("some-observation-guid");
                setSubject(new Reference(){{
                    setReference("some-patient-guid");
                }});
                setIssued(observationDate);
                setCode(new CodeableConcept(){{
                    setText("Body Mass Index");
                    addCoding(new Coding(){{
                        setSystem("http://loinc.org");
                        setCode("39156-5");
                        setDisplay("Body Mass Index Display Text");
                    }});
                }});
                setValue(new Quantity(){{
                    setValue(16.76230320043242);
                    setUnit("kg/m2");
                    setCode("kg/m2-code");
                    setSystem("http://unitsofmeasure.org");
                }});
            }});
        }};

        initMocks(this);
    }

    @Test
    public void save() {
        assertThat(subject).isNotNull();

        subject.save(sourceSystem, observations);

        verify(observationRepository).save(argThat(a ->
                a.getId().contentEquals("some-observation-guid") &&
                a.getPatientId().contentEquals("some-patient-guid") &&
                a.getIssued().equals(observationDate) &&
                a.getCodeText().contentEquals("Body Mass Index") &&
                a.getQuantityValue() == 16.76230320043242 &&
                a.getQuantityUnit().contentEquals("kg/m2") &&
                a.getSourceSystemId() == 42l &&
                a.getSourceSystemName().contentEquals("iClinic")));
    }
}