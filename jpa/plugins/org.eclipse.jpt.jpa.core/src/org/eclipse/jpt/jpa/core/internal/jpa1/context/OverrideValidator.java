/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyOverride;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class OverrideValidator
	implements JptValidator
{
	/** this is <code>null</code> for overrides defined on entities */
	protected final ReadOnlyPersistentAttribute persistentAttribute;

	protected final ReadOnlyOverride override;

	protected final OverrideContainer container;

	protected final OverrideTextRangeResolver textRangeResolver;

	protected final OverrideDescriptionProvider overrideDescriptionProvider;

	protected OverrideValidator(
				ReadOnlyOverride override,
				OverrideContainer container,
				OverrideTextRangeResolver textRangeResolver,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		this(null, override, container, textRangeResolver, overrideDescriptionProvider);
	}


	protected OverrideValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				ReadOnlyOverride override,
				OverrideContainer container,
				OverrideTextRangeResolver textRangeResolver,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		this.persistentAttribute = persistentAttribute;
		this.override = override;
		this.container = container;
		this.textRangeResolver = textRangeResolver;
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
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getUnresolvedOverrideTypeMessage(),
				new String[] {this.override.getName()},
				this.override,
				this.textRangeResolver.getNameTextRange()
			); 
	}

	protected String getUnresolvedOverrideTypeMessage() {
		return this.override.isVirtual() ?
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_INVALID_TYPE :
				JpaValidationMessages.ATTRIBUTE_OVERRIDE_INVALID_TYPE;
	}

	protected boolean validateName(List<IMessage> messages) {
		if ( ! CollectionTools.contains(this.container.getAllOverridableNames(), this.override.getName())) {
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
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualOverrideUnresolvedNameMessage(),
				new String[] {
					this.override.getName(),
					this.getOverrideDescriptionMessage(),
					this.container.getOverridableTypeMapping().getName()
				},
				this.override,
				this.textRangeResolver.getNameTextRange()
			);
	}

	protected abstract String getVirtualOverrideUnresolvedNameMessage();

	protected IMessage buildUnresolvedNameMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				message,
				new String[] {
					this.override.getName(),
					this.getOverrideDescriptionMessage(),
					this.container.getOverridableTypeMapping().getName()
				},
				this.override,
				this.textRangeResolver.getNameTextRange()
			);
	}

	protected abstract String getUnresolvedNameMessage();

	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualAttributeUnresolvedNameMessage(),
				new String[] {
					this.persistentAttribute.getName(),
					this.override.getName(),
					this.getOverrideDescriptionMessage(),
					this.container.getOverridableTypeMapping().getName()
				},
				this.override,
				this.textRangeResolver.getNameTextRange()
			);
	}

	protected abstract String getVirtualAttributeUnresolvedNameMessage();

	protected boolean overrideIsPartOfVirtualAttribute() {
		return (this.persistentAttribute != null) &&
				this.persistentAttribute.isVirtual();
	}

	public interface OverrideDescriptionProvider {
		String getOverrideDescriptionMessage();
	}
}
