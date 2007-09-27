/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.content.java.mappings;

import java.util.Iterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.internal.resource.java.JPA;
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

//	public void testXXX() throws Exception {
//		this.createTestEntity1();
//		this.assertSourceDoesNotContain("@Id");
//
//		IWorkbench wb = PlatformUI.getWorkbench();
//		IWorkbenchWindow[] windows = wb.getWorkbenchWindows();
//		IWorkbenchWindow window = windows[0];
//		IWorkbenchPage[] pages = window.getPages();
//		IWorkbenchPage page = pages[0];
//		IFile file = (IFile) this.javaProject.getProject().findMember(FILE_PATH);
//		IDE.openEditor(page, file);
//		TestThread t = new TestThread();
//		t.start();
//
////		while (t.isAlive()) {
////			Thread.sleep(50);
////		}
////		assertFalse("see console", t.exceptionCaught);
//	}

//	private class TestThread extends Thread {
//		boolean exceptionCaught = false;
//		TestThread() {
//			super();
//		}
//		@Override
//		public void run() {
//			try {
//				JavaEntityTests.this.xxx();
//			} catch (Exception ex) {
//				this.exceptionCaught = true;
//				throw new RuntimeException(ex);
//			}
//		}
//	}
//
//	void xxx() throws Exception {
//		this.jpaProject().getJpaProject().setThreadLocalModifySharedDocumentCommandExecutor(SynchronousUiCommandExecutor.instance());
//		JavaPersistentAttribute attribute = this.javaPersistentAttributeNamed("id");
//		attribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
//		this.assertSourceContains("@Id");
//	}
//
//	// TODO move to JavaPersistentAttributeTests
//	public void testSetSpecifiedMappingKey() throws Exception {
//		this.createTestEntity1();
//		this.assertSourceDoesNotContain("@Id");
//		JavaPersistentAttribute attribute = this.javaPersistentAttributeNamed("id");
//		attribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
//		this.assertSourceContains("@Id");
//	}
//
//	public void testGetName() throws Exception {
//		this.createTestEntity1();
//		IJavaTypeMapping typeMapping = this.javaPersistentTypeNamed(FULLY_QUALIFIED_TYPE_NAME).getMapping();
//		assertEquals(TYPE_NAME, typeMapping.getName());
//	}
//
//	public void testGetKey() throws Exception {
//		this.createTestEntity1();
//		IJavaTypeMapping typeMapping = this.javaPersistentTypeNamed(FULLY_QUALIFIED_TYPE_NAME).getMapping();
//		assertEquals(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, typeMapping.getKey());
//	}

}
