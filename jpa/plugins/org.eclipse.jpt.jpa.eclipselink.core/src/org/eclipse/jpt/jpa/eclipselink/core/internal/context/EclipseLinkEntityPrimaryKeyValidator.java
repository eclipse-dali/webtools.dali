/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context;

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.jpa.core.context.IdTypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractEntityPrimaryKeyValidator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkEntityPrimaryKeyValidator
	extends AbstractEntityPrimaryKeyValidator
{
	public EclipseLinkEntityPrimaryKeyValidator(
			EclipseLinkEntity entity) {
		
		super(entity);
	}
	
	
	@Override
	protected EclipseLinkEntity entity() {
		return (EclipseLinkEntity) typeMapping();
	}
	
	// in EclipseLink, a hierarchy may define its primary key on multiple classes, so long as the
	// root entity has a coherent defined primary key.
	// However, the id class may only be defined on one class in the hierarchy.
	@Override
	protected void validatePrimaryKeyIsNotRedefined(List<IMessage> messages, IReporter reporter) {
		if (idClassReference().isSpecified() && definesIdClassOnAncestor(typeMapping())) {
			messages.add(
					ValidationMessageTools.buildValidationMessage(
						typeMapping().getResource(),
						idClassReference().getValidationTextRange(),
						JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_REDEFINED
					)
				);
		}
	}
	
	@Override
	protected boolean definesPrimaryKey(IdTypeMapping typeMapping) {
		return super.definesPrimaryKey(typeMapping)
			|| ((EclipseLinkTypeMapping) typeMapping).usesPrimaryKeyColumns()
			|| ((EclipseLinkTypeMapping) typeMapping).usesPrimaryKeyTenantDiscriminatorColumns();
	}
	
	@Override
	protected boolean specifiesIdClass() {
		return super.specifiesIdClass() || definesIdClass(typeMapping());
	}


	@Override
	protected void validateIdClassAttributesWithPropertyAccess(
			JavaPersistentType idClass, List<IMessage> messages,
			IReporter reporter) {
		//do nothing - Eclipselink does not care about the existence status of property methods
	}
}
