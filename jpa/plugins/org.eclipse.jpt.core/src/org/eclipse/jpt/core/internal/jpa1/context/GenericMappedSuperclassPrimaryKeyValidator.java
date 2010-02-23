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
import org.eclipse.jpt.core.context.IdClassReference;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericMappedSuperclassPrimaryKeyValidator
	extends AbstractPrimaryKeyValidator
{
	public GenericMappedSuperclassPrimaryKeyValidator(
			MappedSuperclass mappedSuperclass, PrimaryKeyTextRangeResolver textRangeResolver) {
		
		super(mappedSuperclass, textRangeResolver);
	}
	
	
	protected MappedSuperclass mappedSuperclass() {
		return (MappedSuperclass) this.typeMapping();
	}
	
	@Override
	protected IdClassReference idClassReference() {
		return mappedSuperclass().getIdClassReference();
	}
	
	public void validate(List<IMessage> messages, IReporter reporter) {
		validatePrimaryKeyIsNotRedefined(messages, reporter);
		validateIdClassIsUsedIfNecessary(messages, reporter);
		
		// if primary key is composite, it may either use an id class or embedded id, not both
		validateOneOfIdClassOrEmbeddedIdIsUsed(messages, reporter);
		// ... and only one embedded id
		validateOneEmbeddedId(messages, reporter);
		
		if (specifiesIdClass()) {
			validateIdClass(idClassReference().getIdClass(), messages, reporter);
		}
	}
}
