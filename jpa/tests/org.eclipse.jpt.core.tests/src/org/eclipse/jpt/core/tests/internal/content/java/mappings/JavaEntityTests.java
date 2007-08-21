/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.content.java.mappings;

import java.util.Iterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaEntityTests extends JpaJavaTestCase {

	public JavaEntityTests(String name) {
		super(name);
	}

	private void createTestEntity1() throws CoreException {
		this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
			}
		});
		this.synchPersistenceXml();
	}

	public void testGetName() throws Exception {
		this.createTestEntity1();
		IJavaTypeMapping typeMapping = this.javaPersistentTypeNamed(FULLY_QUALIFIED_TYPE_NAME).getMapping();
		assertEquals(TYPE_NAME, typeMapping.getName());
	}

// TODO we can only execute 1 test for now...
//	public void testGetKey() throws Exception {
//		this.createTestEntity1();
//		IJavaTypeMapping typeMapping = this.javaPersistentTypeNamed(FULLY_QUALIFIED_TYPE_NAME).getMapping();
//		assertEquals(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, typeMapping.getKey());
//	}
//
}
