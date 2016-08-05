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
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkOptions;

/**
 * OptionsValueModelTests
 */
public class OptionsValueModelTests extends EclipseLinkPersistenceUnitTestCase
{
	private EclipseLinkOptions options;

	private ModifiablePropertyValueModel<Boolean> includeDescriptorQueriesHolder;
	private PropertyChangeListener includeDescriptorQueriesListener;
	private PropertyChangeEvent includeDescriptorQueriesEvent;

	public static final Boolean INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE = Boolean.FALSE;

	public OptionsValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.options = this.subject.getEclipseLinkOptions(); // Subject
		PropertyValueModel<EclipseLinkOptions> optionsHolder = new SimplePropertyValueModel<EclipseLinkOptions>(this.options);
		
		this.includeDescriptorQueriesHolder = this.buildIncludeDescriptorQueriesAA(optionsHolder);
		this.includeDescriptorQueriesListener = this.buildIncludeDescriptorQueriesChangeListener();
		this.includeDescriptorQueriesHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.includeDescriptorQueriesListener);
		this.includeDescriptorQueriesEvent = null;
	}

	public void testHasListeners() {
		AbstractModel subjectOptions = (AbstractModel) this.options; // Subject
		
		AbstractModel includeDescriptorQueriesAA = (AbstractModel) this.includeDescriptorQueriesHolder;
		assertTrue(includeDescriptorQueriesAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(subjectOptions.hasAnyPropertyChangeListeners(EclipseLinkOptions.INCLUDE_DESCRIPTOR_QUERIES_PROPERTY));
		
		includeDescriptorQueriesAA.removePropertyChangeListener(PropertyValueModel.VALUE, this.includeDescriptorQueriesListener);
		assertFalse(subjectOptions.hasAnyPropertyChangeListeners(EclipseLinkOptions.INCLUDE_DESCRIPTOR_QUERIES_PROPERTY));
		assertFalse(includeDescriptorQueriesAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	/**
	 * Initializes directly the PU properties before testing. 
	 */
	@Override
	protected void populatePu() {
		this.persistenceUnitSetProperty(
			EclipseLinkOptions.ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES, 
			INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE);
		return;
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.options;
	}

	// ****** IncludeDescriptorQueries *******
	private ModifiablePropertyValueModel<Boolean> buildIncludeDescriptorQueriesAA(PropertyValueModel<EclipseLinkOptions> subjectModel) {
		return PropertyValueModelTools.modifiableModelAspectAdapter(
				subjectModel,
				EclipseLinkOptions.INCLUDE_DESCRIPTOR_QUERIES_PROPERTY,
				EclipseLinkOptions.INCLUDE_DESCRIPTOR_QUERIES_TRANSFORMER,
				EclipseLinkOptions.SET_INCLUDE_DESCRIPTOR_QUERIES_CLOSURE
			);
	}

	private PropertyChangeListener buildIncludeDescriptorQueriesChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				OptionsValueModelTests.this.includeDescriptorQueriesEvent = e;
			}
		};
	}

	// ****** Tests ******* 
	public void testValue() {
		// ****** IncludeDescriptorQueries ******* 
		this.verifyIncludeDescriptorQueriesAAValue(INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE);
		assertEquals(EclipseLinkOptions.DEFAULT_SESSION_INCLUDE_DESCRIPTOR_QUERIES, this.options.getDefaultIncludeDescriptorQueries());
	}

	public void testSetValue() throws Exception {
		// ****** IncludeDescriptorQueries ******* 
		this.includeDescriptorQueriesEvent = null;
		this.verifyHasListeners(this.includeDescriptorQueriesHolder, PropertyValueModel.VALUE);
		Boolean newIncludeDescriptorQueries = !INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE;
		// Modify the property holder
		this.includeDescriptorQueriesHolder.setValue(newIncludeDescriptorQueries);
		this.verifyIncludeDescriptorQueriesAAValue(newIncludeDescriptorQueries);
		assertNotNull(this.includeDescriptorQueriesEvent);
	}

	public void testSetNullValue() {
		String notDeleted = "Property not deleted";
		// ****** IncludeDescriptorQueries *******
		this.includeDescriptorQueriesEvent = null;
		// Setting the property holder
		this.includeDescriptorQueriesHolder.setValue(null);
		// testing Holder
		this.verifyIncludeDescriptorQueriesAAValue(null);
		assertNotNull(this.includeDescriptorQueriesEvent);
		// testing PU properties
		this.verifyPuHasNotProperty(EclipseLinkOptions.ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES, notDeleted);
	}

	// ****** convenience methods *******

	/**
	 * Performs three value tests:<br>
	 * 1. subject value<br>
	 * 2. aspect adapter value<br>
	 * 3. persistenceUnit property value<br>
	 */
	protected void verifyIncludeDescriptorQueriesAAValue(Boolean testValue) {
		this.verifyAAValue(
			testValue, 
			this.options.getIncludeDescriptorQueries(), 
			this.includeDescriptorQueriesHolder, 
			EclipseLinkOptions.ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES);
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
