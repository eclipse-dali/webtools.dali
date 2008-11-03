/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.options;

import org.eclipse.jpt.eclipselink.core.internal.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.options.Options;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.PersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * OptionsValueModelTests
 */
public class OptionsValueModelTests extends PersistenceUnitTestCase
{
	private Options options;

	private WritablePropertyValueModel<Boolean> includeDescriptorQueriesHolder;
	private PropertyChangeListener includeDescriptorQueriesListener;
	private PropertyChangeEvent includeDescriptorQueriesEvent;

	public static final Boolean INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE = Boolean.FALSE;

	public OptionsValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.options = this.persistenceUnitProperties.getOptions(); // Subject
		PropertyValueModel<Options> optionsHolder = new SimplePropertyValueModel<Options>(this.options);
		
		this.includeDescriptorQueriesHolder = this.buildIncludeDescriptorQueriesAA(optionsHolder);
		this.includeDescriptorQueriesListener = this.buildIncludeDescriptorQueriesChangeListener();
		this.includeDescriptorQueriesHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.includeDescriptorQueriesListener);
		this.includeDescriptorQueriesEvent = null;
	}

	public void testHasListeners() {
		AbstractModel subjectOptions = (AbstractModel) this.options; // Subject
		
		PropertyAspectAdapter<Options, Boolean> includeDescriptorQueriesAA = 
			(PropertyAspectAdapter<Options, Boolean>) this.includeDescriptorQueriesHolder;
		assertTrue(includeDescriptorQueriesAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(subjectOptions.hasAnyPropertyChangeListeners(Options.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY));
		
		includeDescriptorQueriesAA.removePropertyChangeListener(PropertyValueModel.VALUE, this.includeDescriptorQueriesListener);
		assertFalse(subjectOptions.hasAnyPropertyChangeListeners(Options.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY));
		assertFalse(includeDescriptorQueriesAA.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	/**
	 * Initializes directly the PU properties before testing. 
	 */
	protected void populatePu() {
		this.persistenceUnitPut(
			Options.ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES, 
			INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE);
		return;
	}

	protected PersistenceUnitProperties model() {
		return this.options;
	}

	// ****** IncludeDescriptorQueries *******
	private WritablePropertyValueModel<Boolean> buildIncludeDescriptorQueriesAA(PropertyValueModel<Options> subjectHolder) {
		return new PropertyAspectAdapter<Options, Boolean>(subjectHolder, Options.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getIncludeDescriptorQueries();
			}

			@Override
			protected void setValue_(Boolean enumValue) {
				this.subject.setIncludeDescriptorQueries(enumValue);
			}
		};
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
		assertEquals(Options.DEFAULT_SESSION_INCLUDE_DESCRIPTOR_QUERIES, this.options.getDefaultIncludeDescriptorQueries());
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
		this.verifyPuHasNotProperty(Options.ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES, notDeleted);
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
			Options.ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES);
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
