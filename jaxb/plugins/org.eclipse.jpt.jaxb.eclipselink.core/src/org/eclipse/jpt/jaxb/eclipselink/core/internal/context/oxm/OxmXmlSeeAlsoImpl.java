/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmTypeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlSeeAlso;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSeeAlso;

public class OxmXmlSeeAlsoImpl
		extends AbstractJaxbContextNode
		implements OxmXmlSeeAlso {
	
	protected EXmlSeeAlso eXmlSeeAlso;
	
	protected ClassesContainer classesContainer;
	
	
	public OxmXmlSeeAlsoImpl(OxmTypeMapping parent, EXmlSeeAlso eXmlSeeAlso) {
		super(parent);
		this.eXmlSeeAlso = eXmlSeeAlso;
		this.classesContainer = new ClassesContainer();
	}
	
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncClasses();
	}
	
	public ListIterable<String> getClasses() {
		return this.classesContainer.getContextElements();
	}

	public int getClassesSize() {
		return this.classesContainer.getContextElementsSize();
	}

	public void addClass(int index, String clazz) {
		this.classesContainer.addContextElement(index, clazz);
		this.eXmlSeeAlso.addClass(index, clazz);
	}

	public void removeClass(int index) {
		this.classesContainer.removeContextElement(index);
		this.eXmlSeeAlso.removeClass(index);
	}

	public void moveClass(int targetIndex, int sourceIndex) {
		this.classesContainer.moveContextElement(targetIndex, sourceIndex);
		this.eXmlSeeAlso.moveClass(targetIndex, sourceIndex);
	}
	
	protected void initClasses() {
		this.classesContainer.initialize();
	}
	
	protected void syncClasses() {
		this.classesContainer.synchronizeWithResourceModel();
	}

	public Iterable<String> getReferencedXmlTypeNames() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	protected class ClassesContainer
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
			return OxmXmlSeeAlsoImpl.this.eXmlSeeAlso.getClassesList();
		}
		
		@Override
		protected String getResourceElement(String contextElement) {
			return contextElement;
		}
	}
}
