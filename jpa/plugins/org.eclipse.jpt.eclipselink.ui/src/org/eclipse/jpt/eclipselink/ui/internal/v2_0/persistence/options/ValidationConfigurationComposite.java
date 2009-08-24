/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.options;

import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.options.Options2_0;
import org.eclipse.jpt.ui.internal.jpa2.Jpt2_0UiMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  ValidationConfigurationComposite
 */
public class ValidationConfigurationComposite extends FormPane<Options2_0>
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
					FormPane<Options2_0> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// GroupPrePersist
		this.addLabeledText(
			container,
			Jpt2_0UiMessages.ValidationConfigurationComposite_groupPrePersistLabel,
			this.buildValidationGroupPrePersistHolder()
		);

		// ValidationGroupPreUpdate
		this.addLabeledText(
			container,
			Jpt2_0UiMessages.ValidationConfigurationComposite_groupPreUpdateLabel,
			this.buildValidationGroupPreUpdateHolder()
		);

		// ValidationGroupPreRemove
		this.addLabeledText(
			container,
			Jpt2_0UiMessages.ValidationConfigurationComposite_groupPreRemoveLabel,
			this.buildValidationGroupPreRemoveHolder()
		);
	}

	private WritablePropertyValueModel<String> buildValidationGroupPrePersistHolder() {
		return new PropertyAspectAdapter<Options2_0, String>(this.getSubjectHolder(), Options2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY) {
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
		return new PropertyAspectAdapter<Options2_0, String>(this.getSubjectHolder(), Options2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY) {
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
		return new PropertyAspectAdapter<Options2_0, String>(this.getSubjectHolder(), Options2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY) {
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