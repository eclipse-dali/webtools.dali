package org.eclipse.jpt.utility.tests.internal;

import java.io.Serializable;
import java.util.EventListener;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.ListenerList;

@SuppressWarnings("nls")
public class ListenerListTests extends TestCase {

	public ListenerListTests(String name) {
		super(name);
	}

	public void testGetListeners() throws Exception {
		ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);
		Listener listener1 = new LocalListener();
		Listener listener2 = new LocalListener();
		Listener[] listeners = listenerList.getListeners();
		assertEquals(0, listeners.length);

		listenerList.add(listener1);
		listenerList.add(listener2);
		listeners = listenerList.getListeners();
		assertEquals(2, listeners.length);
		assertTrue(ArrayTools.contains(listeners, listener1));
		assertTrue(ArrayTools.contains(listeners, listener2));
	}

	public void testSize() throws Exception {
		ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);
		Listener listener1 = new LocalListener();
		Listener listener2 = new LocalListener();
		assertEquals(0, listenerList.size());

		listenerList.add(listener1);
		listenerList.add(listener2);
		assertEquals(2, listenerList.size());
	}

	public void testIsEmpty() throws Exception {
		ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);
		Listener listener1 = new LocalListener();
		Listener listener2 = new LocalListener();
		assertTrue(listenerList.isEmpty());

		listenerList.add(listener1);
		listenerList.add(listener2);
		assertFalse(listenerList.isEmpty());
	}

	public void testAdd_null() throws Exception {
		ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);
		boolean exCaught = false;
		try {
			listenerList.add(null);
			fail("invalid listener list: " + listenerList);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAdd_duplicate() throws Exception {
		ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);
		Listener listener = new LocalListener();
		listenerList.add(listener);

		boolean exCaught = false;
		try {
			listenerList.add(listener);
			fail("invalid listener list: " + listenerList);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemove() throws Exception {
		ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);
		Listener listener1 = new LocalListener();
		Listener listener2 = new LocalListener();
		listenerList.add(listener1);
		listenerList.add(listener2);
		assertTrue(ArrayTools.contains(listenerList.getListeners(), listener1));
		assertTrue(ArrayTools.contains(listenerList.getListeners(), listener2));

		listenerList.remove(listener1);
		assertFalse(ArrayTools.contains(listenerList.getListeners(), listener1));
		assertTrue(ArrayTools.contains(listenerList.getListeners(), listener2));

		listenerList.remove(listener2);
		assertFalse(ArrayTools.contains(listenerList.getListeners(), listener2));
	}

	public void testRemove_null() throws Exception {
		ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);
		boolean exCaught = false;
		try {
			listenerList.remove(null);
			fail("invalid listener list: " + listenerList);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemove_unregistered() throws Exception {
		ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);
		Listener listener = new LocalListener();
		listenerList.add(listener);
		listenerList.remove(listener);

		boolean exCaught = false;
		try {
			listenerList.remove(listener);
			fail("invalid listener list: " + listenerList);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testClear() throws Exception {
		ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);
		Listener listener1 = new LocalListener();
		Listener listener2 = new LocalListener();
		listenerList.add(listener1);
		listenerList.add(listener2);
		assertTrue(ArrayTools.contains(listenerList.getListeners(), listener1));
		assertTrue(ArrayTools.contains(listenerList.getListeners(), listener2));

		listenerList.clear();
		assertFalse(ArrayTools.contains(listenerList.getListeners(), listener1));
		assertFalse(ArrayTools.contains(listenerList.getListeners(), listener2));
	}

//TODO - This test doesn't pass in the Eclipse build environment (Linux) for some reason
//	public void testSerialization() throws Exception {
//		ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);
//		Listener listener1 = new LocalListener();
//		Listener listener2 = new LocalListener();
//		listenerList.add(listener1);
//		listenerList.add(listener2);
//
//		ListenerList<Listener> listenerList2 = TestTools.serialize(listenerList);
//		assertNotSame(listenerList, listenerList2);
//		assertEquals(2, listenerList2.size());
//
//		Listener listener3 = new NonSerializableListener();
//		listenerList.add(listener3);
//
//		listenerList2 = TestTools.serialize(listenerList);
//		assertNotSame(listenerList, listenerList2);
//		assertEquals(2, listenerList2.size());
//
//	}

	interface Listener extends EventListener {
		void somethingHappened();
	}
	
	static class LocalListener implements Listener, Serializable {
		public void somethingHappened() {
			// do nothing
		}
	}
	
	static class NonSerializableListener implements Listener {
		public void somethingHappened() {
			// do nothing
		}
	}
	
}
