/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class OverrideValidator
	implements JptValidator
{
	protected final PersistentAttribute persistentAttribute;

	protected final Override_ override;

	protected final OverrideContainer container;

	protected final OverrideTextRangeResolver textRangeResolver;

	protected final OverrideDescriptionProvider overrideDescriptionProvider;

	protected OverrideValidator(
				Override_ override,
				OverrideContainer container,
				OverrideTextRangeResolver textRangeResolver,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		this(null, override, container, textRangeResolver, overrideDescriptionProvider);
	}


	protected OverrideValidator(
				PersistentAttribute persistentAttribute,
				Override_ override,
				OverrideContainer container,
				OverrideTextRangeResolver textRangeResolver,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		this.persistentAttribute = persistentAttribute;
		this.override = override;
		this.container = container;
		this.textRangeResolver = textRangeResolver;
		this.overrideDescriptionProvider = overrideDescriptionProvider;
	}

	protected boolean persistentAttributeIsVirtual() {
		return (this.persistentAttribute != null) && this.persistentAttribute.isVirtual();
	}

	protected String getPersistentAttributeName() {
		return this.persistentAttribute.getName();
	}

	protected String getOverrideDescriptionMessage()  {
		return this.overrideDescriptionProvider.getOverrideDescriptionMessage();
	}

	public boolean validate(List<IMessage> messages, IReporter reporter) {
		return this.validateName(messages);
	}

	protected boolean validateName(List<IMessage> messages) {
		if ( ! CollectionTools.contains(this.container.allOverridableNames(), this.override.getName())) {
			messages.add(this.buildUnresolvedNameMessage());
			return false;
		}
		return true;
	}

	protected IMessage buildUnresolvedNameMessage() {
		if (this.override.isVirtual()) {
			return this.buildVirtualUnresolvedNameMessage();
		}
		if (this.persistentAttributeIsVirtual()) {
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
					this.getPersistentAttributeName(),
					this.override.getName(),
					this.getOverrideDescriptionMessage(),
					this.container.getOverridableTypeMapping().getName()
				},
				this.override,
				this.textRangeResolver.getNameTextRange()
			);
	}

	protected abstract String getVirtualAttributeUnresolvedNameMessage();

	public interface OverrideDescriptionProvider {
		String getOverrideDescriptionMessage();
	}
}
