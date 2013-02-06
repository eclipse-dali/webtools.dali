/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.resource.java;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JavaResource2_1Tests
{

	public static Test suite() {
		TestSuite suite = new TestSuite(JavaResource2_1Tests.class.getPackage().getName());

		suite.addTestSuite(NamedStoredProcedureQuery2_1AnnotationTests.class);
		suite.addTestSuite(NamedStoredProcedureQueries2_1AnnotationTests.class);
		suite.addTestSuite(StoredProcedureParameter2_1AnnotationTests.class);

		return suite;
	}

	private JavaResource2_1Tests() {
		super();
		throw new UnsupportedOperationException();
	}

}
