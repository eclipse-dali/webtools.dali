/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementsAnnotation;

/**
 * javax.xml.bind.annotation.XmlElements
 */
public class SourceXmlElementsAnnotation
		extends SourceAnnotation
		implements XmlElementsAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER 
	= new SimpleDeclarationAnnotationAdapter(JAXB.XML_ELEMENTS);

	
	private final XmlElementsAnnotationContainer xmlElementsContainer = new XmlElementsAnnotationContainer();
	
	public SourceXmlElementsAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		this(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public SourceXmlElementsAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, DeclarationAnnotationAdapter daa) {
		super(parent, annotatedElement, daa);
	}
	
	public String getAnnotationName() {
		return JAXB.XML_ELEMENTS;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.xmlElementsContainer.initializeFromContainerAnnotation(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.xmlElementsContainer.synchronize(astAnnotation);
	}
	
	
	// **************** xmlns *************************************************
	
	public ListIterable<XmlElementAnnotation> getXmlElements() {
		return this.xmlElementsContainer.getNestedAnnotations();
	}
	
	public int getXmlElementsSize() {
		return this.xmlElementsContainer.getNestedAnnotationsSize();
	}
	
	public XmlElementAnnotation xmlElementAt(int index) {
		return this.xmlElementsContainer.getNestedAnnotation(index);
	}
	
	public XmlElementAnnotation addXmlElement(int index) {
		return this.xmlElementsContainer.addNestedAnnotation(index);
	}
	
	private XmlElementAnnotation buildXmlElement(int index) {
		return SourceXmlElementAnnotation.buildNestedSourceXmlElementAnnotation(this, this.annotatedElement, buildXmlElementIndexedDeclarationAnnotationAdapter(index));
	}
	
	private IndexedDeclarationAnnotationAdapter buildXmlElementIndexedDeclarationAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(
			this.daa, JAXB.XML_ELEMENTS__VALUE, index, JAXB.XML_ELEMENT);
	}
	
	public void moveXmlElement(int targetIndex, int sourceIndex) {
		this.xmlElementsContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}
	
	public void removeXmlElement(int index) {
		this.xmlElementsContainer.removeNestedAnnotation(index);
	}
	
	/**
	* adapt the AnnotationContainer interface to the xml schema's xmlns
	*/
	class XmlElementsAnnotationContainer 
		extends AnnotationContainer<XmlElementAnnotation>
		{
		@Override
		protected String getNestedAnnotationsListName() {
			return XML_ELEMENTS_LIST;
		}
		@Override
		protected String getElementName() {
			return JAXB.XML_ELEMENTS__VALUE;
		}
		@Override
		protected String getNestedAnnotationName() {
			return JAXB.XML_ELEMENT;
		}
		@Override
		protected XmlElementAnnotation buildNestedAnnotation(int index) {
			return SourceXmlElementsAnnotation.this.buildXmlElement(index);
		}
	}
}
