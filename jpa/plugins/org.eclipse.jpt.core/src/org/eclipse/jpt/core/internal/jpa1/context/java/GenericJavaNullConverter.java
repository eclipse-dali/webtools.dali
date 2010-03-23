/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaNullConverter extends AbstractJavaJpaContextNode
	implements JavaConverter
{
	
	public GenericJavaNullConverter(JavaAttributeMapping parent) {
		super(parent);
	}

	@Override
	public JavaAttributeMapping getParent() {
		return (JavaAttributeMapping) super.getParent();
	}
	
	public String getType() {
		return Converter.NO_CONVERTER;
	}
	
	public void addToResourceModel() {
		//do nothing
	}
	
	public void removeFromResourceModel() {
		//do nothin		
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		//do nothing		
	}
}
