/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.logging;

import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.EclipseLinkPersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * LoggingValueModelTests
 */
@SuppressWarnings("nls")
public class LoggingValueModelTests extends EclipseLinkPersistenceUnitTestCase
{
	private Logging logging;

	private WritablePropertyValueModel<Boolean> timestampHolder;
	private PropertyChangeListener timestampListener;
	private PropertyChangeEvent timestampEvent;

	public static final Boolean TIMESTAMP_TEST_VALUE = Boolean.FALSE;

	public LoggingValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.logging = this.subject.getLogging(); // Subject
		PropertyValueModel<Logging> loggingHolder = new SimplePropertyValueModel<Logging>(this.logging);
		
		this.timestampHolder = this.buildTimestampAA(loggingHolder);
		this.timestampListener = this.buildTimestampChangeListener();
		this.timestampHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.timestampListener);
		this.timestampEvent = null;
	}

	public void testHasListeners() {
		AbstractModel subjectLogging = (AbstractModel) this.logging; // Subject
		
		PropertyAspectAdapter<Logging, Boolean> timestampAA = 
			(PropertyAspectAdapter<Logging, Boolean>) this.timestampHolder;
		assertTrue(timestampAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(subjectLogging.hasAnyPropertyChangeListeners(Logging.TIMESTAMP_PROPERTY));
		
		timestampAA.removePropertyChangeListener(PropertyValueModel.VALUE, this.timestampListener);
		assertFalse(subjectLogging.hasAnyPropertyChangeListeners(Logging.TIMESTAMP_PROPERTY));
		assertFalse(timestampAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	/**
	 * Initializes directly the PU properties before testing. 
	 */
	@Override
	protected void populatePu() {
		this.persistenceUnitSetProperty(
			Logging.ECLIPSELINK_TIMESTAMP, 
			TIMESTAMP_TEST_VALUE);
		return;
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.logging;
	}

	// ****** Timestamp *******
	private WritablePropertyValueModel<Boolean> buildTimestampAA(PropertyValueModel<Logging> subjectHolder) {
		return new PropertyAspectAdapter<Logging, Boolean>(subjectHolder, Logging.TIMESTAMP_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getTimestamp();
			}

			@Override
			protected void setValue_(Boolean enumValue) {
				this.subject.setTimestamp(enumValue);
			}
		};
	}

	private PropertyChangeListener buildTimestampChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				LoggingValueModelTests.this.timestampEvent = e;
			}
		};
	}

	// ****** Tests ******* 
	public void testValue() {
		// ****** Timestamp ******* 
		this.verifyTimestampAAValue(TIMESTAMP_TEST_VALUE);
		assertEquals(Logging.DEFAULT_TIMESTAMP, this.logging.getDefaultTimestamp());
	}

	public void testSetValue() throws Exception {
		// ****** Timestamp ******* 
		this.timestampEvent = null;
		this.verifyHasListeners(this.timestampHolder, PropertyValueModel.VALUE);
		Boolean newTimestamp = !TIMESTAMP_TEST_VALUE;
		// Modify the property holder
		this.timestampHolder.setValue(newTimestamp);
		this.verifyTimestampAAValue(newTimestamp);
		assertNotNull(this.timestampEvent);
	}

	public void testSetNullValue() {
		String notDeleted = "Property not deleted";
		// ****** Timestamp *******
		this.timestampEvent = null;
		// Setting the property holder
		this.timestampHolder.setValue(null);
		// testing Holder
		this.verifyTimestampAAValue(null);
		assertNotNull(this.timestampEvent);
		// testing PU properties
		this.verifyPuHasNotProperty(Logging.ECLIPSELINK_TIMESTAMP, notDeleted);
	}

	// ****** convenience methods *******

	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifyTimestampAAValue(Boolean testValue) {
		this.verifyAAValue(
			testValue, 
			this.logging.getTimestamp(), 
			this.timestampHolder, 
			Logging.ECLIPSELINK_TIMESTAMP);
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
