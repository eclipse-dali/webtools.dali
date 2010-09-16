/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOverrideValidator
	implements JptValidator
{

	protected final PersistentAttribute persistentAttribute;

	protected final BaseOverride override;

	protected final BaseOverride.Owner owner;

	protected final OverrideTextRangeResolver textRangeResolver;

	protected final OverrideDescriptionProvider overrideDescriptionProvider;

	protected AbstractOverrideValidator(
				BaseOverride override,
				BaseOverride.Owner owner,
				OverrideTextRangeResolver textRangeResolver,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		this(null, override, owner, textRangeResolver, overrideDescriptionProvider);
	}


	protected AbstractOverrideValidator(
				PersistentAttribute persistentAttribute,
				BaseOverride override,
				BaseOverride.Owner owner,
				OverrideTextRangeResolver textRangeResolver,
				OverrideDescriptionProvider overrideDescriptionProvider) {
		this.persistentAttribute = persistentAttribute;
		this.override = override;
		this.owner = owner;
		this.textRangeResolver = textRangeResolver;
		this.overrideDescriptionProvider = overrideDescriptionProvider;
	}

	protected BaseOverride getOverride() {
		return this.override;
	}

	protected BaseOverride.Owner getOwner() {
		return this.owner;
	}

	protected OverrideTextRangeResolver getTextRangeResolver() {
		return this.textRangeResolver;
	}

	protected boolean isPersistentAttributeVirtual() {
		return this.persistentAttribute != null && this.persistentAttribute.isVirtual();
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
		if (!CollectionTools.contains(this.getOwner().allOverridableAttributeNames(), this.getOverride().getName())) {
			messages.add(this.buildUnresolvedNameMessage());
			return false;
		}
		return true;
	}

	protected IMessage buildUnresolvedNameMessage() {
		if (this.override.isVirtual()) {
			return this.buildVirtualUnresolvedNameMessage();
		}
		if (isPersistentAttributeVirtual()) {
			return this.buildVirtualAttributeUnresolvedNameMessage();
		}
		return this.buildUnresolvedNameMessage(this.getUnresolvedNameMessage());
	}
	
	protected IMessage buildVirtualUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			getVirtualOverrideUnresolvedNameMessage(),
			new String[] {
				this.getOverride().getName(),
				this.getOverrideDescriptionMessage(),
				this.getOwner().getOverridableTypeMapping().getName()},
			this.getOverride(),
			this.textRangeResolver.getNameTextRange()
		);
	}

	protected abstract String getVirtualOverrideUnresolvedNameMessage();

	protected IMessage buildUnresolvedNameMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				message,
				new String[] {
					this.getOverride().getName(),
					this.getOverrideDescriptionMessage(),
					this.getOwner().getOverridableTypeMapping().getName()},
				this.getOverride(),
				this.textRangeResolver.getNameTextRange());
	}

	protected abstract String getUnresolvedNameMessage();

	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			getVirtualAttributeUnresolvedNameMessage(),
			new String[] {
				this.getPersistentAttributeName(),
				this.getOverride().getName(),
				this.getOverrideDescriptionMessage(),
				this.getOwner().getOverridableTypeMapping().getName()},
			this.getOverride(),
			this.textRangeResolver.getNameTextRange());
	}

	protected abstract String getVirtualAttributeUnresolvedNameMessage();
	

	public static interface OverrideDescriptionProvider {
		String getOverrideDescriptionMessage();
	}

}
