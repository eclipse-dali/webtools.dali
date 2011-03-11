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

import java.io.File;
import java.util.NoSuchElementException;

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
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * AllJpaEditorTests
 * 
 * Required Java system properties:
 *    -Dorg.eclipse.jpt.jpa.jar=<jpa.jar path>
 *    -Dorg.eclipse.jpt.eclipselink.jar=<eclipselink.jar path>
 */

@RunWith(Suite.class)
@SuiteClasses( {
				JPAEditorPreferenceInitializerTest.class,
	 			JPAEditorPreferencesPageTest.class,
	 			AddJPAEntityFeatureTest.class,
				ClickAddFeatureTest.class,
				CreateDeleteEntity.class,
				DirectEditAttributeFeatureTest.class,
				EditorTest.class,	
				JPAEditorToolBehaviorProviderTest.class,
				JPAEditorUtilTest.class,
				JPASolverTest.class,
				OpenMiniatureViewFeatureTest.class,
				RefactorAttributeTypeFeatureTest.class,
				SaveEntityFeatureTest.class,
				AddRelationFeatureTest.class,
				AddAttributeFeatureTest.class,
				CreateDeleteOnlyAttributeTest.class,
				JPAProjectListenerTest.class,
				ModelIntegrationTest.class,
				ModelIntegrationUtilTest.class,
				LayoutEntityFeatureTest.class,
				DeleteRelationFeatureTest.class,
				CreateRelationsTest.class
				})

public class AllJpaEditorTests {
	
	/**
	 * This check is necessary to abort the test suite in the build 
	 * when the environment is not setup properly.
	 * 
	 * @throws NoSuchElementException
	 */
    @BeforeClass
    public static void verifyRequiredJarsExists() throws NoSuchElementException {
    	verifyJpaJarExists();
    	verifyEclipseLinkJarExists();
    }

	private static void verifyJpaJarExists() throws NoSuchElementException {
		verifyRequiredPropertyExists(JPACreateFactory.JPA_JAR_NAME_SYSTEM_PROPERTY);
		verifyRequiredFileExists(JPACreateFactory.JPA_JAR_NAME_SYSTEM_PROPERTY);
	}
	
	private static void verifyEclipseLinkJarExists() throws NoSuchElementException {
		verifyRequiredPropertyExists(JPACreateFactory.ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY);
		verifyRequiredFileExists(JPACreateFactory.ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY);
	}
		
	private static void verifyRequiredPropertyExists(String propertyName) throws NoSuchElementException {
		if(JPACreateFactory.getSystemProperty(propertyName) == null) {
			throw new NoSuchElementException(errorMissingProperty(propertyName));
		}
	}

	private static void verifyRequiredFileExists(String propertyName) throws NoSuchElementException {
		String fileName = JPACreateFactory.getSystemProperty(propertyName);
		if( ! (new File(fileName)).exists()) {
			throw new NoSuchElementException(errorJarFileDoesNotExist(fileName));
		}
	}

	private static String errorMissingProperty(String propertyName) {
		return "Missing Java system property: \"" + propertyName + "\"";
	}

	private static String errorJarFileDoesNotExist(String fileName) {
		return "JAR file doesn't exist: \"" + fileName + "\"";
	}

}
