/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkPrivateOwnedAnnotation;

@SuppressWarnings("nls")
public class PrivateOwnedTests extends EclipseLinkJavaResourceModelTestCase {

	public PrivateOwnedTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestPrivateOwned() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.PRIVATE_OWNED);
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
		
		EclipseLinkPrivateOwnedAnnotation privateOwned = (EclipseLinkPrivateOwnedAnnotation) attributeResource.getAnnotation(EclipseLink.PRIVATE_OWNED);
		assertNotNull(privateOwned);
	}

}
