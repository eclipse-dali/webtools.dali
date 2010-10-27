/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import java.util.Vector;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotatedPackage;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypesAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;


public class SourceXmlSchemaTypesAnnotation
		extends SourceAnnotation<AnnotatedPackage>
		implements XmlSchemaTypesAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = 
			new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	private final Vector<XmlSchemaTypeAnnotation> schemaTypes = new Vector<XmlSchemaTypeAnnotation>();
	
	
	public SourceXmlSchemaTypesAnnotation(JavaResourcePackage parent, AnnotatedPackage annotatedElement) {
		super(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
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
		sb.append(this.schemaTypes);
	}
	
	
	// ********** AnnotationContainer implementation **********
	
	public String getElementName() {
		return JAXB.XML_SCHEMA_TYPES__VALUE;
	}
	
	public String getNestedAnnotationName() {
		return JAXB.XML_SCHEMA_TYPE;
	}
	
	public Iterable<XmlSchemaTypeAnnotation> getNestedAnnotations() {
		return new LiveCloneIterable<XmlSchemaTypeAnnotation>(this.schemaTypes);
	}
	
	public int getNestedAnnotationsSize() {
		return this.schemaTypes.size();
	}
	
	public XmlSchemaTypeAnnotation getNestedAnnotation(int index) {
		return this.schemaTypes.get(index);
	}
	
	public XmlSchemaTypeAnnotation addNestedAnnotation() {
		return this.addNestedAnnotation(this.schemaTypes.size());
	}
	
	private XmlSchemaTypeAnnotation addNestedAnnotation(int index) {
		XmlSchemaTypeAnnotation adapter = this.buildXmlSchemaTypeAnnotation(index);
		this.schemaTypes.add(adapter);
		return adapter;
	}
	
	public void syncAddNestedAnnotation(Annotation astAnnotation) {
		int index = this.schemaTypes.size();
		XmlSchemaTypeAnnotation schemaType = this.addNestedAnnotation(index);
		schemaType.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(XML_SCHEMA_TYPES_LIST, index, schemaType);
	}
	
	protected XmlSchemaTypeAnnotation buildXmlSchemaTypeAnnotation(int index) {
		return SourceXmlSchemaTypeAnnotation.createNestedXmlSchemaTypeAnnotation(
				this, this.annotatedElement, index, this.daa);
	}
	
	public XmlSchemaTypeAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.schemaTypes, targetIndex, sourceIndex).get(targetIndex);
	}
	
	public XmlSchemaTypeAnnotation removeNestedAnnotation(int index) {
		return this.schemaTypes.remove(index);
	}
	
	public void syncRemoveNestedAnnotations(int index) {
		this.removeItemsFromList(index, this.schemaTypes, XML_SCHEMA_TYPES_LIST);
	}
}
