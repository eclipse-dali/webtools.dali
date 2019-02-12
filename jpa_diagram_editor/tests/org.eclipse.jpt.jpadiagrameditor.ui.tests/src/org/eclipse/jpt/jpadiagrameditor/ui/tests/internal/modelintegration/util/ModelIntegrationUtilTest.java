/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.modelintegration.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.BasicInternalEList;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.preferences.JPAEditorPreferenceInitializer;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleListener;


@SuppressWarnings({"unchecked", "nls"})
public class ModelIntegrationUtilTest {
	
	@Test
	public void getMOINNoResourceFolderTest() {
		IProject project = EasyMock.createMock(IProject.class);
		//EList rr = new BasicInternalEList(String.class);
		EasyMock.expect(project.getProjectRelativePath()).andStubReturn(new Path("src"));
		EasyMock.replay(project);
		IPath p = ModelIntegrationUtil.getEMFResourceFolderPath(project);
		assertNotNull(p);
		assertEquals(2, p.segmentCount());
		assertEquals(p.segment(0), "src");
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getMOINSrcResourceFolderTest() {
		IProject project = EasyMock.createMock(IProject.class);
		EList rr = new BasicInternalEList(String.class);
		rr.add("src");
		EasyMock.expect(project.getProjectRelativePath()).andStubReturn(new Path("src"));
		EasyMock.replay(project);
		IPath p = ModelIntegrationUtil.getEMFResourceFolderPath(project);
		assertNotNull(p);
		assertEquals(2, p.segmentCount());
		assertEquals(p.segment(0), "src");
	}
	
	@Test
	public void copyExistingXMIContentAndDeleteFileTest() throws Exception {
		Bundle b = EasyMock.createMock(Bundle.class);
		BundleContext bc = EasyMock.createMock(BundleContext.class);
		EasyMock.expect(bc.getBundle()).andStubReturn(b);
		EasyMock.expect(b.getSymbolicName()).andStubReturn("jpa_editor");
		bc.addBundleListener(EasyMock.isA(BundleListener.class));
		EasyMock.replay(bc, b);
		
		JPADiagramEditorPlugin p = new JPADiagramEditorPlugin();
		p.start(bc);
		
		IPreferenceStore store = JPADiagramEditorPlugin.getDefault().getPreferenceStore();
		store.putValue(JPAEditorPreferenceInitializer.PROPERTY_DIAGRAM_FOLDER, "diagrams");
		store.getString(JPAEditorPreferenceInitializer.PROPERTY_DIAGRAM_FOLDER);
		
		JPACreateFactory factory = JPACreateFactory.instance();
		JpaProject jpaProject = factory.createJPAProject("Test_" + System.currentTimeMillis());
		assertNotNull(jpaProject);		
		ModelIntegrationUtil.copyExistingXMIContentAndDeleteFile(jpaProject.getProject(), "diagram_name", null);
	}
}
