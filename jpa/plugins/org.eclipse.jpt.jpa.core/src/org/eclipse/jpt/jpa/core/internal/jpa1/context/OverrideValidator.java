/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class OverrideValidator
	implements JpaValidator
{
	/** this is <code>null</code> for overrides defined on entities */
	protected final PersistentAttribute persistentAttribute;

	protected final Override_ override;

	protected final OverrideContainer container;

	protected final OverrideDescriptionProvider overrideDescriptionProvider;

	protected OverrideValidator(
				Override_ override,
				OverrideContainer container,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		this(null, override, container, overrideDescriptionProvider);
	}


	protected OverrideValidator(
				PersistentAttribute persistentAttribute,
				Override_ override,
				OverrideContainer container,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		this.persistentAttribute = persistentAttribute;
		this.override = override;
		this.container = container;
		this.overrideDescriptionProvider = overrideDescriptionProvider;
	}

	protected String getOverrideDescriptionMessage()  {
		return this.overrideDescriptionProvider.getOverrideDescriptionMessage();
	}

	public boolean validate(List<IMessage> messages, IReporter reporter) {
		if (this.validateType(messages)) {
			// validate the name only if the type is valid
			return this.validateName(messages);
		}
		return false;
	}

	protected boolean validateType(List<IMessage> messages) {
		if (this.container.getOverridableTypeMapping() == null) {
			messages.add(this.buildUnresolvedOverrideTypeMessage());
			return false;
		}
		return true;
	}

	protected IMessage buildUnresolvedOverrideTypeMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.override.getResource(),
				this.override.getNameTextRange(),
				this.getUnresolvedOverrideTypeMessage(),
				this.override.getName()
			); 
	}

	protected ValidationMessage getUnresolvedOverrideTypeMessage() {
		return this.override.isVirtual() ?
				JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_INVALID_TYPE :
				JptJpaCoreValidationMessages.ATTRIBUTE_OVERRIDE_INVALID_TYPE;
	}

	protected boolean validateName(List<IMessage> messages) {
		if ( ! IterableTools.contains(this.container.getAllOverridableNames(), this.override.getName())) {
			messages.add(this.buildUnresolvedNameMessage());
			return false;
		}
		return true;
	}

	protected IMessage buildUnresolvedNameMessage() {
		if (this.override.isVirtual()) {
			// this can happen when an erroneous Java override triggers a
			// corresponding orm.xml virtual override
			return this.buildVirtualUnresolvedNameMessage();
		}
		if (this.overrideIsPartOfVirtualAttribute()) {
			return this.buildVirtualAttributeUnresolvedNameMessage();
		}
		return this.buildUnresolvedNameMessage(this.getUnresolvedNameMessage());
	}

	protected IMessage buildVirtualUnresolvedNameMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.override.getResource(),
				this.override.getNameTextRange(),
				this.getVirtualOverrideUnresolvedNameMessage(),
				this.override.getName(),
				this.getOverrideDescriptionMessage(),
				this.container.getOverridableTypeMapping().getName()
			);
	}

	protected abstract ValidationMessage getVirtualOverrideUnresolvedNameMessage();

	protected IMessage buildUnresolvedNameMessage(ValidationMessage message) {
		return ValidationMessageTools.buildValidationMessage(
				this.override.getResource(),
				this.override.getNameTextRange(),
				message,
				this.override.getName(),
				this.getOverrideDescriptionMessage(),
				this.container.getOverridableTypeMapping().getName()
			);
	}

	protected abstract ValidationMessage getUnresolvedNameMessage();

	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.override.getResource(),
				this.persistentAttribute.getValidationTextRange(),
				this.getVirtualAttributeUnresolvedNameMessage(),
				this.persistentAttribute.getName(),
				this.override.getName(),
				this.getOverrideDescriptionMessage(),
				this.container.getOverridableTypeMapping().getName()
			);
	}

	protected abstract ValidationMessage getVirtualAttributeUnresolvedNameMessage();

	protected boolean overrideIsPartOfVirtualAttribute() {
		return (this.persistentAttribute != null) &&
				this.persistentAttribute.isVirtual();
	}

	public interface OverrideDescriptionProvider {
		String getOverrideDescriptionMessage();
	}
}
