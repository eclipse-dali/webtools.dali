/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractEntityPrimaryKeyValidator;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkEntityPrimaryKeyValidator
	extends AbstractEntityPrimaryKeyValidator
{
	public EclipseLinkEntityPrimaryKeyValidator(
			EclipseLinkEntity entity, PrimaryKeyTextRangeResolver textRangeResolver) {
		
		super(entity, textRangeResolver);
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
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TYPE_MAPPING_ID_CLASS_REDEFINED,
						new String[0],
						typeMapping(),
						textRangeResolver().getIdClassTextRange()));
		}
	}
	
	@Override
	protected boolean definesPrimaryKey(TypeMapping typeMapping) {
		return super.definesPrimaryKey(typeMapping)
			|| ((EclipseLinkTypeMapping) typeMapping).usesPrimaryKeyColumns()
			|| ((EclipseLinkTypeMapping) typeMapping).usesPrimaryKeyTenantDiscriminatorColumns();
	}
	
	@Override
	protected boolean specifiesIdClass() {
		return super.specifiesIdClass() || definesIdClass(typeMapping());
	}
}
