/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.general;

import org.eclipse.jpt.core.internal.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.general.GeneralProperties;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.EclipseLinkPersistenceUnitTestCase;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;

/**
 *  GeneralAdapterTests
 */
@SuppressWarnings("nls")
public class GeneralPropertiesAdapterTests extends EclipseLinkPersistenceUnitTestCase
{
	private GeneralProperties generalProperties;

	public static final String EXCLUDE_ECLIPSELINK_ORM_KEY = GeneralProperties.ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM;
	public static final Boolean EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE = false;
	public static final Boolean EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE_2 = ! EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE;

	
	public GeneralPropertiesAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.generalProperties = this.subject.getGeneralProperties();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		
		this.generalProperties.addPropertyChangeListener(GeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY, propertyChangeListener);

		this.clearEvent();
	}

	/**
	 * Initializes directly the PU properties before testing.
	 */
	@Override
	protected void populatePu() {
		this.modelPropertiesSizeOriginal = 1;
		this.propertiesTotal = this.modelPropertiesSizeOriginal + 2; // misc properties
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		this.persistenceUnitSetProperty("misc.property.1", "value.1");
		this.persistenceUnitSetProperty(EXCLUDE_ECLIPSELINK_ORM_KEY, EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE.toString());
		this.persistenceUnitSetProperty("misc.property.2", "value.2");

		return;
	}

	
	// ********** ExcludeEclipselinkOrm tests **********
	public void testSetExcludeEclipselinkOrm() throws Exception {
		this.verifyModelInitialized(
			EXCLUDE_ECLIPSELINK_ORM_KEY,
			EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE);
		this.verifySetProperty(
			EXCLUDE_ECLIPSELINK_ORM_KEY,
			EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE,
			EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE_2);
	}

	public void testAddRemoveExcludeEclipselinkOrm() throws Exception {
		this.verifyAddRemoveProperty(
			EXCLUDE_ECLIPSELINK_ORM_KEY,
			EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE,
			EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE_2);
	}



	// ********** get/set property **********
	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(GeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY))
			modelValue = this.generalProperties.getExcludeEclipselinkOrm();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}

	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(GeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY))
			this.generalProperties.setExcludeEclipselinkOrm((Boolean) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}
	
	@Override
	protected PersistenceUnitProperties getModel() {
		return this.generalProperties;
	}
}
