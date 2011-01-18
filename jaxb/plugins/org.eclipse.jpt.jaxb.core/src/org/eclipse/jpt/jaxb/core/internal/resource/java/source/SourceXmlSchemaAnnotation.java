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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotatedPackage;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;


public class SourceXmlSchemaAnnotation
		extends SourceAnnotation<AnnotatedPackage>
		implements XmlSchemaAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER 
			= new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	
	private static final DeclarationAnnotationElementAdapter<String> ATTRIBUTE_FORM_DEFAULT_ADAPTER = 
			buildAttributeFormDefaultAdapter();
	private final AnnotationElementAdapter<String> attributeFormDefaultAdapter;
	private XmlNsForm attributeFormDefault;
	
	private static final DeclarationAnnotationElementAdapter<String> ELEMENT_FORM_DEFAULT_ADAPTER = 
			buildElementFormDefaultAdapter();
	private final AnnotationElementAdapter<String> elementFormDefaultAdapter;
	private XmlNsForm elementFormDefault;
	
	private static final DeclarationAnnotationElementAdapter<String> LOCATION_ADAPTER = 
			buildLocationAdapter();
	private final AnnotationElementAdapter<String> locationAdapter;
	private String location;
	
	private static final DeclarationAnnotationElementAdapter<String> NAMESPACE_ADAPTER = 
			buildNamespaceAdapter();
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;
	
	private final XmlnsAnnotationContainer xmlnsContainer = new XmlnsAnnotationContainer();
	
	
	private static DeclarationAnnotationElementAdapter<String> buildAttributeFormDefaultAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(
				DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_SCHEMA__ATTRIBUTE_FORM_DEFAULT); //remove annotation when empty
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildElementFormDefaultAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(
				DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_SCHEMA__ELEMENT_FORM_DEFAULT); // remove annotation when empty
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildLocationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(
				DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_SCHEMA__LOCATION); // remove annotation when empty
	}
	
	private static DeclarationAnnotationElementAdapter<String> buildNamespaceAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(
				DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_SCHEMA__NAMESPACE); // remove annotation when empty
	}
	
	
	public SourceXmlSchemaAnnotation(JavaResourcePackage parent, AnnotatedPackage pack) {
		this(parent, pack, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public SourceXmlSchemaAnnotation(JavaResourcePackage parent, AnnotatedPackage pack, DeclarationAnnotationAdapter daa) {
		super(parent, pack, daa);
		this.attributeFormDefaultAdapter = buildAnnotationElementAdapter(ATTRIBUTE_FORM_DEFAULT_ADAPTER);
		this.elementFormDefaultAdapter = buildAnnotationElementAdapter(ELEMENT_FORM_DEFAULT_ADAPTER);
		this.locationAdapter = buildAnnotationElementAdapter(LOCATION_ADAPTER);
		this.namespaceAdapter = buildAnnotationElementAdapter(NAMESPACE_ADAPTER);
	}
	
	
	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(
			DeclarationAnnotationElementAdapter<String> daea) {
		
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.attributeFormDefault = buildAttributeFormDefault(astRoot);
		this.elementFormDefault = buildElementFormDefault(astRoot);
		this.location = buildLocation(astRoot);
		this.namespace = buildNamespace(astRoot);
		this.xmlnsContainer.initialize(this.getAstAnnotation(astRoot));
	}
	
	public void synchronizeWith(CompilationUnit astRoot) {
		syncAttributeFormDefault(buildAttributeFormDefault(astRoot));
		syncElementFormDefault(buildElementFormDefault(astRoot));
		syncLocation(buildLocation(astRoot));
		syncNamespace(buildNamespace(astRoot));
		this.xmlnsContainer.synchronize(this.getAstAnnotation(astRoot));
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.namespace);
	}
	
	
	// **************** attribute form default ********************************
	
	public XmlNsForm getAttributeFormDefault() {
		return this.attributeFormDefault;
	}
	
	public void setAttributeFormDefault(XmlNsForm attributeFormDefault) {
		if (attributeValueHasChanged(this.attributeFormDefault, attributeFormDefault)) {
			this.attributeFormDefault = attributeFormDefault;
			this.attributeFormDefaultAdapter.setValue(XmlNsForm.toJavaAnnotationValue(attributeFormDefault));
		}
	}
	
	private XmlNsForm buildAttributeFormDefault(CompilationUnit astRoot) {
		return XmlNsForm.fromJavaAnnotationValue(this.attributeFormDefaultAdapter.getValue(astRoot));
	}
	
	private void syncAttributeFormDefault(XmlNsForm attributeFormDefault) {
		XmlNsForm old = this.attributeFormDefault;
		this.attributeFormDefault = attributeFormDefault;
		firePropertyChanged(ATTRIBUTE_FORM_DEFAULT_PROPERTY, old, attributeFormDefault);
	}
	
	public TextRange getAttributeFormDefaultTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(ATTRIBUTE_FORM_DEFAULT_ADAPTER, astRoot);
	}
	
	
	// **************** element form default ********************************
	
	public XmlNsForm getElementFormDefault() {
		return this.elementFormDefault;
	}
	
	public void setElementFormDefault(XmlNsForm elementFormDefault) {
		if (attributeValueHasChanged(this.elementFormDefault, elementFormDefault)) {
			this.elementFormDefault = elementFormDefault;
			this.elementFormDefaultAdapter.setValue(XmlNsForm.toJavaAnnotationValue(elementFormDefault));
		}
	}
	
	private XmlNsForm buildElementFormDefault(CompilationUnit astRoot) {
		return XmlNsForm.fromJavaAnnotationValue(this.elementFormDefaultAdapter.getValue(astRoot));
	}
	
	private void syncElementFormDefault(XmlNsForm elementFormDefault) {
		XmlNsForm old = this.elementFormDefault;
		this.elementFormDefault = elementFormDefault;
		firePropertyChanged(ELEMENT_FORM_DEFAULT_PROPERTY, old, elementFormDefault);
	}
	
	public TextRange getElementFormDefaultTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(ELEMENT_FORM_DEFAULT_ADAPTER, astRoot);
	}
	
	
	// **************** location **********************************************
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String location) {
		if (attributeValueHasChanged(this.location, location)) {
			this.location = location;
			this.locationAdapter.setValue(location);
		}
	}
	
	private String buildLocation(CompilationUnit astRoot) {
		return this.locationAdapter.getValue(astRoot);
	}
	
	private void syncLocation(String location) {
		String old = this.location;
		this.location = location;
		firePropertyChanged(LOCATION_PROPERTY, old, location);
	}
	
	public TextRange getLocationTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(LOCATION_ADAPTER, astRoot);
	}
	
	
	// **************** namespace *********************************************
	
	public String getNamespace() {
		return this.namespace;
	}
	
	public void setNamespace(String namespace) {
		if (attributeValueHasChanged(this.namespace, namespace)) {
			this.namespace = namespace;
			this.namespaceAdapter.setValue(namespace);
		}
	}
	
	private String buildNamespace(CompilationUnit astRoot) {
		return this.namespaceAdapter.getValue(astRoot);
	}
	
	private void syncNamespace(String namespace) {
		String old = this.namespace;
		this.namespace = namespace;
		firePropertyChanged(NAMESPACE_PROPERTY, old, namespace);
	}
	
	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(NAMESPACE_ADAPTER, astRoot);
	}
	
	
	// **************** xmlns *************************************************
	
	public ListIterable<XmlNsAnnotation> getXmlns() {
		return this.xmlnsContainer.getNestedAnnotations();
	}
	
	public int getXmlnsSize() {
		return this.xmlnsContainer.getNestedAnnotationsSize();
	}
	
	public XmlNsAnnotation xmlnsAt(int index) {
		return this.xmlnsContainer.nestedAnnotationAt(index);
	}
	
	public XmlNsAnnotation addXmlns(int index) {
		return this.xmlnsContainer.addNestedAnnotation(index);
	}
	
	private XmlNsAnnotation buildXmlns(int index) {
		return new SourceXmlNsAnnotation(
				this, this.annotatedElement, buildXmlnsIndexedDeclarationAnnotationAdapter(index));
	}
	
	private IndexedDeclarationAnnotationAdapter buildXmlnsIndexedDeclarationAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(
				this.daa, JAXB.XML_SCHEMA__XMLNS, index, JAXB.XML_NS);
	}
	
	public void moveXmlns(int targetIndex, int sourceIndex) {
		this.xmlnsContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}
	
	public void removeXmlns(int index) {
		this.xmlnsContainer.removeNestedAnnotation(index);
	}
	
	/**
	 * adapt the AnnotationContainer interface to the xml schema's xmlns
	 */
	class XmlnsAnnotationContainer 
		extends AnnotationContainer<XmlNsAnnotation>
	{
		@Override
		protected String getAnnotationsPropertyName() {
			return XMLNS_LIST;
		}
		@Override
		protected String getElementName() {
			return JAXB.XML_SCHEMA__XMLNS;
		}
		@Override
		protected String getNestedAnnotationName() {
			return JAXB.XML_NS;
		}
		@Override
		protected XmlNsAnnotation buildNestedAnnotation(int index) {
			return SourceXmlSchemaAnnotation.this.buildXmlns(index);
		}
	}
}
