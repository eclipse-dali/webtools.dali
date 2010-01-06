/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.persistence.options;

import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.ui.internal.jpa2.Jpt2_0UiMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  ValidationConfigurationComposite
 */
public class ValidationConfigurationComposite extends Pane<JpaOptions2_0>
{
	/**
	 * Creates a new <code>ValidationGroupComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public ValidationConfigurationComposite(
					Pane<? extends JpaOptions2_0> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite parent) {

		// SharedCacheMode
		new SharedCacheModeComposite(this, this.buildPersistenceUnit2_0Holder(), parent);

		// ValidationMode
		new ValidationModeComposite(this, this.buildPersistenceUnit2_0Holder(), parent);
		
		// GroupPrePersist
		this.addLabeledText(
			parent,
			Jpt2_0UiMessages.ValidationConfigurationComposite_groupPrePersistLabel,
			this.buildValidationGroupPrePersistHolder()
		);

		// ValidationGroupPreUpdate
		this.addLabeledText(
			parent,
			Jpt2_0UiMessages.ValidationConfigurationComposite_groupPreUpdateLabel,
			this.buildValidationGroupPreUpdateHolder()
		);

		// ValidationGroupPreRemove
		this.addLabeledText(
			parent,
			Jpt2_0UiMessages.ValidationConfigurationComposite_groupPreRemoveLabel,
			this.buildValidationGroupPreRemoveHolder()
		);
	}
	
	private PropertyValueModel<PersistenceUnit2_0> buildPersistenceUnit2_0Holder() {
		return new PropertyAspectAdapter<JpaOptions2_0, PersistenceUnit2_0>(this.getSubjectHolder()) {
			@Override
			protected PersistenceUnit2_0 buildValue_() {
				return (PersistenceUnit2_0) this.subject.getPersistenceUnit();
			}
		};
	}

	private WritablePropertyValueModel<String> buildValidationGroupPrePersistHolder() {
		return new PropertyAspectAdapter<JpaOptions2_0, String>(this.getSubjectHolder(), JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getValidationGroupPrePersist();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setValidationGroupPrePersist(value);
			}
		};
	}

	private WritablePropertyValueModel<String> buildValidationGroupPreUpdateHolder() {
		return new PropertyAspectAdapter<JpaOptions2_0, String>(this.getSubjectHolder(), JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getValidationGroupPreUpdate();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setValidationGroupPreUpdate(value);
			}
		};
	}

	private WritablePropertyValueModel<String> buildValidationGroupPreRemoveHolder() {
		return new PropertyAspectAdapter<JpaOptions2_0, String>(this.getSubjectHolder(), JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getValidationGroupPreRemove();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setValidationGroupPreRemove(value);
			}
		};
	}
}