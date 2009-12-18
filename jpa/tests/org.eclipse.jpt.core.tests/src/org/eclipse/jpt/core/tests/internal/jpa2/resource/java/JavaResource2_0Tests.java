/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.resource.java;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JavaResource2_0Tests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JavaResource2_0Tests.class.getName());
		
		suite.addTestSuite(Access2_0AnnotationTests.class);
		suite.addTestSuite(AssociationOverride2_0Tests.class);
		suite.addTestSuite(AssociationOverrides2_0Tests.class);
		suite.addTestSuite(Cacheable2_0AnnotationTests.class);
		suite.addTestSuite(CollectionTable2_0Tests.class);
		suite.addTestSuite(ElementCollection2_0AnnotationTests.class);
		suite.addTestSuite(MapKeyClass2_0Tests.class);
		suite.addTestSuite(MapsId2_0AnnotationTests.class);
		suite.addTestSuite(OrderColumn2_0AnnotationTests.class);
		suite.addTestSuite(SequenceGenerator2_0Tests.class);
			
		return suite;
	}

	private JavaResource2_0Tests() {
		super();
		throw new UnsupportedOperationException();
	}

}
