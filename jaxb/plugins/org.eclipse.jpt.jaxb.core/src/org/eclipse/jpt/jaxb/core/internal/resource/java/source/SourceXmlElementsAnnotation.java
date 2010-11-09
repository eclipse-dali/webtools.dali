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
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementsAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;

/**
 * javax.xml.bind.annotation.adapters.XmlElements
 */
public class SourceXmlElementsAnnotation
		extends SourceAnnotation<Attribute>
		implements XmlElementsAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	private final Vector<XmlElementAnnotation> elements = new Vector<XmlElementAnnotation>();
	
	
	public SourceXmlElementsAnnotation(JavaResourceAttribute parent, Attribute attibute) {
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
		sb.append(this.elements);
	}
	
	
	// ********** AnnotationContainer implementation **********
	
	public String getElementName() {
		return JAXB.XML_ELEMENTS__VALUE;
	}
	
	public String getNestedAnnotationName() {
		return XmlElementAnnotation.ANNOTATION_NAME;
	}
	
	public ListIterable<XmlElementAnnotation> getNestedAnnotations() {
		return new LiveCloneListIterable<XmlElementAnnotation>(this.elements);
	}
	
	public int getNestedAnnotationsSize() {
		return this.elements.size();
	}
	
	public XmlElementAnnotation addNestedAnnotation(int index) {
		XmlElementAnnotation element = this.buildXmlElementAnnotation(index);
		this.elements.add(element);
		return element;
	}
	
	public void syncAddNestedAnnotation(Annotation astAnnotation) {
		int index = this.elements.size();
		XmlElementAnnotation element = this.addNestedAnnotation(index);
		element.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(XML_ELEMENTS_LIST, index, element);
	}
	
	protected XmlElementAnnotation buildXmlElementAnnotation(int index) {
		return SourceXmlElementAnnotation.createNestedXmlElementAnnotation(this, this.annotatedElement, index, this.daa);
	}
	
	public XmlElementAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.elements, targetIndex, sourceIndex).get(targetIndex);
	}
	
	public XmlElementAnnotation removeNestedAnnotation(int index) {
		return this.elements.remove(index);
	}
	
	public void syncRemoveNestedAnnotations(int index) {
		this.removeItemsFromList(index, this.elements, XML_ELEMENTS_LIST);
	}
}
