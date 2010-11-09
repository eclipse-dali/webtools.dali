/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import java.util.Vector;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefsAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;

/**
 * javax.xml.bind.annotation.adapters.XmlElementRefs
 */
public class SourceXmlElementRefsAnnotation
		extends SourceAnnotation<Attribute>
		implements XmlElementRefsAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	private final Vector<XmlElementRefAnnotation> elementRefs = new Vector<XmlElementRefAnnotation>();
	
	
	public SourceXmlElementRefsAnnotation(JavaResourceAttribute parent, Attribute attibute) {
		super(parent, attibute, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	public void initialize(CompilationUnit astRoot) {
		AnnotationContainerTools.initialize(this, astRoot);
	}
	
	public void synchronizeWith(CompilationUnit astRoot) {
		AnnotationContainerTools.synchronize(this, astRoot);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.elementRefs);
	}
	
	
	// ********** AnnotationContainer implementation **********
	
	public String getElementName() {
		return JAXB.XML_ELEMENT_REFS__VALUE;
	}
	
	public String getNestedAnnotationName() {
		return XmlElementRefAnnotation.ANNOTATION_NAME;
	}
	
	public ListIterable<XmlElementRefAnnotation> getNestedAnnotations() {
		return new LiveCloneListIterable<XmlElementRefAnnotation>(this.elementRefs);
	}
	
	public int getNestedAnnotationsSize() {
		return this.elementRefs.size();
	}
	
	public XmlElementRefAnnotation addNestedAnnotation(int index) {
		XmlElementRefAnnotation elementRef = this.buildXmlElementRefAnnotation(index);
		this.elementRefs.add(elementRef);
		return elementRef;
	}
	
	public void syncAddNestedAnnotation(Annotation astAnnotation) {
		int index = this.elementRefs.size();
		XmlElementRefAnnotation elementRef = this.addNestedAnnotation(index);
		elementRef.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(XML_ELEMENT_REFS_LIST, index, elementRef);
	}
	
	protected XmlElementRefAnnotation buildXmlElementRefAnnotation(int index) {
		return SourceXmlElementRefAnnotation.createNestedXmlElementRefAnnotation(this, this.annotatedElement, index, this.daa);
	}
	
	public XmlElementRefAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.elementRefs, targetIndex, sourceIndex).get(targetIndex);
	}
	
	public XmlElementRefAnnotation removeNestedAnnotation(int index) {
		return this.elementRefs.remove(index);
	}
	
	public void syncRemoveNestedAnnotations(int index) {
		this.removeItemsFromList(index, this.elementRefs, XML_ELEMENT_REFS_LIST);
	}
}
