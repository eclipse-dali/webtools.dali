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
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.PersistentAttributeTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.java.PropertyAccessor;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class PersistentPropertyValidator
	extends AbstractPersistentAttributeValidator
{

	protected PropertyAccessor propertyAccessor;

	public PersistentPropertyValidator(
		ReadOnlyPersistentAttribute persistentAttribute, PropertyAccessor propertyAccessor, PersistentAttributeTextRangeResolver textRangeResolver)
	{
		super(persistentAttribute, textRangeResolver);
		this.propertyAccessor = propertyAccessor;
	}

	@Override
	//TODO validation for null getter or null setter - would apply to EclipseLink too
	//TODO validation for annotated setter? - would apply to EclipseLink too
	protected void validateMappedAttribute(List<IMessage> messages) {
		if (this.propertyAccessor.getResourceGetter() != null && this.propertyAccessor.getResourceGetter().isFinal()) {
			messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_GETTER));
		}
		if (this.propertyAccessor.getResourceSetter() != null && this.propertyAccessor.getResourceSetter().isFinal()) {
			messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_SETTER));
		}
	}
}
