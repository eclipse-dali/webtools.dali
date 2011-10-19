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
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefsAnnotation;

/**
 * javax.xml.bind.annotation.XmlElementRefs
 */
public class SourceXmlElementRefsAnnotation
		extends SourceAnnotation
		implements XmlElementRefsAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER 
			= new SimpleDeclarationAnnotationAdapter(JAXB.XML_ELEMENT_REFS);
	
	
	private final XmlElementRefsAnnotationContainer xmlElementRefsContainer 
			= new XmlElementRefsAnnotationContainer();
	
	
	public SourceXmlElementRefsAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		this(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public SourceXmlElementRefsAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, DeclarationAnnotationAdapter daa) {
		super(parent, annotatedElement, daa);
	}
	
	
	public String getAnnotationName() {
		return JAXB.XML_ELEMENT_REFS;
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