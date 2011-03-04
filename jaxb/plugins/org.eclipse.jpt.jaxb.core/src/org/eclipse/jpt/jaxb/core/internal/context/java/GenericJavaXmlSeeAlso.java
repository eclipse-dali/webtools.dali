/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.XmlSeeAlso;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;

public class GenericJavaXmlSeeAlso
		extends AbstractJavaContextNode
		implements XmlSeeAlso {
	
	protected final XmlSeeAlsoAnnotation annotation;
	
	protected final ValueContainer valueContainer;
	
	
	public GenericJavaXmlSeeAlso(JaxbPersistentClass parent, XmlSeeAlsoAnnotation annotation) {
		super(parent);
		this.annotation = annotation;
		this.valueContainer = new ValueContainer();
	}
	
	
	public JaxbPersistentClass getPersistentClass() {
		return (JaxbPersistentClass) getParent();
	}
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.valueContainer.synchronizeWithResourceModel();
	}
	
	public ListIterable<String> getClasses() {
		return this.valueContainer.getContextElements();
	}
	
	public int getClassesSize() {
		return this.valueContainer.getContextElementsSize();
	}
	
	public void addClass(int index, String clazz) {
		this.annotation.addClass(index, clazz);
		this.valueContainer.addContextElement(index, clazz);
	}
	
	public void removeClass(int index) {
		this.annotation.removeClass(index);
		this.valueContainer.removeContextElement(index);
	}
	
	public void moveClass(int targetIndex, int sourceIndex) {
		this.annotation.moveClass(targetIndex, sourceIndex);
		this.valueContainer.moveContextElement(targetIndex, sourceIndex);
	}
	
	@Override
	public Iterable<String> getDirectlyReferencedTypeNames() {
		return this.annotation.getFullyQualifiedClasses();
	}
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.annotation.getTextRange(astRoot);
	}
	
	
	protected class ValueContainer
			extends ListContainer<String, String> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return CLASSES_LIST;
		}
		
		@Override
		protected String buildContextElement(String resourceElement) {
			return resourceElement;
		}
		
		@Override
		protected ListIterable<String> getResourceElements() {
			return GenericJavaXmlSeeAlso.this.annotation.getClasses();
		}
		
		@Override
		protected String getResourceElement(String contextElement) {
			return contextElement;
		}
	}
}
