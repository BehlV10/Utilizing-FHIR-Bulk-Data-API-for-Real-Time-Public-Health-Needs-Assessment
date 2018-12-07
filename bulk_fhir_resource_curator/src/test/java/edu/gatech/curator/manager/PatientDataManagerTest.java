package edu.gatech.curator.manager;

import edu.gatech.curator.entity.SourceSystemEntity;
import edu.gatech.curator.repository.PatientRepository;
import org.hl7.fhir.dstu3.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class PatientDataManagerTest {

    @InjectMocks
    private PatientDataManager subject;

    @Mock
    private PatientRepository patientRepository;

    private SourceSystemEntity sourceSystem;
    private List<Patient> patients;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        sourceSystem = mock(SourceSystemEntity.class);
        when(sourceSystem.getId()).thenReturn(42l);
        when(sourceSystem.getName()).thenReturn("the-answer");
        patients = new ArrayList<Patient>() {{
            add(new Patient() {{
                setId("pId-1");
                setGender(Enumerations.AdministrativeGender.OTHER);
                setBirthDate((new SimpleDateFormat("yyyy-MM-dd").parse("1997-06-09")));
                setAddress(new ArrayList<Address>() {{
                    add(new Address() {{
                        setCity("Brownsville");
                        setExtension(new ArrayList<Extension>() {{
                            add(new Extension(){{
                                setUrl("http://hl7.org/fhir/StructureDefinition/geolocation");
                                setExtension(new ArrayList<Extension>(){{
                                    add(new Extension() {{
                                        setUrl("latitude");
                                        setValue(new DecimalType(26.014905));
                                    }});
                                    add(new Extension() {{
                                        setUrl("longitude");
                                        setValue(new DecimalType(-97.471043));
                                    }});
                                }});
                            }});
                        }});
                    }});
                }});
            }});
            add(new Patient() {{
                setId("pId-2");
                setGender(Enumerations.AdministrativeGender.FEMALE);
                setBirthDate((new SimpleDateFormat("yyyy-MM-dd").parse("1954-09-05")));
                setAddress(new ArrayList<Address>() {{
                    add(new Address() {{
                        setCity("Harlingen");
                        setExtension(new ArrayList<Extension>() {{
                            add(new Extension(){{
                                setUrl("http://hl7.org/fhir/StructureDefinition/geolocation");
                                setExtension(new ArrayList<Extension>() {{
                                    add(new Extension(){{
                                        setUrl("latitude");
                                        setValue(new DecimalType(26.168322));
                                    }});
                                    add(new Extension(){{
                                        setUrl("longitude");
                                        setValue(new DecimalType(-97.704782));
                                    }});
                                }});
                            }});
                        }});
                    }});
                }});
            }});
        }};
    }

    @Test
    public void save() throws ParseException {
        subject.save(sourceSystem, patients);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date p1Birthdate = simpleDateFormat.parse("1997-06-09");
        Date p2Birthdate = simpleDateFormat.parse("1954-09-05");

        verify(patientRepository, times(1)).save(argThat(p -> {
            boolean expected = true;
            expected = expected & p.getId().equals("pId-1");
            expected = expected & p.getGender().equals("Other");
            expected = expected & p.getBirthDate().equals(p1Birthdate);
            expected = expected & p.getLatitude() == 26.014905;
            expected = expected & p.getLongitude() == -97.471043;
            expected = expected & p.getSourceSystemId() == 42l;
            expected = expected & p.getCity().equals("Brownsville");
            expected = expected & p.getSourceSystemName().equals("the-answer");
            return expected;
        }));

        verify(patientRepository, times(1)).save(argThat(p -> {
            boolean expected = true;
            expected = expected & p.getId().equals("pId-2");
            expected = expected & p.getSourceSystemId() == 42l;
            expected = expected & p.getSourceSystemName().equals("the-answer");
            expected = expected & p.getBirthDate().equals(p2Birthdate);
            expected = expected & p.getGender().equals("Female");
            expected = expected & p.getCity().equals("Harlingen");
            expected = expected & p.getLatitude() == 26.168322;
            expected = expected & p.getLongitude() == -97.704782;
            return expected;
        }));
    }
}