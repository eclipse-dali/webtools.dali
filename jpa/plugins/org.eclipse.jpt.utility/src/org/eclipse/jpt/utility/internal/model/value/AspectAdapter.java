/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.EventListener;

import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * This abstract extension of {@link AbstractModel} provides a base for adding 
 * change listeners (property change, collection change, list change, tree change)
 * to a subject and converting the subject's change notifications into a single
 * set of change notifications for a common aspect (e.g. <code>VALUE</code>).
 * <p>
 * The adapter will only listen to the subject (and subject holder) when the
 * adapter itself actually has listeners. This will allow the adapter to be
 * garbage collected when appropriate
 */
public abstract class AspectAdapter<S>
	extends AbstractModel
{
	/**
	 * The subject that holds the aspect and fires
	 * change notification when the aspect changes.
	 * We need to hold on to this directly so we can
	 * disengage it when it changes.
	 */
	protected S subject;

	/**
	 * A value model that holds the subject
	 * that holds the aspect and provides change notification.
	 * This is useful when there are a number of aspect adapters
	 * that have the same subject and that subject can change.
	 * All the aspect adapters should share the same subject holder.
	 * For now, this is can only be set upon construction and is
	 * immutable.
	 */
	protected final PropertyValueModel<? extends S> subjectHolder;

	/** A listener that keeps us in synch with the subject holder. */
	protected final PropertyChangeListener subjectChangeListener;


	// ********** constructors **********

	/**
	 * Construct an aspect adapter for the specified subject.
	 */
	protected AspectAdapter(S subject) {
		this(new StaticPropertyValueModel<S>(subject));
	}

	/**
	 * Construct an aspect adapter for the specified subject holder.
	 * The subject holder cannot be null.
	 */
	protected AspectAdapter(PropertyValueModel<? extends S> subjectHolder) {
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
		return new LocalChangeSupport(this, this.getListenerClass(), this.getListenerAspectName());
	}

	/**
	 * The subject holder's value has changed, keep our subject in synch.
	 */
	protected PropertyChangeListener buildSubjectChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				AspectAdapter.this.subjectChanged();
			}
			@Override
			public String toString() {
				return "subject change listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** behavior **********

	/**
	 * The subject has changed. Notify listeners that the value has changed.
	 */
	protected synchronized void subjectChanged() {
		Object oldValue = this.getValue();
		boolean hasListeners = this.hasListeners();
		if (hasListeners) {
			this.disengageSubject();
		}
		this.subject = this.subjectHolder.getValue();
		if (hasListeners) {
			this.engageSubject();
			this.fireAspectChanged(oldValue, this.getValue());
		}
	}

	/**
	 * Return the aspect's current value.
	 */
	protected abstract Object getValue();

	/**
	 * Return the class of listener that is interested in the aspect adapter's
	 * changes.
	 */
	protected abstract Class<? extends EventListener> getListenerClass();

	/**
	 * Return the name of the aspect adapter's aspect (e.g. VALUE).
	 * This is the name of the aspect adapter's single aspect, not the
	 * name of the subject's aspect the aspect adapter is adapting.
	 */
	protected abstract String getListenerAspectName();

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
	protected abstract void fireAspectChanged(Object oldValue, Object newValue);

	protected void engageSubject() {
		// check for nothing to listen to
		if (this.subject != null) {
			this.engageSubject_();
		}
	}

	/**
	 * The subject is not null - add our listener.
	 */
	protected abstract void engageSubject_();

	protected void disengageSubject() {
		// check for nothing to listen to
		if (this.subject != null) {
			this.disengageSubject_();
		}
	}

	/**
	 * The subject is not null - remove our listener.
	 */
	protected abstract void disengageSubject_();

	protected void engageSubjectHolder() {
		this.subjectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.subjectChangeListener);
		// synch our subject *after* we start listening to the subject holder,
		// since its value might change when a listener is added
		this.subject = this.subjectHolder.getValue();
	}

	protected void disengageSubjectHolder() {
		this.subjectHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.subjectChangeListener);
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
	 * A relevant listener is a listener of the relevant type and aspect or a
	 * general-purpose listener.
	 */
	protected class LocalChangeSupport extends SingleAspectChangeSupport {
		private static final long serialVersionUID = 1L;

		public LocalChangeSupport(AspectAdapter<S> source, Class<? extends EventListener> validListenerClass, String validAspectName) {
			super(source, validListenerClass, validAspectName);
		}

		protected boolean hasNoListeners() {
			return this.hasNoListeners(this.validListenerClass, this.validAspectName);
		}


		// ********** overrides **********

		@Override
		protected synchronized <T extends EventListener> void addListener(Class<T> listenerClass, T listener) {
			if (this.hasNoListeners()) {
				AspectAdapter.this.engageModels();
			}
			super.addListener(listenerClass, listener);
		}

		@Override
		protected synchronized <T extends EventListener> void addListener(Class<T> listenerClass, String aspectName, T listener) {
			if (this.hasNoListeners()) {
				AspectAdapter.this.engageModels();
			}
			super.addListener(listenerClass, aspectName, listener);
		}

		@Override
		protected synchronized <T extends EventListener> void removeListener(Class<T> listenerClass, T listener) {
			super.removeListener(listenerClass, listener);
			if (this.hasNoListeners()) {
				AspectAdapter.this.disengageModels();
			}
		}

		@Override
		protected synchronized <T extends EventListener> void removeListener(Class<T> listenerClass, String aspectName, T listener) {
			super.removeListener(listenerClass, aspectName, listener);
			if (this.hasNoListeners()) {
				AspectAdapter.this.disengageModels();
			}
		}

	}

}
