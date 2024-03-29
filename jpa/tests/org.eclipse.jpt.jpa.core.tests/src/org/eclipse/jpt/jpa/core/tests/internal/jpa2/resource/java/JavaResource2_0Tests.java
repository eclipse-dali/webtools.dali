/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JavaResource2_0Tests {

	public static Test suite() {
		TestSuite suite = new TestSuite(JavaResource2_0Tests.class.getPackage().getName());
		
		suite.addTestSuite(Access2_0AnnotationTests.class);
		suite.addTestSuite(AssociationOverride2_0Tests.class);
		suite.addTestSuite(AssociationOverrides2_0Tests.class);
		suite.addTestSuite(Cacheable2_0AnnotationTests.class);
		suite.addTestSuite(CollectionTable2_0AnnotationTests.class);
		suite.addTestSuite(ElementCollection2_0AnnotationTests.class);
		suite.addTestSuite(ManyToMany2_0AnnotationTests.class);
		suite.addTestSuite(ManyToOne2_0AnnotationTests.class);
		suite.addTestSuite(MapKeyClass2_0AnnotationTests.class);
		suite.addTestSuite(MapKeyColumn2_0AnnotationTests.class);
		suite.addTestSuite(MapKeyEnumerated2_0AnnotationTests.class);
		suite.addTestSuite(MapKeyJoinColumn2_0AnnotationTests.class);
		suite.addTestSuite(MapKeyJoinColumns2_0AnnotationTests.class);
		suite.addTestSuite(MapKeyTemporal2_0AnnotationTests.class);
		suite.addTestSuite(MapsId2_0AnnotationTests.class);
		suite.addTestSuite(OneToMany2_0AnnotationTests.class);
		suite.addTestSuite(OneToOne2_0AnnotationTests.class);
		suite.addTestSuite(OrderColumn2_0AnnotationTests.class);
		suite.addTestSuite(SequenceGenerator2_0AnnotationTests.class);
			
		return suite;
	}

	private JavaResource2_0Tests() {
		super();
		throw new UnsupportedOperationException();
	}

}
