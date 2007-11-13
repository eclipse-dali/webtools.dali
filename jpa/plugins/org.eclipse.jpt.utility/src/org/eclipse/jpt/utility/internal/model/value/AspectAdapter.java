/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;

/**
 * This abstract extension of AbstractModel provides a base for adding 
 * change listeners (PropertyChange, CollectionChange, ListChange, TreeChange)
 * to a subject and converting the subject's change notifications into a single
 * set of change notifications for a common aspect (e.g. VALUE).
 * 
 * The adapter will only listen to the subject (and subject holder) when the
 * adapter itself actually has listeners. This will allow the adapter to be
 * garbage collected when appropriate
 */
public abstract class AspectAdapter 
	extends AbstractModel
{
	/**
	 * The subject that holds the aspect and fires
	 * change notification when the aspect changes.
	 * We need to hold on to this directly so we can
	 * disengage it when it changes.
	 */
	protected Object subject;

	/**
	 * A value model that holds the subject
	 * that holds the aspect and provides change notification.
	 * This is useful when there are a number of AspectAdapters
	 * that have the same subject and that subject can change.
	 * All the AspectAdapters should share the same subject holder.
	 * For now, this is can only be set upon construction and is
	 * immutable.
	 */
	protected final ValueModel subjectHolder;

	/** A listener that keeps us in synch with the subjectHolder. */
	protected final PropertyChangeListener subjectChangeListener;


	// ********** constructors **********

	/**
	 * Construct an AspectAdapter for the specified subject.
	 */
	protected AspectAdapter(Object subject) {
		this(new ReadOnlyPropertyValueModel(subject));
	}

	/**
	 * Construct an AspectAdapter for the specified subject holder.
	 * The subject holder cannot be null.
	 */
	protected AspectAdapter(ValueModel subjectHolder) {
		super();
		if (subjectHolder == null) {
			throw new NullPointerException();
		}
		this.subjectHolder = subjectHolder;
		this.subjectChangeListener = this.buildSubjectChangeListener();
		// the subject is null when we are not listening to it
		// this will typically result in our value being null
		this.subject = null;
	}


	// ********** initialization **********

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new LocalChangeSupport(this, this.listenerAspectName());
	}

	/**
	 * The subject holder's value has changed, keep our subject in synch.
	 */
	protected PropertyChangeListener buildSubjectChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				AspectAdapter.this.subjectChanged();
			}
			@Override
			public String toString() {
				return "subject change listener";
			}
		};
	}


	// ********** behavior **********

	/**
	 * The subject has changed. Notify listeners that the value has changed.
	 */
	protected synchronized void subjectChanged() {
		Object oldValue = this.value();
		boolean hasListeners = this.hasListeners();
		if (hasListeners) {
			this.disengageSubject();
		}
		this.subject = this.subjectHolder.value();
		if (hasListeners) {
			this.engageSubject();
			this.fireAspectChange(oldValue, this.value());
		}
	}

	/**
	 * Return the aspect's current value.
	 */
	protected abstract Object value();

	/**
	 * Return the class of listener that is interested in the aspect adapter's
	 * changes.
	 */
	protected abstract Class<? extends ChangeListener> listenerClass();

	/**
	 * Return the name of the aspect adapter's aspect (e.g. VALUE).
	 * This is the name of the aspect adapter's single aspect, not the
	 * name of the subject's aspect the aspect adapter is adapting.
	 */
	protected abstract String listenerAspectName();

	/**
	 * Return whether there are any listeners for the aspect.
	 */
	protected abstract boolean hasListeners();

	/**
	 * Return whether there are no listeners for the aspect.
	 */
	protected boolean hasNoListeners() {
		return ! this.hasListeners();
	}

	/**
	 * The aspect has changed, notify listeners appropriately.
	 */
	protected abstract void fireAspectChange(Object oldValue, Object newValue);

	/**
	 * The subject is not null - add our listener.
	 */
	protected abstract void engageNonNullSubject();

	protected void engageSubject() {
		// check for nothing to listen to
		if (this.subject != null) {
			this.engageNonNullSubject();
		}
	}

	/**
	 * The subject is not null - remove our listener.
	 */
	protected abstract void disengageNonNullSubject();

	protected void disengageSubject() {
		// check for nothing to listen to
		if (this.subject != null) {
			this.disengageNonNullSubject();
		}
	}

	protected void engageSubjectHolder() {
		this.subjectHolder.addPropertyChangeListener(ValueModel.VALUE, this.subjectChangeListener);
		// synch our subject *after* we start listening to the subject holder,
		// since its value might change when a listener is added
		this.subject = this.subjectHolder.value();
	}

	protected void disengageSubjectHolder() {
		this.subjectHolder.removePropertyChangeListener(ValueModel.VALUE, this.subjectChangeListener);
		// clear out the subject when we are not listening to its holder
		this.subject = null;
	}

	protected void engageModels() {
		this.engageSubjectHolder();
		this.engageSubject();
	}

	protected void disengageModels() {
		this.disengageSubject();
		this.disengageSubjectHolder();
	}


	// ********** local change support **********

	/**
	 * Extend change support to start listening to the aspect adapter's
	 * models (the subject holder and the subject itself) when the first
	 * relevant listener is added.
	 * Conversely, stop listening to the aspect adapter's models when the
	 * last relevant listener is removed.
	 * A relevant listener is a listener of the relevant type.
	 */
	protected class LocalChangeSupport extends SingleAspectChangeSupport {
		private static final long serialVersionUID = 1L;

		public LocalChangeSupport(AspectAdapter source, String aspectName) {
			super(source, aspectName);
		}

		protected boolean listenerIsRelevant(Class<? extends ChangeListener> listenerClass) {
			return listenerClass == AspectAdapter.this.listenerClass();
		}

		protected boolean hasNoRelevantListeners(Class<? extends ChangeListener> listenerClass) {
			return this.listenerIsRelevant(listenerClass)
						&& this.hasNoListeners(listenerClass);
		}

		protected boolean listenerIsRelevant(Class<? extends ChangeListener> listenerClass, String listenerAspectName) {
			return this.listenerIsRelevant(listenerClass)
						&& (listenerAspectName == AspectAdapter.this.listenerAspectName());
		}

		protected boolean hasNoRelevantListeners(Class<? extends ChangeListener> listenerClass, String listenerAspectName) {
			return this.listenerIsRelevant(listenerClass, listenerAspectName)
						&& this.hasNoListeners(listenerClass, listenerAspectName);
		}


		// ********** overrides **********

		@Override
		protected <T extends ChangeListener> void addListener(Class<T> listenerClass, T listener) {
			if (this.hasNoRelevantListeners(listenerClass)) {
				AspectAdapter.this.engageModels();
			}
			super.addListener(listenerClass, listener);
		}

		@Override
		protected <T extends ChangeListener> void addListener(String listenerAspectName, Class<T> listenerClass, T listener) {
			if (this.hasNoRelevantListeners(listenerClass, listenerAspectName)) {
				AspectAdapter.this.engageModels();
			}
			super.addListener(listenerAspectName, listenerClass, listener);
		}

		@Override
		protected <T extends ChangeListener> void removeListener(Class<T> listenerClass, T listener) {
			super.removeListener(listenerClass, listener);
			if (this.hasNoRelevantListeners(listenerClass)) {
				AspectAdapter.this.disengageModels();
			}
		}

		@Override
		protected <T extends ChangeListener> void removeListener(String listenerAspectName, Class<T> listenerClass, T listener) {
			super.removeListener(listenerAspectName, listenerClass, listener);
			if (this.hasNoRelevantListeners(listenerClass, listenerAspectName)) {
				AspectAdapter.this.disengageModels();
			}
		}

	}

}
