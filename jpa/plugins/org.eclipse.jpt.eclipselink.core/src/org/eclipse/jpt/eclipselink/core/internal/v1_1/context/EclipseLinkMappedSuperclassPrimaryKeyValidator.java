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
package org.eclipse.jpt.eclipselink.core.internal.v1_1.context;

import java.util.List;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.AbstractMappedSuperclassPrimaryKeyValidator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkMappedSuperclassPrimaryKeyValidator
	extends AbstractMappedSuperclassPrimaryKeyValidator
{
	public EclipseLinkMappedSuperclassPrimaryKeyValidator(
			EclipseLinkMappedSuperclass mappedSuperclass, PrimaryKeyTextRangeResolver textRangeResolver) {
		
		super(mappedSuperclass, textRangeResolver);
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
	protected boolean definesPrimaryKey(TypeMapping typeMapping) {
		return super.definesPrimaryKey(typeMapping)
			|| ((EclipseLinkTypeMapping) typeMapping).usesPrimaryKeyColumns();
	}
}
