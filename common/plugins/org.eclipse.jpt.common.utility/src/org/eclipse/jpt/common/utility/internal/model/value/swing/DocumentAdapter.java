/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.swing;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.EventObject;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;
import javax.swing.text.Segment;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.listener.awt.AWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * This javax.swing.text.Document can be used to keep a DocumentListener
 * (e.g. a JTextField) in synch with a PropertyValueModel that holds a string.
 * 
 * NB: This model should only be used for "small" documents;
 * i.e. documents used by text fields, not text panes.
 * @see #synchronizeDelegate(String)
 */
public class DocumentAdapter
	implements Document, Serializable
{
	/** The delegate document whose behavior we "enhance". */
	protected final Document delegate;

	/** A listener that allows us to forward any changes made to the delegate document. */
	protected final CombinedListener delegateListener;

	/** A value model on the underlying model string. */
	protected final ModifiablePropertyValueModel<String> stringHolder;

	/** A listener that allows us to synchronize with changes made to the underlying model string. */
	protected transient PropertyChangeListener stringListener;

    /** The event listener list for the document. */
    protected final EventListenerList listenerList = new EventListenerList();

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Constructor - the string holder is required.
	 * Wrap the specified document.
	 */
	public DocumentAdapter(ModifiablePropertyValueModel<String> stringHolder, Document delegate) {
		super();
		if (stringHolder == null || delegate == null) {
			throw new NullPointerException();
		}
		this.stringHolder = stringHolder;
		// postpone listening to the underlying model string
		// until we have listeners ourselves...
		this.delegate = delegate;
		this.stringListener = this.buildStringListener();
		this.delegateListener = this.buildDelegateListener();
	}

	/**
	 * Constructor - the string holder is required.
	 * Wrap a plain document.
	 */
	public DocumentAdapter(ModifiablePropertyValueModel<String> stringHolder) {
		this(stringHolder, new PlainDocument());
	}


	// ********** initialization **********

	protected PropertyChangeListener buildStringListener() {
		return new AWTPropertyChangeListenerWrapper(this.buildStringListener_());
	}

	protected PropertyChangeListener buildStringListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				DocumentAdapter.this.stringChanged(event);
			}
			@Override
			public String toString() {
				return "string listener"; //$NON-NLS-1$
			}
		};
	}

	protected CombinedListener buildDelegateListener() {
		return new InternalListener();
	}


	// ********** Document implementation **********

	public int getLength() {
		return this.delegate.getLength();
	}

	/**
	 * Extend to start listening to the underlying models if necessary.
	 */
	public void addDocumentListener(DocumentListener listener) {
		if (this.listenerList.getListenerCount(DocumentListener.class) == 0) {
			this.delegate.addDocumentListener(this.delegateListener);
			this.engageStringHolder();
		}
		this.listenerList.add(DocumentListener.class, listener);
	}

	/**
	 * Extend to stop listening to the underlying models if appropriate.
	 */
	public void removeDocumentListener(DocumentListener listener) {
		this.listenerList.remove(DocumentListener.class, listener);
		if (this.listenerList.getListenerCount(DocumentListener.class) == 0) {
			this.disengageStringHolder();
			this.delegate.removeDocumentListener(this.delegateListener);
		}
	}

	/**
	 * Extend to start listening to the delegate document if necessary.
	 */
	public void addUndoableEditListener(UndoableEditListener listener) {
		if (this.listenerList.getListenerCount(UndoableEditListener.class) == 0) {
			this.delegate.addUndoableEditListener(this.delegateListener);
		}
		this.listenerList.add(UndoableEditListener.class, listener);
	}

	/**
	 * Extend to stop listening to the delegate document if appropriate.
	 */
	public void removeUndoableEditListener(UndoableEditListener listener) {
		this.listenerList.remove(UndoableEditListener.class, listener);
		if (this.listenerList.getListenerCount(UndoableEditListener.class) == 0) {
			this.delegate.removeUndoableEditListener(this.delegateListener);
		}
	}

	public Object getProperty(Object key) {
		return this.delegate.getProperty(key);
	}

	public void putProperty(Object key, Object value) {
		this.delegate.putProperty(key, value);
	}

	/**
	 * Extend to update the underlying model string directly.
	 * The resulting event will be ignored: @see #synchronizeDelegate(String).
	 */
	public void remove(int offset, int len) throws BadLocationException {
		this.delegate.remove(offset, len);
		this.stringHolder.setValue(this.delegate.getText(0, this.delegate.getLength()));
	}

	/**
	 * Extend to update the underlying model string directly.
	 * The resulting event will be ignored: @see #synchronizeDelegate(String).
	 */
	public void insertString(int offset, String insertedString, AttributeSet a) throws BadLocationException {
		this.delegate.insertString(offset, insertedString, a);
		this.stringHolder.setValue(this.delegate.getText(0, this.delegate.getLength()));
	}

	public String getText(int offset, int length) throws BadLocationException {
		return this.delegate.getText(offset, length);
	}

	public void getText(int offset, int length, Segment txt) throws BadLocationException {
		this.delegate.getText(offset, length, txt);
	}

	public Position getStartPosition() {
		return this.delegate.getStartPosition();
	}

	public Position getEndPosition() {
		return this.delegate.getEndPosition();
	}

	public Position createPosition(int offs) throws BadLocationException {
		return this.delegate.createPosition(offs);
	}

	public Element[] getRootElements() {
		return this.delegate.getRootElements();
	}

	public Element getDefaultRootElement() {
		return this.delegate.getDefaultRootElement();
	}

	public void render(Runnable r) {
		this.delegate.render(r);
	}


	// ********** queries **********

	public DocumentListener[] documentListeners() {
		return this.listenerList.getListeners(DocumentListener.class);
	}

	public UndoableEditListener[] undoableEditListeners() {
		return this.listenerList.getListeners(UndoableEditListener.class);
	}


	// ********** behavior **********

	/**
	 * A third party has modified the underlying model string.
	 * Synchronize the delegate document accordingly.
	 */
	protected void stringChanged(PropertyChangeEvent event) {
		this.synchronizeDelegate((String) event.getNewValue());
	}

	/**
	 * Replace the document's entire text string with the new string.
	 */
	protected void synchronizeDelegate(String s) {
		try {
			int len = this.delegate.getLength();
			// check to see whether the delegate has already been synchronized
			// (via #insertString() or #remove())
			if ( ! this.delegate.getText(0, len).equals(s)) {
				this.delegate.remove(0, len);
				this.delegate.insertString(0, s, null);
			}
		} catch (BadLocationException ex) {
			throw new IllegalStateException(ex.getMessage());	// this should not happen...
		}
	}

	protected void engageStringHolder() {
		this.stringHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.stringListener);
		this.synchronizeDelegate(this.stringHolder.getValue());
	}

	protected void disengageStringHolder() {
		this.stringHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.stringListener);
	}

	protected void delegateChangedUpdate(DocumentEvent event) {
		// no need to lazy-initialize the event;
		// we wouldn't get here if we did not have listeners...
		DocumentEvent ee = new InternalDocumentEvent(this, event);
		DocumentListener[] listeners = this.documentListeners();
		for (int i = listeners.length; i-- > 0; ) {
			listeners[i].changedUpdate(ee);
		}
	}

	protected void delegateInsertUpdate(DocumentEvent event) {
		// no need to lazy-initialize the event;
		// we wouldn't get here if we did not have listeners...
		DocumentEvent ee = new InternalDocumentEvent(this, event);
		DocumentListener[] listeners = this.documentListeners();
		for (int i = listeners.length; i-- > 0; ) {
			listeners[i].insertUpdate(ee);
		}
	}

	protected void delegateRemoveUpdate(DocumentEvent event) {
		// no need to lazy-initialize the event;
		// we wouldn't get here if we did not have listeners...
		DocumentEvent ee = new InternalDocumentEvent(this, event);
		DocumentListener[] listeners = this.documentListeners();
		for (int i = listeners.length; i-- > 0; ) {
			listeners[i].removeUpdate(ee);
		}
	}

	protected void delegateUndoableEditHappened(UndoableEditEvent event) {
		// no need to lazy-initialize the event;
		// we wouldn't get here if we did not have listeners...
		UndoableEditEvent ee = new UndoableEditEvent(this, event.getEdit());
		UndoableEditListener[] listeners = this.undoableEditListeners();
		for (int i = listeners.length; i-- > 0; ) {
			listeners[i].undoableEditHappened(ee);
		}
	}

	// ********** standard methods **********

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.stringHolder);
	}

	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		// read in any hidden stuff
		s.defaultReadObject();
		this.stringListener = this.buildStringListener();
	}


// ********** inner class **********

	protected interface CombinedListener extends DocumentListener, UndoableEditListener, Serializable {
		// just consolidate the two interfaces
	}

	protected class InternalListener implements CombinedListener {
		private static final long serialVersionUID = 1L;
		public void changedUpdate(DocumentEvent event) {
			DocumentAdapter.this.delegateChangedUpdate(event);
		}
		public void insertUpdate(DocumentEvent event) {
			DocumentAdapter.this.delegateInsertUpdate(event);
		}
		public void removeUpdate(DocumentEvent event) {
			DocumentAdapter.this.delegateRemoveUpdate(event);
		}
		public void undoableEditHappened(UndoableEditEvent event) {
			DocumentAdapter.this.delegateUndoableEditHappened(event);
		}
	}
	
	protected static class InternalDocumentEvent
		extends EventObject
		implements DocumentEvent
	{
		protected DocumentEvent delegate;
	
		private static final long serialVersionUID = 1L;

		protected InternalDocumentEvent(Document document, DocumentEvent delegate) {
			super(document);
			this.delegate = delegate;
		}
		public ElementChange getChange(Element elem) {
			return this.delegate.getChange(elem);
		}
		public Document getDocument() {
			return (Document) this.source;
		}
		public int getLength() {
			return this.delegate.getLength();
		}
		public int getOffset() {
			return this.delegate.getOffset();
		}
		public EventType getType() {
			return this.delegate.getType();
		}
	}

}
