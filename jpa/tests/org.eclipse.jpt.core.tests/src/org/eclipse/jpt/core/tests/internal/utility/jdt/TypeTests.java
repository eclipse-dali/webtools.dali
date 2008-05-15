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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;

public class TypeTests extends AnnotationTestCase {

	public TypeTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected ICompilationUnit createCompilationUnit(String source) throws CoreException {
		return this.javaProject.createCompilationUnit(PACKAGE_NAME, FILE_NAME, source);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test1() throws Exception {
		ICompilationUnit jdtCompilationUnit = this.createCompilationUnit(this.buildTest1Source());
		assertNotNull(jdtCompilationUnit);
	}

	private String buildTest1Source() {
		StringBuilder sb = new StringBuilder();
		sb.append("public class ").append(TYPE_NAME).append(" {").append(CR);
		sb.append("    private int id;").append(CR);
		sb.append("    public int getId() {").append(CR);
		sb.append("        return this.id;").append(CR);
		sb.append("    }").append(CR);
		sb.append("}").append(CR);
		return sb.toString();
	}

}
