/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.EventListener;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.exception.DefaultExceptionHandler;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This abstract extension of {@link AbstractModel} provides a base for adding 
 * change listeners (property change, collection change, list change, tree change)
 * to a subject and converting the subject's change notifications into a single
 * set of change notifications for a common aspect
 * (e.g. {@link PropertyValueModel#VALUE}).
 * <p>
 * The adapter will only listen to the subject (and subject model) when the
 * adapter itself actually has listeners. This will allow the adapter to be
 * garbage collected when appropriate.
 * 
 * @param <S> the type of the model's subject
 * @param <A> the type of the subject's aspect
 */
public abstract class AspectAdapter<S, A>
	extends AbstractModel
{
	/**
	 * The subject that holds the aspect and fires
	 * change notification when the aspect changes.
	 * We need to hold on to this directly so we can
	 * disengage it when it changes.
	 */
	protected volatile S subject;

	/**
	 * A value model that holds the {@link #subject}
	 * that holds the aspect and provides change notification.
	 * This is useful when there are a number of aspect adapters
	 * that have the same subject and that subject can change.
	 * All the aspect adapters can share the same subject model.
	 * For now, this is can only be set upon construction and is
	 * immutable.
	 */
	protected final PropertyValueModel<? extends S> subjectModel;

	/** A listener that keeps us in sync with the subject model. */
	protected final PropertyChangeListener subjectListener;


	// ********** constructors **********

	/**
	 * Construct an aspect adapter for the specified subject.
	 */
	protected AspectAdapter(S subject) {
		this(PropertyValueModelTools.staticModel(subject));
	}

	/**
	 * Construct an aspect adapter for the specified subject model.
	 * The subject model cannot be <code>null</code>.
	 */
	protected AspectAdapter(PropertyValueModel<? extends S> subjectModel) {
		super();
		if (subjectModel == null) {
			throw new NullPointerException();
		}
		this.subjectModel = subjectModel;
		this.subjectListener = this.buildSubjectListener();
		// the subject is null when we are not listening to it
		// this will typically result in our value being null
		this.subject = null;
	}


	// ********** initialization **********

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new LocalChangeSupport(this, this.getListenerClass(), this.getListenerAspectName());
	}

	protected PropertyChangeListener buildSubjectListener() {
		return new SubjectListener();
	}

	protected class SubjectListener
		extends PropertyChangeAdapter
	{
		/**
		 * The subject model's value has changed, keep our subject in sync.
		 */
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			AspectAdapter.this.subjectChanged();
		}
	}


	// ********** behavior **********

	/**
	 * The subject has changed.
	 * Move our subject listener and
	 * notify listeners that the value has changed.
	 */
	protected synchronized void subjectChanged() {
		A old = this.getAspectValue();
		boolean hasListeners = this.hasListeners();
		if (hasListeners) {
			this.disengageSubject();
		}
		this.subject = this.subjectModel.getValue();
		if (hasListeners) {
			this.engageSubject();
			this.fireAspectChanged(old, this.getAspectValue());
		}
	}

	/**
	 * Return the aspect's current value.
	 */
	protected abstract A getAspectValue();

	/**
	 * Return the class of listener that is interested in the aspect adapter's
	 * changes.
	 */
	protected abstract Class<? extends EventListener> getListenerClass();

	/**
	 * Return the name of the aspect adapter's aspect
	 * (e.g. {@link PropertyValueModel#VALUE}).
	 * This is the name of the aspect adapter's single aspect, not the
	 * name of the subject's aspect(s) the aspect adapter is adapting.
	 */
	protected abstract String getListenerAspectName();

	/**
	 * Return whether there are any listeners for the aspect.
	 */
	public abstract boolean hasListeners();

	/**
	 * Return whether there are no listeners for the aspect.
	 */
	public boolean hasNoListeners() {
		return ! this.hasListeners();
	}

	/**
	 * The aspect has changed, notify listeners appropriately.
	 */
	protected abstract void fireAspectChanged(A oldValue, A newValue);


	// ********** engage/disengage models **********

	/**
	 * Called by {@link LocalChangeSupport}
	 */
	protected void engageModels() {
		this.engageSubjectModel();
		this.engageSubject();
	}

	protected void engageSubjectModel() {
		this.subjectModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.subjectListener);
		// sync our subject *after* we start listening to the subject holder,
		// since its value might change when a listener is added
		this.subject = this.subjectModel.getValue();
	}

	protected void engageSubject() {
		// check for nothing to listen to
		if (this.subject != null) {
			this.engageSubject_();
		}
	}

	/**
	 * The {@link #subject} is not <code>null</code> - add our listener.
	 */
	protected abstract void engageSubject_();

	/**
	 * Called by {@link LocalChangeSupport}
	 */
	protected void disengageModels() {
		this.disengageSubject();
		this.disengageSubjectModel();
	}

	protected void disengageSubject() {
		// check for nothing to listen to
		if (this.subject != null) {
			this.disengageSubject_();
		}
	}

	/**
	 * The {@link #subject} is not <code>null</code> - remove our listener.
	 */
	protected abstract void disengageSubject_();

	protected void disengageSubjectModel() {
		this.subjectModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.subjectListener);
		// clear out the subject when we are not listening to its holder
		this.subject = null;
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
	public class LocalChangeSupport
		extends SingleAspectChangeSupport
	{
		// TODO remove
		public LocalChangeSupport(AspectAdapter<S, A> source, Class<? extends EventListener> validListenerClass, String validAspectName) {
			this(source, validListenerClass, validAspectName, DefaultExceptionHandler.instance());
		}

		public LocalChangeSupport(AspectAdapter<S, A> source, Class<? extends EventListener> validListenerClass, String validAspectName, ExceptionHandler exceptionHandler) {
			super(source, validListenerClass, validAspectName, exceptionHandler);
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
