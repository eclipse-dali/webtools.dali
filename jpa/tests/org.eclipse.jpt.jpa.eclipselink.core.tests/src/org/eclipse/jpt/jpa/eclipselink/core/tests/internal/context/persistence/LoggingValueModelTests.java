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
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging;

/**
 * LoggingValueModelTests
 */
@SuppressWarnings("nls")
public class LoggingValueModelTests extends EclipseLinkPersistenceUnitTestCase
{
	private EclipseLinkLogging logging;

	private ModifiablePropertyValueModel<Boolean> timestampHolder;
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
		PropertyValueModel<EclipseLinkLogging> loggingHolder = new SimplePropertyValueModel<EclipseLinkLogging>(this.logging);
		
		this.timestampHolder = this.buildTimestampAA(loggingHolder);
		this.timestampListener = this.buildTimestampChangeListener();
		this.timestampHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.timestampListener);
		this.timestampEvent = null;
	}

	public void testHasListeners() {
		AbstractModel subjectLogging = (AbstractModel) this.logging; // Subject
		
		AbstractModel timestampAA = (AbstractModel) this.timestampHolder;
		assertTrue(timestampAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(subjectLogging.hasAnyPropertyChangeListeners(EclipseLinkLogging.TIMESTAMP_PROPERTY));
		
		timestampAA.removePropertyChangeListener(PropertyValueModel.VALUE, this.timestampListener);
		assertFalse(subjectLogging.hasAnyPropertyChangeListeners(EclipseLinkLogging.TIMESTAMP_PROPERTY));
		assertFalse(timestampAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	/**
	 * Initializes directly the PU properties before testing. 
	 */
	@Override
	protected void populatePu() {
		this.persistenceUnitSetProperty(
			EclipseLinkLogging.ECLIPSELINK_TIMESTAMP, 
			TIMESTAMP_TEST_VALUE);
		return;
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.logging;
	}

	// ****** Timestamp *******
	private ModifiablePropertyValueModel<Boolean> buildTimestampAA(PropertyValueModel<EclipseLinkLogging> subjectModel) {
		return PropertyValueModelTools.modifiableModelAspectAdapter(
				subjectModel,
				EclipseLinkLogging.TIMESTAMP_PROPERTY,
				EclipseLinkLogging.TIMESTAMP_TRANSFORMER,
				EclipseLinkLogging.SET_TIMESTAMP_CLOSURE
			);
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
		assertEquals(EclipseLinkLogging.DEFAULT_TIMESTAMP, this.logging.getDefaultTimestamp());
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
		this.verifyPuHasNotProperty(EclipseLinkLogging.ECLIPSELINK_TIMESTAMP, notDeleted);
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
			EclipseLinkLogging.ECLIPSELINK_TIMESTAMP);
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
