/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JptCoreContextJavaModelTests
	extends TestCase
{
	public static Test suite() {
		TestSuite suite = new TestSuite(JptCoreContextJavaModelTests.class.getPackage().getName());
		suite.addTestSuite(JavaAssociationOverrideTests.class);
		suite.addTestSuite(JavaAttributeOverrideTests.class);
		suite.addTestSuite(JavaBasicMappingTests.class);
		suite.addTestSuite(JavaCascadeTests.class);
		suite.addTestSuite(JavaColumnTests.class);
		suite.addTestSuite(JavaDiscriminatorColumnTests.class);
		suite.addTestSuite(JavaEmbeddableTests.class);
		suite.addTestSuite(JavaEmbeddedIdMappingTests.class);
		suite.addTestSuite(JavaEmbeddedMappingTests.class);
		suite.addTestSuite(JavaEntityTests.class);
		suite.addTestSuite(JavaGeneratedValueTests.class);
		suite.addTestSuite(JavaIdMappingTests.class);
		suite.addTestSuite(JavaJoinColumnTests.class);	
		suite.addTestSuite(JavaJoinTableTests.class);		
		suite.addTestSuite(JavaMappedSuperclassTests.class);
		suite.addTestSuite(JavaManyToManyMappingTests.class);
		suite.addTestSuite(JavaManyToOneMappingTests.class);
		suite.addTestSuite(JavaNamedQueryTests.class);
		suite.addTestSuite(JavaNamedNativeQueryTests.class);
		suite.addTestSuite(JavaQueryHintTests.class);
		suite.addTestSuite(JavaOneToManyMappingTests.class);
		suite.addTestSuite(JavaOneToOneMappingTests.class);
		suite.addTestSuite(GenericJavaPersistentTypeTests.class);
		suite.addTestSuite(GenericJavaPersistentAttributeTests.class);
		suite.addTestSuite(JavaPrimaryKeyJoinColumnTests.class);
		suite.addTestSuite(JavaSecondaryTableTests.class);
		suite.addTestSuite(JavaSequenceGeneratorTests.class);
		suite.addTestSuite(JavaTableGeneratorTests.class);
		suite.addTestSuite(JavaTableTests.class);
		suite.addTestSuite(JavaTransientMappingTests.class);
		suite.addTestSuite(JavaVersionMappingTests.class);
		return suite;
	}

	private JptCoreContextJavaModelTests() {
		super();
		throw new UnsupportedOperationException();
	}
}
