package org.eclipse.jpt.utility.tests.internal.model.value;

import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.ReadOnlyWritablePropertyValueModelWrapper;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

public class ReadOnlyWritablePropertyValueModelWrapperTests 
	extends TestCase
{
	private WritablePropertyValueModel<String> objectHolder;
	
	private PropertyChangeEvent event;
	
	private WritablePropertyValueModel<String> wrapperObjectHolder;
	
	private PropertyChangeEvent wrapperEvent;
	
	
	public ReadOnlyWritablePropertyValueModelWrapperTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.objectHolder = new SimplePropertyValueModel<String>("foo");
		this.wrapperObjectHolder = new ReadOnlyWritablePropertyValueModelWrapper<String>(this.objectHolder);
	}
	
	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}
	
	
	public void testValue() {
		assertEquals("foo", this.objectHolder.getValue());
		assertEquals("foo", this.wrapperObjectHolder.getValue());
		
		this.objectHolder.setValue("bar");
		assertEquals("bar", this.objectHolder.getValue());
		assertEquals("bar", this.wrapperObjectHolder.getValue());
		
		this.objectHolder.setValue(null);
		assertNull(this.objectHolder.getValue());
		assertNull(this.wrapperObjectHolder.getValue());
		
		this.objectHolder.setValue("foo");
		assertEquals("foo", this.objectHolder.getValue());
		assertEquals("foo", this.wrapperObjectHolder.getValue());
	}
	
	public void testSetValue() {
		boolean exceptionOccurred = false;
		try {
			this.wrapperObjectHolder.setValue("bar");
		}
		catch (UnsupportedOperationException uoe) {
			exceptionOccurred = true;
		}
		
		assertTrue(exceptionOccurred);
		assertEquals("foo", this.objectHolder.getValue());
		assertEquals("foo", this.wrapperObjectHolder.getValue());
	}
	
	public void testLazyListening() {
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		PropertyChangeListener listener = buildWrapperListener();
		this.wrapperObjectHolder.addPropertyChangeListener(listener);
		assertTrue(((AbstractModel) this.objectHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.wrapperObjectHolder.removePropertyChangeListener(listener);
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		
		this.wrapperObjectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.objectHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.wrapperObjectHolder.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}
	
	public void testPropertyChange1() {
		this.objectHolder.addPropertyChangeListener(this.buildListener());
		this.wrapperObjectHolder.addPropertyChangeListener(this.buildWrapperListener());
		this.verifyPropertyChanges();
	}
	
	public void testPropertyChange2() {
		this.objectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildListener());
		this.wrapperObjectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildWrapperListener());
		this.verifyPropertyChanges();
	}
	
	private void verifyPropertyChanges() {
		this.event = null;
		this.wrapperEvent = null;
		this.objectHolder.setValue("bar");
		verifyEvent(this.event, this.objectHolder, "foo", "bar");
		verifyEvent(this.wrapperEvent, this.wrapperObjectHolder, "foo", "bar");
		
		this.event = null;
		this.wrapperEvent = null;
		this.objectHolder.setValue(null);
		verifyEvent(this.event, this.objectHolder, "bar", null);
		verifyEvent(this.wrapperEvent, this.wrapperObjectHolder, "bar", null);
		
		this.event = null;
		this.wrapperEvent = null;
		this.objectHolder.setValue("foo");
		verifyEvent(this.event, this.objectHolder, null, "foo");
		verifyEvent(this.wrapperEvent, this.wrapperObjectHolder, null, "foo");
	}

	private PropertyChangeListener buildListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				ReadOnlyWritablePropertyValueModelWrapperTests.this.event = e;
			}
		};
	}

	private PropertyChangeListener buildWrapperListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				ReadOnlyWritablePropertyValueModelWrapperTests.this.wrapperEvent = e;
			}
		};
	}

	private void verifyEvent(PropertyChangeEvent e, Object source, Object oldValue, Object newValue) {
		assertEquals(source, e.getSource());
		assertEquals(PropertyValueModel.VALUE, e.getPropertyName());
		assertEquals(oldValue, e.getOldValue());
		assertEquals(newValue, e.getNewValue());
	}
}
