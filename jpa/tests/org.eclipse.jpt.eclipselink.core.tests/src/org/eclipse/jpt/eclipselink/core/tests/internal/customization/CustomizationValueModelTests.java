/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.customization;

import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.eclipselink.core.tests.internal.PersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * CustomizationValueModelTests
 */
public class CustomizationValueModelTests extends PersistenceUnitTestCase
{
	private Customization customization;

	private WritablePropertyValueModel<Boolean> throwExceptionsHolder;
	private PropertyChangeListener throwExceptionsListener;
	private PropertyChangeEvent throwExceptionsEvent;

	public static final String ENTITY_NAME_TEST_VALUE = "Employee";
	public static final Boolean THROW_EXCEPTIONS_TEST_VALUE = Boolean.FALSE;

	public CustomizationValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.customization = this.persistenceUnitProperties.getCustomization(); // Subject
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
	protected void populatePu() {
		this.persistenceUnitPut(
			Customization.ECLIPSELINK_THROW_EXCEPTIONS, 
			THROW_EXCEPTIONS_TEST_VALUE);
		return;
	}

	protected PersistenceUnitProperties model() {
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
	
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		// do nothing
	}

	protected  void verifyPutProperty(String propertyName, Object expectedValue) throws Exception {
		// do nothing
	}
}
