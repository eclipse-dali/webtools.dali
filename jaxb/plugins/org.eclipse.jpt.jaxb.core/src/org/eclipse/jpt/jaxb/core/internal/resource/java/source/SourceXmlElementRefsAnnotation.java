/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefsAnnotation;

/**
 * javax.xml.bind.annotation.XmlElementRefs
 */
public class SourceXmlElementRefsAnnotation
		extends SourceAnnotation<Attribute>
		implements XmlElementRefsAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER 
			= new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	
	private final XmlElementRefsAnnotationContainer xmlElementRefsContainer 
			= new XmlElementRefsAnnotationContainer();
	
	
	public SourceXmlElementRefsAnnotation(JavaResourceAttribute parent, Attribute attribute) {
		this(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public SourceXmlElementRefsAnnotation(JavaResourceAttribute parent, Attribute attribute, DeclarationAnnotationAdapter daa) {
		super(parent, attribute, daa);
	}
	
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.xmlElementRefsContainer.initialize(this.getAstAnnotation(astRoot));
	}
	
	public void synchronizeWith(CompilationUnit astRoot) {
		this.xmlElementRefsContainer.synchronize(this.getAstAnnotation(astRoot));
	}
	
	
	// **************** xml element refs **************************************
	
	public ListIterable<XmlElementRefAnnotation> getXmlElementRefs() {
		return this.xmlElementRefsContainer.getNestedAnnotations();
	}
	
	public int getXmlElementRefsSize() {
		return this.xmlElementRefsContainer.getNestedAnnotationsSize();
	}
	
	public XmlElementRefAnnotation xmlElementRefAt(int index) {
		return this.xmlElementRefsContainer.nestedAnnotationAt(index);
	}
	
	public XmlElementRefAnnotation addXmlElementRef(int index) {
		return this.xmlElementRefsContainer.addNestedAnnotation(index);
	}
	
	private XmlElementRefAnnotation buildXmlElementRef(int index) {
		return SourceXmlElementRefAnnotation.buildNestedSourceXmlElementRefAnnotation(
				this, this.annotatedElement, buildXmlElementRefIndexedDeclarationAnnotationAdapter(index));
	}
	
	private IndexedDeclarationAnnotationAdapter buildXmlElementRefIndexedDeclarationAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(
				this.daa, JAXB.XML_ELEMENT_REFS__VALUE, index, JAXB.XML_ELEMENT_REF);
	}
	
	public void moveXmlElementRef(int targetIndex, int sourceIndex) {
		this.xmlElementRefsContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}
	
	public void removeXmlElementRef(int index) {
		this.xmlElementRefsContainer.removeNestedAnnotation(index);
	}
	
	
	class XmlElementRefsAnnotationContainer 
			extends AnnotationContainer<XmlElementRefAnnotation> {
		
		@Override
		protected String getAnnotationsPropertyName() {
			return XML_ELEMENT_REFS_LIST;
		}
		
		@Override
		protected String getElementName() {
			return JAXB.XML_ELEMENT_REFS__VALUE;
		}
		
		@Override
		protected String getNestedAnnotationName() {
			return JAXB.XML_ELEMENT_REF;
		}
		
		@Override
		protected XmlElementRefAnnotation buildNestedAnnotation(int index) {
			return SourceXmlElementRefsAnnotation.this.buildXmlElementRef(index);
		}
	}
}
