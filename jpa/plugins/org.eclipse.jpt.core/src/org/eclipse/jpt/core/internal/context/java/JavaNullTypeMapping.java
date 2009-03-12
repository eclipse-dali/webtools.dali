/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class JavaNullTypeMapping extends AbstractJavaTypeMapping
{
	public JavaNullTypeMapping(JavaPersistentType parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return null;
	}
	
	public String getKey() {
		return MappingKeys.NULL_TYPE_MAPPING_KEY;
	}
	
	public boolean isMapped() {
		return false;
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return EmptyIterator.instance();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}
	
	@Override
	public boolean shouldValidateAgainstDatabase() {
		return false;
	}
	
	/**
	 * We added this message here because the most likely solution is to add
	 * an annotation to the .java file.
	 * This message used to be found on the <class> tag in persistence.xml.
	 * The other possible way to fix the error is to remove it from persistence.xml.
	 * This can be accomplished with the Synchronize Classes action.
	 * We could also add a quick fix for this error.
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		messages.add(
			DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.PERSISTENCE_UNIT_INVALID_CLASS,
				new String[] {this.getPersistentType().getName()},
				this,
				this.getValidationTextRange(astRoot)
			)
		);
	}
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getPersistentType().getValidationTextRange(astRoot);
	}
}
