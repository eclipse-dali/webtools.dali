/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence.customization;

import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.customization.Entity;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence.EclipseLinkPersistenceUnitTestCase;

/**
 * CustomizationValueModelTests
 */
@SuppressWarnings("nls")
public class CustomizationValueModelTests extends EclipseLinkPersistenceUnitTestCase
{
	private Customization customization;

	private WritablePropertyValueModel<Boolean> throwExceptionsHolder;
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
		PropertyValueModel<Customization> customizationHolder = new SimplePropertyValueModel<Customization>(this.customization);
		
		this.throwExceptionsHolder = this.buildThrowExceptionsAA(customizationHolder);
		this.throwExceptionsListener = this.buildThrowExceptionsChangeListener();
		this.throwExceptionsHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.throwExceptionsListener);
		this.throwExceptionsEvent = null;
	}

	public void testHasListeners() {
		AbstractModel subjectCustomization = (AbstractModel) this.customization; // Subject
		
		PropertyAspectAdapter<Customization, Boolean> throwExceptionsAA = 
			(PropertyAspectAdapter<Customization, Boolean>) this.throwExceptionsHolder;
		assertTrue(throwExceptionsAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(subjectCustomization.hasAnyPropertyChangeListeners(Customization.THROW_EXCEPTIONS_PROPERTY));
		
		throwExceptionsAA.removePropertyChangeListener(PropertyValueModel.VALUE, this.throwExceptionsListener);
		assertFalse(subjectCustomization.hasAnyPropertyChangeListeners(Customization.THROW_EXCEPTIONS_PROPERTY));
		assertFalse(throwExceptionsAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	/**
	 * Initializes directly the PU properties before testing. 
	 */
	@Override
	protected void populatePu() {
		this.persistenceUnitSetProperty(
			Customization.ECLIPSELINK_THROW_EXCEPTIONS, 
			THROW_EXCEPTIONS_TEST_VALUE);
		return;
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.customization;
	}

	// ****** ThrowExceptions *******
	private WritablePropertyValueModel<Boolean> buildThrowExceptionsAA(PropertyValueModel<Customization> subjectHolder) {
		return new PropertyAspectAdapter<Customization, Boolean>(subjectHolder, Customization.THROW_EXCEPTIONS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getThrowExceptions();
			}

			@Override
			protected void setValue_(Boolean enumValue) {
				this.subject.setThrowExceptions(enumValue);
			}
		};
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
		Entity entity = this.buildEntity("TestEntity", CUSTOMIZER_TEST_VALUE);

		this.verifyClone(entity, entity.clone());
	}
	
	public void testEquals() {
		Entity entity1 = this.buildEntity("TestEntityA", CUSTOMIZER_TEST_VALUE);
		Entity entity2 = this.buildEntity("TestEntityB", CUSTOMIZER_TEST_VALUE);
		assertEquals(entity1, entity2);
		Entity entity3 = this.buildEntity("TestEntityC", CUSTOMIZER_TEST_VALUE);
		assertEquals(entity1, entity3);
		assertEquals(entity2, entity3);
	}
	
	public void testIsEmpty() {
		Entity entity = this.buildEntity("TestEntity");
		assertTrue(entity.isEmpty());
		this.customization.setDescriptorCustomizerOf(entity.getName(), CUSTOMIZER_TEST_VALUE);
		assertFalse(entity.isEmpty());
	}

	private void verifyClone(Entity original, Entity clone) {
		assertNotSame(original, clone);
		assertEquals(original, original);
		assertEquals(original, clone);
	}

	private Entity buildEntity(String name) {
		return this.customization.addEntity(name);
	}

	private Entity buildEntity(String name, String aClassName) {
		Entity entity = this.customization.addEntity(name);
		this.customization.setDescriptorCustomizerOf(entity.getName(), aClassName);
		return entity;
	}

	// ****** Tests ******* 
	public void testValue() {
		// ****** ThrowExceptions ******* 
		this.verifyThrowExceptionsAAValue(THROW_EXCEPTIONS_TEST_VALUE);
		assertEquals(Customization.DEFAULT_THROW_EXCEPTIONS, this.customization.getDefaultThrowExceptions());
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
		this.verifyPuHasNotProperty(Customization.ECLIPSELINK_THROW_EXCEPTIONS, notDeleted);
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
			Customization.ECLIPSELINK_THROW_EXCEPTIONS);
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
