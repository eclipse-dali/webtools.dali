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
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.PersistentAttributeTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractPersistentAttributeValidator
	implements JptValidator
{
	protected PersistentAttribute persistentAttribute;

	protected JavaPersistentAttribute javaPersistentAttribute;

	protected PersistentAttributeTextRangeResolver textRangeResolver;


	protected AbstractPersistentAttributeValidator(
		PersistentAttribute persistentAttribute, JavaPersistentAttribute javaPersistentAttribute, PersistentAttributeTextRangeResolver textRangeResolver) {
		this.persistentAttribute = persistentAttribute;
		this.javaPersistentAttribute = javaPersistentAttribute;
		this.textRangeResolver = textRangeResolver;
	}


	public boolean validate(List<IMessage> messages, IReporter reporter) {
		this.validateAttribute(messages);
		return true;
	}

	protected abstract void validateAttribute(List<IMessage> messages);

	protected boolean isFieldAttribute() {
		if (this.javaPersistentAttribute == null) {
			return false;
		}
		return this.javaPersistentAttribute.isField();
	}

	protected boolean isPropertyAttribute() {
		if (this.javaPersistentAttribute == null) {
			return false;
		}
		return this.javaPersistentAttribute.isProperty();
	}

	protected boolean isFinalAttribute() {
		if (this.javaPersistentAttribute == null) {
			return false;
		}
		return this.javaPersistentAttribute.isFinal();
	}

	protected boolean isPublicAttribute() {
		if (this.javaPersistentAttribute == null) {
			return false;
		}
		return this.javaPersistentAttribute.isPublic();
	}

}
