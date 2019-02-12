/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TemporalType;

@SuppressWarnings("nls")
public class TemporalTests extends JpaJavaResourceModelTestCase {

	public TemporalTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestTemporal() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.TEMPORAL);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Temporal");
			}
		});
	}
	
	private ICompilationUnit createTestTemporalWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.TEMPORAL, JPA.TEMPORAL_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Temporal(TemporalType.DATE)");
			}
		});
	}

	public void testTemporal() throws Exception {
		ICompilationUnit cu = this.createTestTemporal();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TemporalAnnotation temporal = (TemporalAnnotation) resourceField.getAnnotation(JPA.TEMPORAL);
		assertNotNull(temporal);
	}
	
	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestTemporalWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TemporalAnnotation temporal = (TemporalAnnotation) resourceField.getAnnotation(JPA.TEMPORAL);
		assertEquals(TemporalType.DATE, temporal.getValue());
	}
	
	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestTemporal();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		TemporalAnnotation temporal = (TemporalAnnotation) resourceField.getAnnotation(JPA.TEMPORAL);

		temporal.setValue(TemporalType.TIME);
		
		assertSourceContains("@Temporal(TIME)", cu);
		
		temporal.setValue(null);
		
		assertSourceDoesNotContain("@Temporal(", cu);
	}

}
