/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.utility.jdt;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.utility.jdt.JDTType;

public class TypeTests extends AnnotationTestCase {

	private IType jdtType;
	private JDTType testType;


	public TypeTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.jdtType = this.createTestType();
		this.testType = this.buildType(this.jdtType);
	}

	@Override
	protected void tearDown() throws Exception {
		this.testType = null;
		this.jdtType = null;
		super.tearDown();
	}

	public void testJdtMember() throws Exception {
		assertEquals(this.jdtType, this.testType.getJdtMember());
	}

	public void testTopLevelDeclaringType() throws Exception {
		assertEquals(this.testType, this.testType.getTopLevelDeclaringType());
	}

	public void testDeclaringType() throws Exception {
		assertNull(this.testType.declaringType());
	}

	public void testName() throws Exception {
		assertEquals(TYPE_NAME, this.testType.name());
	}

}
