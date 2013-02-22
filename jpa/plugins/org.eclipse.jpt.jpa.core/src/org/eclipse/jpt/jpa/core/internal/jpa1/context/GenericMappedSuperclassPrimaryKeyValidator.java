/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericMappedSuperclassPrimaryKeyValidator extends
		AbstractMappedSuperclassPrimaryKeyValidator {

	public GenericMappedSuperclassPrimaryKeyValidator(MappedSuperclass mappedSuperclass) {
			super(mappedSuperclass);
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
			messages.add(
					ValidationMessageTools.buildValidationMessage(
						typeMapping().getResource(),
						idClassReference().getValidationTextRange(),
						JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_PUBLIC_NO_ARG_CONSTRUCTOR,
						idClass.getName()
					)
				);
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
