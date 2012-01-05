/*******************************************************************************
 *  Copyright (c) 2010, 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericEntityPrimaryKeyValidator
	extends AbstractEntityPrimaryKeyValidator
{
	public GenericEntityPrimaryKeyValidator(Entity entity, PrimaryKeyTextRangeResolver textRangeResolver) {
		super(entity, textRangeResolver);
	}
	
	@Override
	protected boolean idClassIsRequired() {
		//Short circuit check for idClassIsRequired if any part of the primary key is defined
		//in a superclass for Generic types.  Other validation will exist and needs to be
		//addressed first
		if(definesPrimaryKeyOnAncestor(typeMapping())){
			return false;
		}
		return super.idClassIsRequired();
	}

	@Override
	protected void validateIdClassConstructor(JavaPersistentType idClass,
			List<IMessage> messages, IReporter reporter) {
		if (!idClass.getJavaResourceType().hasPublicNoArgConstructor()) {
			messages.add(DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_PUBLIC_NO_ARG_CONSTRUCTOR,
					new String[] {idClass.getName()},
					typeMapping(),
					textRangeResolver().getIdClassTextRange()));
		}
	}

	@Override
	protected void checkMissingAttributeWithPropertyAccess(JavaPersistentType idClass, AttributeMapping attributeMapping, 
			List<IMessage> messages, IReporter reporter) {
		// Missing attribute is reported if missing getter or/and setter 
		// property method(s) in Id class with property access
		checkMissingAttribute(idClass, attributeMapping, messages, reporter);
	}

	@Override
	protected void validateIdClassAttributesWithPropertyAccess(
			JavaPersistentType idClass, List<IMessage> messages,
			IReporter reporter) {
		validateIdClassPropertyMethods(idClass, messages, reporter);
	}

}