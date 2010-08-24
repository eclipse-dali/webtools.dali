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
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.internal.context.EntityTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractEntityValidator
	implements JptValidator
{
	protected Entity entity;

	protected JavaResourcePersistentType jrpt;

	protected EntityTextRangeResolver textRangeResolver;


	protected AbstractEntityValidator(
			Entity entity, JavaResourcePersistentType jrpt, EntityTextRangeResolver textRangeResolver) {
		this.entity = entity;
		this.jrpt = jrpt;
		this.textRangeResolver = textRangeResolver;
	}


	public void validate(List<IMessage> messages, IReporter reporter) {
		this.validateType(messages);
	}

	protected abstract void validateType(List<IMessage> messages);

	protected boolean isMemberType() {
		if (this.jrpt == null) {
			return false;
		}
		return this.jrpt.isMemberType();
	}

	protected boolean isStaticType() {
		if (this.jrpt == null) {
			return false;
		}
		return this.jrpt.isStatic();
	}

	protected boolean isFinalType() {
		if (this.jrpt == null) {
			return false;
		}
		return this.jrpt.isFinal();
	}

	protected boolean hasPrivateNoArgConstructor() {
		if (this.jrpt == null) {
			return false;
		}
		return this.jrpt.hasPrivateNoArgConstructor();
	}

	protected boolean hasNoArgConstructor() {
		if (this.jrpt == null) {
			return false;
		}
		return this.jrpt.hasNoArgConstructor();
	}
}
