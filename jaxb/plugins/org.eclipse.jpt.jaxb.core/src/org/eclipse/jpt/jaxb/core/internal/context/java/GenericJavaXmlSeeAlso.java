/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlSeeAlso;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;

public class GenericJavaXmlSeeAlso
		extends AbstractJavaContextNode
		implements XmlSeeAlso {
	
	protected final XmlSeeAlsoAnnotation annotation;
	
	protected final ListContainer<String, String> valueContainer;
	
	
	public GenericJavaXmlSeeAlso(JaxbTypeMapping parent, XmlSeeAlsoAnnotation annotation) {
		super(parent);
		this.annotation = annotation;
		this.valueContainer = this.buildValueContainer();
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
	
	public Iterable<String> getReferencedXmlTypeNames() {
		return this.annotation.getFullyQualifiedClasses();
	}
	
	@Override
	public TextRange getValidationTextRange() {
		return this.annotation.getTextRange();
	}
	
	protected ListContainer<String, String> buildValueContainer() {
		ValueContainer container = new ValueContainer();
		container.initialize();
		return container;
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
