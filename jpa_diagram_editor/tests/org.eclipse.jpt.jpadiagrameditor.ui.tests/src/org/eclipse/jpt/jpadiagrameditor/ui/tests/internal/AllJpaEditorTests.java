/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal;

import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.editor.EditorTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature.AddAttributeFeatureTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature.AddJPAEntityFeatureTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature.AddRelationFeatureTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature.ClickAddFeatureTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature.CreateDeleteOnlyAttributeTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature.DeleteRelationFeatureTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature.DirectEditAttributeFeatureTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature.LayoutEntityFeatureTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature.OpenMiniatureViewFeatureTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature.RefactorAttributeTypeFeatureTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature.SaveEntityFeatureTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.listener.JPAProjectListenerTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.modelintegration.ui.ModelIntegrationTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.modelintegration.util.ModelIntegrationUtilTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.preferences.JPAEditorPreferenceInitializerTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.preferences.JPAEditorPreferencesPageTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.provider.JPAEditorToolBehaviorProviderTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.relation.CreateRelationsTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.util.CreateDeleteEntity;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.util.JPAEditorUtilTest;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.util.JPASolverTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// I have commented out the tests that required the persistence.jar, 
// please use a "org.eclipse.jpt.jpa.jar" environment variable to access it.
//
// Before un-commented out these tests, make sure that they are passing locally
// by using the persistence.jar through the environment variable "org.eclipse.jpt.jpa.jar".
// If not it will break the build.
// -Tran Le

@RunWith(Suite.class)
@SuiteClasses( {
				JPAEditorPreferenceInitializerTest.class,
	 			JPAEditorPreferencesPageTest.class,
	 			AddJPAEntityFeatureTest.class,
//				ClickAddFeatureTest.class,
//				CreateDeleteEntity.class,
				DirectEditAttributeFeatureTest.class,
				EditorTest.class,	
				JPAEditorToolBehaviorProviderTest.class,
//				JPAEditorUtilTest.class,
				JPASolverTest.class,
//				OpenMiniatureViewFeatureTest.class,				 
//				RefactorAttributeTypeFeatureTest.class,
//				SaveEntityFeatureTest.class,
				AddRelationFeatureTest.class,
				AddAttributeFeatureTest.class,
//				CreateDeleteOnlyAttributeTest.class,
//				JPAProjectListenerTest.class,
//				ModelIntegrationTest.class,
				ModelIntegrationUtilTest.class,
				LayoutEntityFeatureTest.class,
				DeleteRelationFeatureTest.class,
//				CreateRelationsTest.class
				})

public class AllJpaEditorTests {

}
