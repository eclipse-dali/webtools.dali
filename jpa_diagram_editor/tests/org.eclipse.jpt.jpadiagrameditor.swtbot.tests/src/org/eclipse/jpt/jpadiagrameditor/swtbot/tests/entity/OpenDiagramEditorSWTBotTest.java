/*******************************************************************************
 * Copyright (c) 2013, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.entity;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.AbstractSwtBotEditorTest;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.Utils;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.junit.BeforeClass;
import org.junit.Test;

public class OpenDiagramEditorSWTBotTest extends AbstractSwtBotEditorTest{
	
	private static String TEST_PROJECT = "Test_" + System.currentTimeMillis();
	private static JpaProject jpaProjectToOpen;

	@BeforeClass
	public static void beforeClass() throws Exception {
		jpaProjectToOpen = createJPa20Project(TEST_PROJECT);
	}
	
	/**
	 * Test that the JPA Diagram editor is opened when the context menu
	 * "JPA Tools -> Open Diagram" of the project is called.
	 */
	@Test
	public void testOpenDiagramOnProjectLevel() {
		Utils.sayTestStarted("testOpenDiagramOnProjectLevel");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		workbenchBot.closeAllEditors();
		// open JPA diagram editor on project level: JPA Tools -> Open Diagram
		SWTBotGefEditor diagramEditor = editorProxy
				.openDiagramOnJPAProjectNode(jpaProjectToOpen.getName());
		diagramEditor.close();

		Utils.sayTestFinished("testOpenDiagramOnProjectLevel");
	}

	/**
	 * Test that the JPA Diagram editor is opened when the context menu
	 * "Open Diagram" of the JPA content node is called.
	 */
	@Test
	public void testOpenDiagramOnJPAContentNodeLevel() {
		Utils.sayTestStarted("testOpenDiagramOnJPAContentNodeLevel");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		workbenchBot.closeAllEditors();
		// open JPA diagram editor on JPA content level: Open Diagram
		SWTBotGefEditor diagramEditor = editorProxy
				.openDiagramOnJPAContentNode(jpaProjectToOpen.getName());
		diagramEditor.close();

		Utils.sayTestFinished("testOpenDiagramOnJPAContentNodeLevel");
	}

}
