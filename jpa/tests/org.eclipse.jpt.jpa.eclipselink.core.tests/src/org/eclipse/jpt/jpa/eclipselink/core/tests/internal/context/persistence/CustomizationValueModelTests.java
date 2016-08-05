/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence;

import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomizationEntity;

/**
 * CustomizationValueModelTests
 */
@SuppressWarnings("nls")
public class CustomizationValueModelTests extends EclipseLinkPersistenceUnitTestCase
{
	private EclipseLinkCustomization customization;

	private ModifiablePropertyValueModel<Boolean> throwExceptionsHolder;
	private PropertyChangeListener throwExceptionsListener;
	private PropertyChangeEvent throwExceptionsEvent;

	public static final String ENTITY_NAME_TEST_VALUE = "Employee";
	public static final Boolean THROW_EXCEPTIONS_TEST_VALUE = Boolean.FALSE;
	public static final String CUSTOMIZER_TEST_VALUE = "acme.sessions.Customizer";

	public CustomizationValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.customization = this.subject.getCustomization(); // Subject
		PropertyValueModel<EclipseLinkCustomization> customizationHolder = new SimplePropertyValueModel<EclipseLinkCustomization>(this.customization);
		
		this.throwExceptionsHolder = this.buildThrowExceptionsAA(customizationHolder);
		this.throwExceptionsListener = this.buildThrowExceptionsChangeListener();
		this.throwExceptionsHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.throwExceptionsListener);
		this.throwExceptionsEvent = null;
	}

	public void testHasListeners() {
		AbstractModel subjectCustomization = (AbstractModel) this.customization; // Subject
		
		AbstractModel throwExceptionsAA = (AbstractModel) this.throwExceptionsHolder;
		assertTrue(throwExceptionsAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(subjectCustomization.hasAnyPropertyChangeListeners(EclipseLinkCustomization.THROW_EXCEPTIONS_PROPERTY));
		
		throwExceptionsAA.removePropertyChangeListener(PropertyValueModel.VALUE, this.throwExceptionsListener);
		assertFalse(subjectCustomization.hasAnyPropertyChangeListeners(EclipseLinkCustomization.THROW_EXCEPTIONS_PROPERTY));
		assertFalse(throwExceptionsAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	/**
	 * Initializes directly the PU properties before testing. 
	 */
	@Override
	protected void populatePu() {
		this.persistenceUnitSetProperty(
			EclipseLinkCustomization.ECLIPSELINK_THROW_EXCEPTIONS, 
			THROW_EXCEPTIONS_TEST_VALUE);
		return;
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.customization;
	}

	// ****** ThrowExceptions *******
	private ModifiablePropertyValueModel<Boolean> buildThrowExceptionsAA(PropertyValueModel<EclipseLinkCustomization> subjectModel) {
		return PropertyValueModelTools.modifiableModelAspectAdapter(
				subjectModel,
				EclipseLinkCustomization.THROW_EXCEPTIONS_PROPERTY,
				EclipseLinkCustomization.THROW_EXCEPTIONS_TRANSFORMER,
				EclipseLinkCustomization.SET_THROW_EXCEPTIONS_CLOSURE
			);
	}

	private PropertyChangeListener buildThrowExceptionsChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				CustomizationValueModelTests.this.throwExceptionsEvent = e;
			}
		};
	}

	/** ****** Basic Entity's Properties Tests ******* */

	public void testClone() {
		EclipseLinkCustomizationEntity entity = this.buildEntity("TestEntity", CUSTOMIZER_TEST_VALUE);

		this.verifyClone(entity, entity.clone());
	}
	
	public void testEquals() {
		EclipseLinkCustomizationEntity entity1 = this.buildEntity("TestEntityA", CUSTOMIZER_TEST_VALUE);
		EclipseLinkCustomizationEntity entity2 = this.buildEntity("TestEntityB", CUSTOMIZER_TEST_VALUE);
		assertEquals(entity1, entity2);
		EclipseLinkCustomizationEntity entity3 = this.buildEntity("TestEntityC", CUSTOMIZER_TEST_VALUE);
		assertEquals(entity1, entity3);
		assertEquals(entity2, entity3);
	}
	
	public void testIsEmpty() {
		EclipseLinkCustomizationEntity entity = this.buildEntity("TestEntity");
		assertTrue(entity.isEmpty());
		this.customization.setDescriptorCustomizerOf(entity.getName(), CUSTOMIZER_TEST_VALUE);
		assertFalse(entity.isEmpty());
	}

	private void verifyClone(EclipseLinkCustomizationEntity original, EclipseLinkCustomizationEntity clone) {
		assertNotSame(original, clone);
		assertEquals(original, original);
		assertEquals(original, clone);
	}

	private EclipseLinkCustomizationEntity buildEntity(String name) {
		return this.customization.addEntity(name);
	}

	private EclipseLinkCustomizationEntity buildEntity(String name, String aClassName) {
		EclipseLinkCustomizationEntity entity = this.customization.addEntity(name);
		this.customization.setDescriptorCustomizerOf(entity.getName(), aClassName);
		return entity;
	}

	// ****** Tests ******* 
	public void testValue() {
		// ****** ThrowExceptions ******* 
		this.verifyThrowExceptionsAAValue(THROW_EXCEPTIONS_TEST_VALUE);
		assertEquals(EclipseLinkCustomization.DEFAULT_THROW_EXCEPTIONS, this.customization.getDefaultThrowExceptions());
	}

	public void testSetValue() throws Exception {
		// ****** ThrowExceptions ******* 
		this.throwExceptionsEvent = null;
		this.verifyHasListeners(this.throwExceptionsHolder, PropertyValueModel.VALUE);
		Boolean newThrowExceptions = !THROW_EXCEPTIONS_TEST_VALUE;
		// Modify the property holder
		this.throwExceptionsHolder.setValue(newThrowExceptions);
		this.verifyThrowExceptionsAAValue(newThrowExceptions);
		assertNotNull(this.throwExceptionsEvent);
	}

	public void testSetNullValue() {
		String notDeleted = "Property not deleted";
		// ****** ThrowExceptions *******
		this.throwExceptionsEvent = null;
		// Setting the property holder
		this.throwExceptionsHolder.setValue(null);
		// testing Holder
		this.verifyThrowExceptionsAAValue(null);
		assertNotNull(this.throwExceptionsEvent);
		// testing PU properties
		this.verifyPuHasNotProperty(EclipseLinkCustomization.ECLIPSELINK_THROW_EXCEPTIONS, notDeleted);
	}

	// ****** convenience methods *******

	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifyThrowExceptionsAAValue(Boolean testValue) {
		this.verifyAAValue(
			testValue, 
			this.customization.getThrowExceptions(), 
			this.throwExceptionsHolder, 
			EclipseLinkCustomization.ECLIPSELINK_THROW_EXCEPTIONS);
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
