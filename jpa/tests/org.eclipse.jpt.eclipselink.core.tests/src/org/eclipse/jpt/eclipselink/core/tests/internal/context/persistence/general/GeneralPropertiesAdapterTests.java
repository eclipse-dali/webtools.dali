/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.general;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.context.persistence.GenericProperty;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.PersistenceUnitPropertyListListener;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.general.EclipseLinkGeneralProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.general.GeneralProperties;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.PersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 *  GeneralAdapterTests
 */
@SuppressWarnings("nls")
public class GeneralPropertiesAdapterTests extends PersistenceUnitTestCase
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
		
		this.persistenceUnitPut("misc.property.1", "value.1");
		this.persistenceUnitPut(EXCLUDE_ECLIPSELINK_ORM_KEY, EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE.toString());
		this.persistenceUnitPut("misc.property.2", "value.2");

		return;
	}

	
	// ********** Listeners tests **********
	public void testHasListeners() throws Exception {
		// new
		ListAspectAdapter<PersistenceUnit, Property> propertiesAdapter = 
			(ListAspectAdapter<PersistenceUnit, Property>) this.subject.getPropertiesAdapter();
		GenericProperty ctdProperty = (GenericProperty) this.getPersistenceUnit().getProperty(EXCLUDE_ECLIPSELINK_ORM_KEY);
		ListValueModel<Property> propertyListAdapter = this.subject.getPropertyListAdapter();
		
		assertTrue(propertiesAdapter.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(ctdProperty.hasAnyPropertyChangeListeners(Property.VALUE_PROPERTY));
		this.verifyHasListeners(this.generalProperties, GeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY);
		this.verifyHasListeners(propertyListAdapter);
		
		EclipseLinkGeneralProperties elGeneralProperties = (EclipseLinkGeneralProperties) this.generalProperties;
		PersistenceUnitPropertyListListener propertyListListener = elGeneralProperties.propertyListListener();
		propertyListAdapter.removeListChangeListener(ListValueModel.LIST_VALUES, propertyListListener);
		assertTrue(propertiesAdapter.hasAnyListChangeListeners(ListValueModel.LIST_VALUES)); // other properties are still listening
		this.verifyHasListeners(this.generalProperties, GeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY);
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
