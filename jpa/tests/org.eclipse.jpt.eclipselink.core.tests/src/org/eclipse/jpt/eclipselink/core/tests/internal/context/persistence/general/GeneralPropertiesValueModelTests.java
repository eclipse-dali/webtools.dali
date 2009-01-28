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

import org.eclipse.jpt.eclipselink.core.internal.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.general.GeneralProperties;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.PersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * GeneralPropertyValueModelTests
 */
@SuppressWarnings("nls")
public class GeneralPropertiesValueModelTests extends PersistenceUnitTestCase
{
	private GeneralProperties generalProperty;

	private WritablePropertyValueModel<Boolean> excludeEclipselinkOrmHolder;
	private PropertyChangeListener excludeEclipselinkOrmListener;
	private PropertyChangeEvent excludeEclipselinkOrmEvent;

	public static final Boolean EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE = Boolean.FALSE;

	public GeneralPropertiesValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.generalProperty = this.subject.getGeneralProperties(); // Subject
		PropertyValueModel<GeneralProperties> generalPropertyHolder = new SimplePropertyValueModel<GeneralProperties>(this.generalProperty);
		
		this.excludeEclipselinkOrmHolder = this.buildExcludeEclipselinkOrmAA(generalPropertyHolder);
		this.excludeEclipselinkOrmListener = this.buildExcludeEclipselinkOrmChangeListener();
		this.excludeEclipselinkOrmHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.excludeEclipselinkOrmListener);
		this.excludeEclipselinkOrmEvent = null;
	}

	public void testHasListeners() {
		AbstractModel subjectGeneralProperty = (AbstractModel) this.generalProperty; // Subject
		
		PropertyAspectAdapter<GeneralProperties, Boolean> excludeEclipselinkOrmAA = 
			(PropertyAspectAdapter<GeneralProperties, Boolean>) this.excludeEclipselinkOrmHolder;
		assertTrue(excludeEclipselinkOrmAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(subjectGeneralProperty.hasAnyPropertyChangeListeners(GeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY));
		
		excludeEclipselinkOrmAA.removePropertyChangeListener(PropertyValueModel.VALUE, this.excludeEclipselinkOrmListener);
		assertFalse(subjectGeneralProperty.hasAnyPropertyChangeListeners(GeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY));
		assertFalse(excludeEclipselinkOrmAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	/**
	 * Initializes directly the PU properties before testing. 
	 */
	@Override
	protected void populatePu() {
		this.persistenceUnitSetProperty(
			GeneralProperties.ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM, 
			EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE);
		return;
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.generalProperty;
	}

	// ****** ExcludeEclipselinkOrm *******
	private WritablePropertyValueModel<Boolean> buildExcludeEclipselinkOrmAA(PropertyValueModel<GeneralProperties> subjectHolder) {
		return new PropertyAspectAdapter<GeneralProperties, Boolean>(subjectHolder, GeneralProperties.EXCLUDE_ECLIPSELINK_ORM_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getExcludeEclipselinkOrm();
			}

			@Override
			protected void setValue_(Boolean enumValue) {
				this.subject.setExcludeEclipselinkOrm(enumValue);
			}
		};
	}

	private PropertyChangeListener buildExcludeEclipselinkOrmChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				GeneralPropertiesValueModelTests.this.excludeEclipselinkOrmEvent = e;
			}
		};
	}

	// ****** Tests ******* 
	public void testValue() {
		// ****** ExcludeEclipselinkOrm ******* 
		this.verifyExcludeEclipselinkOrmAAValue(EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE);
		assertEquals(GeneralProperties.DEFAULT_EXCLUDE_ECLIPSELINK_ORM, this.generalProperty.getDefaultExcludeEclipselinkOrm());
	}

	public void testSetValue() throws Exception {
		// ****** ExcludeEclipselinkOrm ******* 
		this.excludeEclipselinkOrmEvent = null;
		this.verifyHasListeners(this.excludeEclipselinkOrmHolder, PropertyValueModel.VALUE);
		Boolean newExcludeEclipselinkOrm = !EXCLUDE_ECLIPSELINK_ORM_TEST_VALUE;
		// Modify the property holder
		this.excludeEclipselinkOrmHolder.setValue(newExcludeEclipselinkOrm);
		this.verifyExcludeEclipselinkOrmAAValue(newExcludeEclipselinkOrm);
		assertNotNull(this.excludeEclipselinkOrmEvent);
	}

	public void testSetNullValue() {
		String notDeleted = "Property not deleted";
		// ****** ExcludeEclipselinkOrm *******
		this.excludeEclipselinkOrmEvent = null;
		// Setting the property holder
		this.excludeEclipselinkOrmHolder.setValue(null);
		// testing Holder
		this.verifyExcludeEclipselinkOrmAAValue(null);
		assertNotNull(this.excludeEclipselinkOrmEvent);
		// testing PU properties
		this.verifyPuHasNotProperty(GeneralProperties.ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM, notDeleted);
	}

	// ****** convenience methods *******

	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifyExcludeEclipselinkOrmAAValue(Boolean testValue) {
		this.verifyAAValue(
			testValue, 
			this.generalProperty.getExcludeEclipselinkOrm(), 
			this.excludeEclipselinkOrmHolder, 
			GeneralProperties.ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM);
	}

	// ********** get/set property **********
	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		throw new UnsupportedOperationException();
	}
}
