package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.caching.JptEclipseLinkPersistenceCachingTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.connection.JptEclipseLinkPersistenceConnectionTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.customization.JptEclipseLinkPersistenceCustomizationTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.general.JptEclipseLinkPersistenceGeneralTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.logging.JptEclipseLinkPersistenceLoggingTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.options.JptEclipseLinkPersistenceOptionsTests;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.schema.generation.JptEclipseLinkPersistenceSchemaGenerationTests;

public class JptEclipseLinkCorePersistenceContextModelTests extends TestCase
{
	public static Test suite() {
		return suite(true);
	}

	public static Test suite(boolean all) {
		TestSuite suite = new TestSuite(JptEclipseLinkCorePersistenceContextModelTests.class.getName());
		suite.addTestSuite(EclipseLinkPersistenceUnitTests.class);
		suite.addTest(JptEclipseLinkPersistenceGeneralTests.suite());
		suite.addTest(JptEclipseLinkPersistenceConnectionTests.suite());
		suite.addTest(JptEclipseLinkPersistenceCustomizationTests.suite());
		suite.addTest(JptEclipseLinkPersistenceCachingTests.suite());
		suite.addTest(JptEclipseLinkPersistenceLoggingTests.suite());
		suite.addTest(JptEclipseLinkPersistenceOptionsTests.suite());
		suite.addTest(JptEclipseLinkPersistenceSchemaGenerationTests.suite());
		return suite;
	}

	private JptEclipseLinkCorePersistenceContextModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
