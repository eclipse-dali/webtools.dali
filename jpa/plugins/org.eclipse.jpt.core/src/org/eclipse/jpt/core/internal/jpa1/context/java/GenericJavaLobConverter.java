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
import org.eclipse.jpt.core.context.java.JavaLobConverter;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.LobAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaLobConverter extends AbstractJavaJpaContextNode
	implements JavaLobConverter
{
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	
	public GenericJavaLobConverter(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa) {
		super(parent);
		this.initialize(jrpa);
	}

	@Override
	public JavaAttributeMapping getParent() {
		return (JavaAttributeMapping) super.getParent();
	}
	
	public String getType() {
		return Converter.LOB_CONVERTER;
	}

	protected String getAnnotationName() {
		return LobAnnotation.ANNOTATION_NAME;
	}
	
	public void addToResourceModel() {
		this.resourcePersistentAttribute.addAnnotation(getAnnotationName());
	}
	
	public void removeFromResourceModel() {
		this.resourcePersistentAttribute.removeAnnotation(getAnnotationName());
	}
	
	protected TemporalAnnotation getResourceLob() {
		return (TemporalAnnotation) this.resourcePersistentAttribute.
				getAnnotation(getAnnotationName());
	}

	protected void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
	}
	
	public void update(JavaResourcePersistentAttribute jrpa) {		
		this.resourcePersistentAttribute = jrpa;
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getResourceLob().getTextRange(astRoot);
	}
}
