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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericTypeMappingValidator
	extends AbstractTypeMappingValidator<TypeMapping>
{
	public GenericTypeMappingValidator(TypeMapping typeMapping) {
		super(typeMapping);
	}

	@Override
	protected void validateType(List<IMessage> messages) {
		JavaResourceType jrt = this.getJavaResourceType();
		if (jrt.isFinal()) {
			messages.add(this.buildTypeMessage(JptJpaCoreValidationMessages.TYPE_MAPPING_FINAL_CLASS));
		}
		if (jrt.getTypeBinding().isMemberTypeDeclaration()) {
			messages.add(this.buildTypeMessage(JptJpaCoreValidationMessages.TYPE_MAPPING_MEMBER_CLASS));
		}
		if (jrt.hasNoArgConstructor()) {
			if (jrt.hasPrivateNoArgConstructor()) {
				messages.add(this.buildTypeMessage(JptJpaCoreValidationMessages.TYPE_MAPPING_CLASS_PRIVATE_NO_ARG_CONSTRUCTOR));
			}
		} else {
			messages.add(this.buildTypeMessage(JptJpaCoreValidationMessages.TYPE_MAPPING_CLASS_MISSING_NO_ARG_CONSTRUCTOR));
		}
	}
}
