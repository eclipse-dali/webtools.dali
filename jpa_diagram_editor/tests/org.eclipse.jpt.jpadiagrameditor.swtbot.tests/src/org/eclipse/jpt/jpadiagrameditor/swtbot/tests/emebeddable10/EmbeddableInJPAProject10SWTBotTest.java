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
package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.emebeddable10;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.AbstractSwtBotEditorTest;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.Utils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation.HasReferenceType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
@SuppressWarnings("restriction")
public class EmbeddableInJPAProject10SWTBotTest extends AbstractSwtBotEditorTest{
	
	private static String TEST_PROJECT = "Test10_" + System.currentTimeMillis();
	private static JpaProject jpaProject;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		jpaProject = createJPa10Project(TEST_PROJECT);
	}
	
	@Test
	public void testJPA10WithEmbeddables() throws CoreException {
		Utils.sayTestStarted("testJPA10WithEmbeddables");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable1 = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);
		SWTBotGefEditPart embeddable2 = editorProxy.addEmbeddableToDiagram(50,
				200, jpaProject);
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(300, 50,
				jpaProject);
		
		boolean notAllowed = false;

		try {
			jpaDiagramEditor.activateTool(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName);
		} catch (WidgetNotFoundException e) {
			notAllowed = true;
		}

		assertTrue(
				"The action \"Embed Collection of Elements\" must not be available by JPA projects with 1.0 facet.",
				notAllowed);
		
		editorProxy.testNoEmbeddedConnectionIsCreated(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, 0, embeddable1, embeddable2, false);
				
		editorProxy.embedConnection(jpaDiagramEditor, JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, entity, embeddable1,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 2);

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(300, 300,
				jpaProject);
		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						0, embeddable1, entity1);
		
		editorProxy
		.testNoConnectionIsCreated(
				JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				0, embeddable1, entity1);
		
		editorProxy
		.testNoConnectionIsCreated(
				JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				1, embeddable1, entity1);
		
		editorProxy
		.testNoConnectionIsCreated(
				JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				0, embeddable1, entity1);
		
		editorProxy
		.testNoConnectionIsCreated(
				JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				1, embeddable1, entity1);
		
		editorProxy
		.testNoConnectionIsCreated(
				JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
				0, embeddable1, entity1);
		
		editorProxy
		.testNoConnectionIsCreated(
				JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
				1, embeddable1, entity1);
		
		String embeddableName = editorProxy.getJPTObjectForGefElement(embeddable1).getSimpleName();
		String embeddingAttributeName = JPAEditorUtil.decapitalizeFirstLetter(embeddableName);
		editorProxy.deleteAttributeInJPT(entity, embeddingAttributeName);
		
		editorProxy.deleteDiagramElements(false);
		
//		jpaDiagramEditor.saveAndClose();
		
		Utils.sayTestFinished("testJPA10WithEmbeddables");

	}
}
