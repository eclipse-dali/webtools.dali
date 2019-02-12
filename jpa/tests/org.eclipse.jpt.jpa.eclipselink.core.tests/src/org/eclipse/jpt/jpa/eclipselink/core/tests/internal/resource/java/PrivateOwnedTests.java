/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.PrivateOwnedAnnotation;

@SuppressWarnings("nls")
public class PrivateOwnedTests extends EclipseLinkJavaResourceModelTestCase {

	public PrivateOwnedTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestPrivateOwned() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(EclipseLink.PRIVATE_OWNED);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrivateOwned");
			}
		});
	}
	
	public void testPrivateOwned() throws Exception {
		ICompilationUnit cu = this.createTestPrivateOwned();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		PrivateOwnedAnnotation privateOwned = (PrivateOwnedAnnotation) resourceField.getAnnotation(EclipseLink.PRIVATE_OWNED);
		assertNotNull(privateOwned);
	}

}
