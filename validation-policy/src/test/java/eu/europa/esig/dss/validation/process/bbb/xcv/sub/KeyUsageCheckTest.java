package eu.europa.esig.dss.validation.process.bbb.xcv.sub;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.europa.esig.dss.jaxb.detailedreport.XmlConstraint;
import eu.europa.esig.dss.jaxb.detailedreport.XmlStatus;
import eu.europa.esig.dss.jaxb.detailedreport.XmlSubXCV;
import eu.europa.esig.dss.jaxb.diagnostic.XmlCertificate;
import eu.europa.esig.dss.validation.process.bbb.xcv.sub.checks.KeyUsageCheck;
import eu.europa.esig.dss.validation.reports.wrapper.CertificateWrapper;
import eu.europa.esig.jaxb.policy.Level;
import eu.europa.esig.jaxb.policy.MultiValuesConstraint;

public class KeyUsageCheckTest {

	@Test
	public void keyUsageCheck() throws Exception {
		List<String> keyUsageBits = new ArrayList<String>();
		keyUsageBits.add("Valid_Key");

		MultiValuesConstraint constraint = new MultiValuesConstraint();
		constraint.setLevel(Level.FAIL);
		constraint.getId().add(keyUsageBits.get(0));

		XmlCertificate xc = new XmlCertificate();
		xc.setKeyUsageBits(keyUsageBits);

		XmlSubXCV result = new XmlSubXCV();
		KeyUsageCheck kuc = new KeyUsageCheck(result, new CertificateWrapper(xc), constraint);
		kuc.execute();

		List<XmlConstraint> constraints = result.getConstraint();
		assertEquals(1, constraints.size());
		assertEquals(XmlStatus.OK, constraints.get(0).getStatus());
	}

	@Test
	public void failedKeyUsageCheck() throws Exception {
		List<String> keyUsageBits = new ArrayList<String>();
		keyUsageBits.add("Valid_Key");

		MultiValuesConstraint constraint = new MultiValuesConstraint();
		constraint.setLevel(Level.FAIL);
		constraint.getId().add("Invalid_Key");

		XmlCertificate xc = new XmlCertificate();
		xc.setKeyUsageBits(keyUsageBits);

		XmlSubXCV result = new XmlSubXCV();
		KeyUsageCheck kuc = new KeyUsageCheck(result, new CertificateWrapper(xc), constraint);
		kuc.execute();

		List<XmlConstraint> constraints = result.getConstraint();
		assertEquals(1, constraints.size());
		assertEquals(XmlStatus.NOT_OK, constraints.get(0).getStatus());
	}

}
