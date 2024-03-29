/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context;

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.jpa.core.context.IdTypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractMappedSuperclassPrimaryKeyValidator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkMappedSuperclassPrimaryKeyValidator
	extends AbstractMappedSuperclassPrimaryKeyValidator
{
	public EclipseLinkMappedSuperclassPrimaryKeyValidator(
			EclipseLinkMappedSuperclass mappedSuperclass) {
		
		super(mappedSuperclass);
	}
	
	
	@Override
	protected EclipseLinkMappedSuperclass mappedSuperclass() {
		return (EclipseLinkMappedSuperclass) typeMapping();
	}
	
	// in EclipseLink, a hierarchy may define its primary key on multiple classes, so long as the
	// root entity has a coherent defined primary key.
	// However, the id class may only be defined on one class in the hierarchy.
	@Override
	protected void validatePrimaryKeyIsNotRedefined(List<IMessage> messages, IReporter reporter) {
		if (declaresIdClassLocally() && definesIdClassOnAncestor(typeMapping())) {
			messages.add(
					ValidationMessageTools.buildValidationMessage(
						typeMapping().getResource(),
						idClassReference().getValidationTextRange(),
						JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_REDEFINED
					)
				);
		}
	}
	
	// in EclipseLink, it's only the root entity where this matters
	@Override
	protected void validateIdClassIsUsedIfNecessary(List<IMessage> messages, IReporter reporter) {
		// no op
	}
	
	// in EclipseLink, it's only the root entity where this matters
	@Override
	protected void validateIdClass(JavaPersistentType idClass, List<IMessage> messages, IReporter reporter) {
		// no op
	}
	
	@Override
	protected boolean definesPrimaryKey(IdTypeMapping typeMapping) {
		return super.definesPrimaryKey(typeMapping)
			|| ((EclipseLinkTypeMapping) typeMapping).usesPrimaryKeyColumns();
	}


	@Override
	protected void validateIdClassAttributesWithPropertyAccess(
			JavaPersistentType idClass, List<IMessage> messages,
			IReporter reporter) {
		//do nothing - Eclipselink does not care about the existence status of property methods
	}

}
