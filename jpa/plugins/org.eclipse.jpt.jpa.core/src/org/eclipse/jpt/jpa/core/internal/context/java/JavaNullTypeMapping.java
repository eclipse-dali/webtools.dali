/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java null type mapping
 */
public class JavaNullTypeMapping
	extends AbstractJavaTypeMapping<Annotation>
{
	public JavaNullTypeMapping(JavaPersistentType parent) {
		super(parent, null);
	}

	public String getKey() {
		return MappingKeys.NULL_TYPE_MAPPING_KEY;
	}

	public JavaPersistentType getIdClass() {
		return null;
	}
	
	public boolean isMapped() {
		return false;
	}
	
	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}
	
	public Iterable<Query> getQueries() {
		return EmptyIterable.instance();
	}


	// ********** validation **********

	/**
	 * We added this message here because the most likely solution is to add
	 * an annotation to the .java file.
	 * This message used to be found on the <class> tag in persistence.xml.
	 * The other possible way to fix the error is to remove it from persistence.xml.
	 * This can be accomplished with the Synchronize Classes action.
	 * We could also add a quick fix for this error.
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		messages.add(
			this.buildErrorValidationMessage(
				JptJpaCoreValidationMessages.PERSISTENCE_UNIT_INVALID_CLASS,
				this.getValidationTextRange(),
				this.getPersistentType().getName()
			)
		);
	}
	
	@Override
	public boolean validatesAgainstDatabase() {
		return false;
	}

	@Override
	public TextRange getValidationTextRange() {
		return this.getPersistentType().getValidationTextRange();
	}
}
