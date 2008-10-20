/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.PrivateOwnedAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class PrivateOwnedTests extends EclipseLinkJavaResourceModelTestCase {

	public PrivateOwnedTests(String name) {
		super(name);
	}
	
	private void createPrivateOwnedAnnotation() throws Exception {
		this.createAnnotationAndMembers("PrivateOwned", "");
	}

	private ICompilationUnit createTestPrivateOwned() throws Exception {
		this.createPrivateOwnedAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.PRIVATE_OWNED);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrivateOwned");
			}
		});
	}
	
	public void testPrivateOwned() throws Exception {
		ICompilationUnit cu = this.createTestPrivateOwned();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		PrivateOwnedAnnotation privateOwned = (PrivateOwnedAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.PRIVATE_OWNED);
		assertNotNull(privateOwned);
	}

}
